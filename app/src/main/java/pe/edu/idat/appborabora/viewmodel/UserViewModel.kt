package pe.edu.idat.appborabora.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import pe.edu.idat.appborabora.data.dto.request.UpdateUserRequest
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.dto.response.UserProfileResponse
import pe.edu.idat.appborabora.data.network.authenticated.AuthClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val authClient = AuthClient(application.applicationContext)
    private val authService = authClient.getInstance()

    val userProfileResponse: MutableLiveData<UserProfileResponse> = MutableLiveData()
    val apiResponse: MutableLiveData<ApiResponse> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    //--BUSCAR USUARIO
    fun getUserByUsername(username: String) {
        authService.getUserByUsername(username).enqueue(object : Callback<UserProfileResponse> {
            override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
                if (response.isSuccessful) {
                    Log.d("UserViewModel", "Respuesta exitosa al obtener el perfil del usuario")
                    userProfileResponse.value = response.body()
                } else {
                    Log.d("UserViewModel", "Error en la respuesta al obtener el perfil del usuario: ${response.errorBody()?.string()}")
                    errorMessage.value = "Error al obtener el perfil del usuario: ${response.errorBody()?.string()}"
                }
            }

            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                Log.e("UserViewModel", "Fallo al obtener el perfil del usuario", t)
                errorMessage.value = "Error al obtener el perfil del usuario: ${t.message}"
            }
        })
    }

    //--ACTUALIZAR USUARIO
    fun updateUser(identityDoc: Int, updateUserRequest: UpdateUserRequest) {
        authService.updateUser(identityDoc, updateUserRequest).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Log.d("UserViewModel", "Respuesta exitosa al actualizar el usuario")
                    apiResponse.value = response.body()
                } else {
                    val gson = Gson()
                    val errorResponse = response.errorBody()?.let {
                        gson.fromJson(it.string(), ApiResponse::class.java)
                    }
                    Log.d("UserViewModel", "Error en la respuesta al actualizar el usuario: ${errorResponse?.message}")
                    errorMessage.value = "${errorResponse?.message}"
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("UserViewModel", "Fallo al actualizar el usuario", t)
                errorMessage.value = "${t.message}"
            }
        })
    }
}
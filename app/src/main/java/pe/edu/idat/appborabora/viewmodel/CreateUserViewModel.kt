package pe.edu.idat.appborabora.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import pe.edu.idat.appborabora.data.dto.request.CreateUserRequest
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.dto.response.CreateUserResponse
import pe.edu.idat.appborabora.data.network.public.PublicClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateUserViewModel (application: Application) : AndroidViewModel(application) {
    private val publicClient = PublicClient()
    private val publicService = publicClient.getInstance()

    val registerResponseMutableLiveData: MutableLiveData<CreateUserResponse> = MutableLiveData()

    fun createUser(createUserRequest: CreateUserRequest) {
        publicService.createUser(createUserRequest)
            .enqueue(object : Callback<CreateUserResponse> {
                override fun onResponse(call: Call<CreateUserResponse>, response: Response<CreateUserResponse>) {
                    if (response.isSuccessful) {
                        registerResponseMutableLiveData.value = response.body()
                    } else {
                        // Handle unsuccessful response
                        val errorResponse = CreateUserResponse("No se puedo crear el usuario", status = true)
                        registerResponseMutableLiveData.value = errorResponse
                        Log.e("CreateUser", "Unsuccessful response: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<CreateUserResponse>, t: Throwable) {
                    // Handle failure
                    val errorResponse = CreateUserResponse("No se puedo crear el usuario", status = true)
                    registerResponseMutableLiveData.value = errorResponse
                    Log.e("CreateUser", "Request failed", t)
                }
            })
    }
}
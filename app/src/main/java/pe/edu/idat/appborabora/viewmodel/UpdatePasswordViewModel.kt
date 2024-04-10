package pe.edu.idat.appborabora.viewmodel

import android.app.Application
import android.util.Log
import retrofit2.Call
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import pe.edu.idat.appborabora.data.dto.request.PasswordUpdateRequest
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.network.public.PublicClient
import retrofit2.Callback
import retrofit2.Response

class UpdatePasswordViewModel (application: Application) : AndroidViewModel(application) {
    private val publicClient = PublicClient()
    private val publicService = publicClient.getInstance()

    val updatePasswordResponseMutableLiveData: MutableLiveData<ApiResponse> = MutableLiveData()

    fun updatePassword(passwordUpdateRequest: PasswordUpdateRequest) {
        publicService.updatePassword(passwordUpdateRequest)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        updatePasswordResponseMutableLiveData.value = response.body()
                    } else {
                        // Handle unsuccessful response
                        val errorResponse = ApiResponse("Error updating password", response.code())
                        updatePasswordResponseMutableLiveData.value = errorResponse
                        Log.e("UpdatePassword", "Unsuccessful response: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    // Handle failure
                    val errorResponse = ApiResponse("Error updating password", 0)
                    updatePasswordResponseMutableLiveData.value = errorResponse
                    Log.e("UpdatePassword", "Request failed", t)
                }
            })
    }
}
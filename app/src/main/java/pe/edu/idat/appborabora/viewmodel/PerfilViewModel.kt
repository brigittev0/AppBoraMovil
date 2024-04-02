package pe.edu.idat.appborabora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.appborabora.data.dto.response.CreateUser
import pe.edu.idat.appborabora.data.dto.response.UserResponse
import pe.edu.idat.appborabora.data.network.BoraBoraClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilViewModel : ViewModel() {
    private val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> get() = _user

    private val boraBoraService = BoraBoraClient().getInstance()

    fun getUserByUsername(username: String) {
        boraBoraService.getUserByUsername(username).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    // Maneja el error
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                // Maneja el error
            }
        })
    }
}
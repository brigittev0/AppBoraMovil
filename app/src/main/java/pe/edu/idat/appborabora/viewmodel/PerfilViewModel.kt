package pe.edu.idat.appborabora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.appborabora.data.dto.response.PerfilResponse
import pe.edu.idat.appborabora.data.network.public.PublicClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilViewModel : ViewModel() {
    private val _user = MutableLiveData<PerfilResponse>()
    val user: LiveData<PerfilResponse> get() = _user

    private val boraBoraService = PublicClient().getInstance()

    fun getUserByUsername(username: String) {
        boraBoraService.getUserByUsername(username).enqueue(object : Callback<PerfilResponse> {
            override fun onResponse(call: Call<PerfilResponse>, response: Response<PerfilResponse>) {
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    // Maneja el error
                }
            }

            override fun onFailure(call: Call<PerfilResponse>, t: Throwable) {
                // Maneja el error
            }
        })
    }
}
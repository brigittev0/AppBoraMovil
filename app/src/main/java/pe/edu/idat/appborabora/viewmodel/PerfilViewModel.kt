package pe.edu.idat.appborabora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.appborabora.data.model.response.CreateUser
import pe.edu.idat.appborabora.data.network.BoraBoraClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilViewModel : ViewModel() {
    private val _updatePerfil = MutableLiveData<String>()
    val updatePerfil: LiveData<String> get() = _updatePerfil
    private val service = BoraBoraClient().getInstance()

    fun actualizarPerfil(userId: Int, userDetails: CreateUser) {
        service.updateUserDetails(userId, userDetails).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    _updatePerfil.value = response.body()
                } else {
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }

    fun observeUpdatePerfilResponse(): LiveData<String> {
        return _updatePerfil
    }
}
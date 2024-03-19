package pe.edu.idat.appborabora.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.appborabora.data.model.request.LoginRequest
import pe.edu.idat.appborabora.data.model.response.LoginResponse
import pe.edu.idat.appborabora.data.network.BoraBoraClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun login(username: String, password: String) {
        try {
            val apiService = BoraBoraClient().getInstance()
            val loginRequest = LoginRequest()

            if (username.isNotBlank()) {
                loginRequest.username = username
            } else {
                throw IllegalArgumentException("Username no puede estar en blanco")
            }

            if (password.isNotBlank()) {
                loginRequest.password = password
            } else {
                throw IllegalArgumentException("Password no puede estar en blanco")
            }

            val call = apiService.login(loginRequest)

            Log.d("LoginViewModel", "Iniciando llamada a la API con usuario: $username")

            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    Log.d("LoginViewModel", "Respuesta recibida de la API: ${response.body()}")
                    handleResponse(response)
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("RetrofitError", "Request error: " + t.message)
                    _loginState.value = LoginState.Error(t.message ?: "Error desconocido")
                }
            })

        } catch (e: Exception) {
            Log.e("LoginViewModel", "Excepción capturada en login: ${e.message}")
            _loginState.value = LoginState.Error(e.message ?: "Error desconocido")
        }
    }

    private fun handleResponse(response: Response<LoginResponse>?) {
        if (response?.isSuccessful == true) {
            val body = response.body()
            val message = body?.message
            val username = body?.username
            val token = body?.token
            val role = body?.role?.get(0)?.authority

            if (username != null && message != null) {
                _loginState.value = LoginState.Success(username, role, token)
            } else {
                _loginState.value = LoginState.Error("Usuario o contraseña inválidos")
            }
        } else {
            val statusCode = response?.code() ?: 0
            val statusMessage = response?.message() ?: "Error desconocido"

            _loginState.value = LoginState.Error("Error $statusCode: $statusMessage")
        }
    }
}

sealed class LoginState {
    data class Success(val username: String, val role: String?, val token: String?) : LoginState()
    data class Error(val error: String) : LoginState()
}
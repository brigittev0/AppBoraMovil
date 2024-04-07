package pe.edu.idat.appborabora.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import pe.edu.idat.appborabora.data.dto.request.LoginRequest
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.dto.response.LoginResponse
import pe.edu.idat.appborabora.data.network.public.PublicClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel() : ViewModel() {
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun login(username: String, password: String) {
        try {
            val apiService = PublicClient().getInstance()
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
                    _loginState.value = LoginState.Error("API - Error de conexión")
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
            val jwt = body?.jwt
            val roles = body?.roles
            val identityDoc = body?.identityDoc

            if (username != null && message != null && !roles.isNullOrEmpty() && identityDoc != null) {
                _loginState.value = LoginState.Success(username, roles[0], jwt, identityDoc)
            } else {
                _loginState.value = LoginState.Error("Usuario o contraseña inválidos")
            }
        } else {
            val statusCode = response?.code() ?: 0
            val errorBody = response?.errorBody()?.string() ?: "Error desconocido"

            if (statusCode == 401) {
                val apiResponse = Gson().fromJson(errorBody, ApiResponse::class.java)
                _loginState.value = LoginState.Error(apiResponse.message)
            } else {
                _loginState.value = LoginState.Error("Error $statusCode: $errorBody")
            }
        }
    }
}

sealed class LoginState {
    data class Success(val username: String, val role: String, val jwt: String?, val identityDoc: Int) : LoginState()
    data class Error(val error: String) : LoginState()
}
package pe.edu.idat.appborabora.data.network.service

import pe.edu.idat.appborabora.data.model.request.LoginRequest
import pe.edu.idat.appborabora.data.model.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("login")
    fun login(@Body data: LoginRequest): Call<LoginResponse>
}
package pe.edu.idat.appborabora.data.network.client

import pe.edu.idat.appborabora.data.network.service.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BoraBoraClient {

    companion object {
        private const val BASE_URL = "http://(AGREGA IP):8070/api/v1/"

        //NOTA: AGREGAR IP TAMBIEN EN EL ARCHIVO =  xml/network-security-config
    }

    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //-- SERVICES

    //-- User
    fun getUserService(): UserService {
        return retrofit.create(UserService::class.java)
    }
}
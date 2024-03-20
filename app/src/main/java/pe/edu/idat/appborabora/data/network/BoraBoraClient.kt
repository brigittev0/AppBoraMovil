package pe.edu.idat.appborabora.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BoraBoraClient {

    companion object {
        private const val BASE_URL = "http://192.168.1.45:8070/api/v1/"
    }

    //Ejemplo:   "http://192.160.13.4:8070/api/v1/"
    //NOTA: AGREGAR IP TAMBIEN EN EL ARCHIVO =  xml/network-security-config


    private val boraboraService: BoraBoraService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        boraboraService = retrofit.create(BoraBoraService::class.java)
    }

    fun getInstance(): BoraBoraService {
        return boraboraService
    }
}


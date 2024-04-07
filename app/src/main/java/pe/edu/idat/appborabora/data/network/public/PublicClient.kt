package pe.edu.idat.appborabora.data.network.public

import pe.edu.idat.appborabora.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// SIN TOKEN
class PublicClient {

    private val publicService: PublicService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        publicService = retrofit.create(PublicService::class.java)
    }

    fun getInstance(): PublicService {
        return publicService
    }
}




package pe.edu.idat.appborabora.data.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BoraBoraClient {

    companion object {
        private const val BASE_URL = "http://192.168.0.15:8070/api/v1/"
    }

    private val boraboraService: BoraBoraService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        boraboraService = retrofit.create(BoraBoraService::class.java)
    }

    //SOLICITUDES SIN TOKEN
    fun getInstance(): BoraBoraService {
        return boraboraService
    }

    //SOLICITUDES CON TOKEN
    fun getInstanceWithAuth(context: Context): BoraBoraService {
        val sharedPref = context.getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE)
        val jwt = sharedPref.getString("jwt", "")

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(jwt ?: ""))
            .build()

        val retrofitWithAuth = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitWithAuth.create(BoraBoraService::class.java)
    }
}


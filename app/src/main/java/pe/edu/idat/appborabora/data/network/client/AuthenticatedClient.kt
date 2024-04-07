package pe.edu.idat.appborabora.data.network.client

import okhttp3.OkHttpClient
import pe.edu.idat.appborabora.data.network.AuthInterceptor
import pe.edu.idat.appborabora.data.network.service.PublicService
import pe.edu.idat.appborabora.util.ConstantsBoraBora
import pe.edu.idat.appborabora.util.SharedPrefManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//SOLICITUDES CON TOKEN
class AuthenticatedClient(private val sharedPrefManager: SharedPrefManager) {

    fun getInstance(): PublicService {
        val jwt = sharedPrefManager.getJwt() ?: ""

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(jwt))
            .build()

        val retrofitWithAuth = Retrofit.Builder()
            .baseUrl(ConstantsBoraBora.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitWithAuth.create(PublicService::class.java)
    }
}
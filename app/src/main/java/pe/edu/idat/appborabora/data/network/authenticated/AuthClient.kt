package pe.edu.idat.appborabora.data.network.authenticated

import android.content.Context
import okhttp3.OkHttpClient
import pe.edu.idat.appborabora.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AuthClient(context: Context) {
    private val authService: AuthService

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authService = retrofit.create(AuthService::class.java)
    }

    fun getInstance(): AuthService {
        return authService
    }
}
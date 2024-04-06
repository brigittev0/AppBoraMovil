package pe.edu.idat.appborabora.data.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String, private val role: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .header("Role", role)
            .build()

        return chain.proceed(newRequest)
    }
}

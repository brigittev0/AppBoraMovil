package pe.edu.idat.appborabora.data.network.authenticated

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer " + obtenerTokenDePreferencias())
            .method(original.method, original.body)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun obtenerTokenDePreferencias(): String {
        val sharedPref = context.getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE)
        return sharedPref.getString("jwt", "") ?: ""
    }
}


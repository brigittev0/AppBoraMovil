package pe.edu.idat.appborabora.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.view.HomeNavigation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

var texto: TextView? = null
var Usuario: String? = null
var Contraseña: String? = null
var ERROR = 5

class Login : AppCompatActivity(), View.OnClickListener {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnuser = findViewById<Button>(R.id.btniniciouser)
        val btnadmin = findViewById<Button>(R.id.btninicioadmin)
        val tvnewpassword = findViewById<TextView>(R.id.tvolvidarcontrasena)
        btnuser.setOnClickListener(this)
        btnadmin.setOnClickListener(this)
        tvnewpassword.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

         /*https://stackoverflow.com/questions/41078866/retrofit2-authorization-global-interceptor-for-access-token*/
        try {
            val client = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)

                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("http:")
                /* .baseUrl("http: */
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)

                .build()
            val apiService = retrofit.create(ApiService::class.java)
            val datosenviar = DatosEnviar()
            Usuario?.let { datosenviar.setUsuario(it) }
            Contraseña?.let { datosenviar.setContraseña(it) }

            val call = apiService.AprobarSolicitud(datosenviar)

            call.enqueue(object : Callback<Respuesta> {
                override fun onResponse(call: Call<Respuesta>, response: Response<Respuesta>) {
                    if (response.isSuccessful) {
                        val respuesta = response.body()
                        var mensaje = respuesta?.Mensaje ?: "SIN DATOS"
                        var Exito = respuesta?.Exito ?: "SIN DATOS"
                        ERROR = response.code()
                        texto?.setText(mensaje)
                        val intent = Intent(v!!.context, Informacion::class.java)
                        startActivityForResult(intent, 0)
                    } else {
                        val codigoEstado = response.code()
                        val mensajeEstado = response.message()
                        ERROR = response.code()
                        texto?.setText("Ocurrió un error: Código $codigoEstado - $mensajeEstado")
                    }
                }

                override fun onFailure(call: Call<Respuesta>, t: Throwable) {
                    Log.e("RetrofitError", "Error en la solicitud: " + t.message)
                    ERROR = 1
                    texto?.setText("Error de conexión Factura: " + t.message)
                }
            })

        } catch (e: Exception) {
            texto?.setText(e.message)
        }

        when (v?.id) {

            R.id.btninicioadmin -> {
                val intent = Intent(this, Admin::class.java)
                startActivity(intent)
            }

            R.id.btniniciouser -> {
                val intent = Intent(this, HomeNavigation::class.java)
                startActivity(intent)
            }
            R.id.tvolvidarcontrasena -> {
                val intent = Intent(this, NewPassword::class.java)
                startActivity(intent)
            }
        }
    }
}

class Informacion {

}

private fun <T> Call<T>?.enqueue(callback: Callback<Respuesta>) {

}

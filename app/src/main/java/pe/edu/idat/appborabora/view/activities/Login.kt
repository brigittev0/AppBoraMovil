package pe.edu.idat.appborabora.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.model.request.LoginRequest
import pe.edu.idat.appborabora.data.model.response.LoginResponse
import pe.edu.idat.appborabora.data.network.client.BoraBoraClient
import pe.edu.idat.appborabora.view.HomeNavigation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity(), View.OnClickListener {

    private var username: String? = null
    private var password: String? = null
    private lateinit var tUser: EditText
    private lateinit var tPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tUser = findViewById(R.id.tUsername)
        tPassword = findViewById(R.id.tPassword)

        val btnlogin = findViewById<Button>(R.id.btnlogin)
        btnlogin.setOnClickListener(this)

        val tvregistrolog = findViewById<TextView>(R.id.tvregisterlog)
        tvregistrolog.setOnClickListener(this)

        // Animacion
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        findViewById<ConstraintLayout>(R.id.innerConstraintLayout).startAnimation(slideUp)

        // Manejar click cerrar
        findViewById<ImageButton>(R.id.closeButton).setOnClickListener {
            finish()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnlogin -> {
                username = tUser.text.toString()
                password = tPassword.text.toString()

                if (username.isNullOrBlank()) {
                    tUser.setError("Ingrese el usuario")
                }

                if (password.isNullOrBlank()) {
                    tPassword.setError("Ingrese la contraseña")
                }

                if (!username.isNullOrBlank() && !password.isNullOrBlank()) {
                    makeApiCall()
                }
            }

            R.id.tvregisterlog -> {
                val intent = Intent(this, RegisterUser::class.java)
                startActivity(intent)
            }
        }
    }

    private fun startHomeNavigation() {
        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val role = sharedPref.getString("role", "")

        val intent = when (role) {
            "ROLE_ADMIN" -> Intent(this, Admin::class.java)
            "ROLE_USER" -> Intent(this, HomeNavigation::class.java)
            else -> Intent(this, HomeNavigation::class.java)
        }

        startActivity(intent)
    }

    private fun makeApiCall() {
        try {
            val apiService = BoraBoraClient().getUserService()
            val loginRequest = LoginRequest()
            username?.let {
                if (it.isNotBlank()) {
                    loginRequest.username = it
                } else {
                    throw IllegalArgumentException("Username no puede estar en blanco")
                }
            }

            password?.let {
                if (it.isNotBlank()) {
                    loginRequest.password = it
                } else {
                    throw IllegalArgumentException("Password no puede estar en blanco")
                }
            }

            val call = apiService.login(loginRequest)

            Log.d("LoginActivity", "Iniciando llamada a la API con usuario: $username")

            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    Log.d("LoginActivity", "Respuesta recibida de la API: ${response.body()}")
                    handleResponse(response)
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("RetrofitError", "Request error: " + t.message)
                    handleNetworkError()
                }
            })

        } catch (e: Exception) {
            Log.e("LoginActivity", "Excepción capturada en makeApiCall: ${e.message}")
        }
    }

    private fun handleResponse(response: Response<LoginResponse>?) {
        if (response?.isSuccessful == true) {
            val body = response.body()
            val message = body?.message
            val username = body?.username
            val token = body?.token
            val role = body?.role?.get(0)?.authority

            if (username != null || message != null) {
                saveToSharedPrefs(username, role, token)
                startHomeNavigation()
            } else {
                val errorMsg = getString(R.string.invalid_user_error)
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
                Log.e("LoginActivity", errorMsg)
            }
        } else {
            val statusCode = response?.code() ?: 0
            val statusMessage = response?.message() ?: getString(R.string.unknown_error)

            val errorMsg = getString(R.string.error_code_status, statusCode, statusMessage)

            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
            Log.e("LoginActivity", errorMsg)
        }
    }

    private fun handleNetworkError() {
        val errorMsg = getString(R.string.network_error)
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
        Log.e("LoginActivity", errorMsg)
    }


    // Preferencias compartidas
    private fun saveToSharedPrefs(username: String?, role: String?, token: String?) {
        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString("username", username)
            putString("role", role)
            putString("token", token)
            apply()
        }
    }
}
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.model.request.LoginRequest
import pe.edu.idat.appborabora.data.model.response.LoginResponse
import pe.edu.idat.appborabora.data.network.BoraBoraClient
import pe.edu.idat.appborabora.view.HomeNavigation
import pe.edu.idat.appborabora.viewmodel.LoginState
import pe.edu.idat.appborabora.viewmodel.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class Login : AppCompatActivity() {

    private lateinit var tUser: EditText
    private lateinit var tPassword: EditText
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tUser = findViewById(R.id.tUsername)
        tPassword = findViewById(R.id.tPassword)

        val btnlogin = findViewById<Button>(R.id.btnlogin)
        btnlogin.setOnClickListener { login() }

        val tvregistrolog = findViewById<TextView>(R.id.tvregisterlog)
        tvregistrolog.setOnClickListener { register() }

        // Animacion
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        findViewById<ConstraintLayout>(R.id.innerConstraintLayout).startAnimation(slideUp)

        // Manejar click cerrar
        findViewById<ImageButton>(R.id.closeButton).setOnClickListener {
            finish()
        }

        // Inicializar ViewModel
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Observar cambios en loginState
        loginViewModel.loginState.observe(this, Observer { loginState ->
            when (loginState) {
                is LoginState.Success -> {
                    saveToSharedPrefs(loginState.username, loginState.role, loginState.token)
                    startHomeNavigation()
                }
                is LoginState.Error -> {
                    Toast.makeText(this, loginState.error, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun login() {
        val username = tUser.text.toString()
        val password = tPassword.text.toString()

        if (username.isNotBlank() && password.isNotBlank()) {
            loginViewModel.login(username, password)
        } else {
            Toast.makeText(this, "Ingrese el usuario y la contraseña", Toast.LENGTH_LONG).show()
        }
    }

    private fun register() {
        val intent = Intent(this, RegisterUser::class.java)
        startActivity(intent)
    }



    private fun startHomeNavigation() {
        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val role = sharedPref.getString("role", "")

        val intent = when (role) {
            "ROLE_ADMIN" -> Intent(this, AdminPanel::class.java)
            "ROLE_USER" -> Intent(this, HomeNavigation::class.java)
            else -> Intent(this, HomeNavigation::class.java)
        }

        startActivity(intent)
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
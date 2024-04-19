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
import pe.edu.idat.appborabora.view.HomeNavigation
import pe.edu.idat.appborabora.viewmodel.LoginState
import pe.edu.idat.appborabora.viewmodel.LoginViewModel
import pe.edu.idat.appborabora.viewmodel.UserViewModel

class Login : AppCompatActivity() {

    private lateinit var tUser: EditText
    private lateinit var tPassword: EditText
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userViewModel: UserViewModel

    private val sPUserLogged by lazy { getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tUser = findViewById(R.id.usernameInputLayout)
        tPassword = findViewById(R.id.passwordInputLayout)

        val btnlogin = findViewById<Button>(R.id.btnlogin)
        btnlogin.setOnClickListener { login() }

        val tvregistrolog = findViewById<TextView>(R.id.tvregisterlog)
        tvregistrolog.setOnClickListener { register() }

        val tvpassword = findViewById<TextView>(R.id.tvpassword)
        tvpassword.setOnClickListener { updatePassword() }

        // Animacion
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        findViewById<ConstraintLayout>(R.id.innerConstraintLayout).startAnimation(slideUp)

        // Manejar click cerrar
        findViewById<ImageButton>(R.id.closeButton).setOnClickListener {
            finish()
        }

        // Inicializar ViewModel
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Observar cambios en loginState
        loginViewModel.loginState.observe(this, Observer { loginState ->
            when (loginState) {
                is LoginState.Success -> {

                    // Limpiar los campos de texto
                    tUser.text.clear()
                    tPassword.text.clear()
                    saveToSharedPrefs(loginState.username, loginState.role, loginState.jwt, loginState.identityDoc)
                    saveDataUser()
                    startHomeNavigation()
                }
                is LoginState.Error -> {
                    Toast.makeText(this, loginState.error, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun saveDataUser() {

        val usernameUsuario = sPUserLogged.getString("username", null)

        if (usernameUsuario != null) {
            userViewModel.getUserByUsername(usernameUsuario)

            userViewModel.userProfileResponse.observe(this, Observer { userProfileResponse ->

                saveToSharedPrefsUser(userProfileResponse.name, userProfileResponse.lastname,
                    userProfileResponse.email, userProfileResponse.cellphone.toString())
            })
        }
    }

    private fun saveToSharedPrefsUser(name: String?, lastname: String?, email: String?, cellphone: String?) {
        sPUserLogged.edit().apply {
            putString("fullname", name + "" + lastname)
            putString("email", email)
            putString("phone", cellphone)
            apply()
        }
    }
    private fun login() {
        val username = tUser.text.toString().trim()
        val password = tPassword.text.toString().trim()

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

    private fun updatePassword() {
        val intent = Intent(this, NewPassword::class.java)
        startActivity(intent)
    }


    private fun startHomeNavigation() {
        val role = sPUserLogged.getString("role", "")

        val intent = when (role) {
            "ROLE_ADMIN_FULL" -> Intent(this, HomeNavigation::class.java)
            "ROLE_ADMIN_BASIC" -> Intent(this, HomeNavigation::class.java)
            "ROLE_USER" -> Intent(this, HomeNavigation::class.java)
            else -> Intent(this, HomeNavigation::class.java)
        }

        startActivity(intent)
    }

    // Preferencias compartidas
    private fun saveToSharedPrefs(username: String?, role: String?, jwt: String?, identityDoc: Int) {
        sPUserLogged.edit().apply {
            putString("username", username)
            putString("role", role)
            putString("jwt", jwt)
            putString("identityDoc", identityDoc.toString())
            apply()
        }
    }
}
package pe.edu.idat.appborabora.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.request.CreateUserRequest
import pe.edu.idat.appborabora.viewmodel.CreateUserViewModel
import java.util.regex.Pattern

class RegisterUser : AppCompatActivity() {

    private lateinit var viewModel: CreateUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        viewModel = ViewModelProvider(this).get(CreateUserViewModel::class.java)

        val tNom = findViewById<EditText>(R.id.t_nom)
        val tApellido = findViewById<EditText>(R.id.t_apellido)
        val tDni = findViewById<EditText>(R.id.t_dni)
        val tCelular = findViewById<EditText>(R.id.t_celular)
        val tUsername = findViewById<EditText>(R.id.t_username)
        val tCorreo = findViewById<EditText>(R.id.t_correo)
        val tPassw = findViewById<EditText>(R.id.t_passw)
        val checkBox = findViewById<CheckBox>(R.id.cbcondicion1)
        val btnCrear = findViewById<Button>(R.id.btncrear)

        btnCrear.setOnClickListener {

            if (!validateName(tNom.text.toString()) ||
                !validateLastName(tApellido.text.toString()) ||
                !validateDni(tDni.text.toString()) ||
                !validateCelular(tCelular.text.toString()) ||
                !validateUserName(tUsername.text.toString()) ||
                !validateCorreo(tCorreo.text.toString()) ||
                !validatePassword(tPassw.text.toString()) ||
                !validateCheckbox(checkBox.isChecked)) {
                return@setOnClickListener
            }

            val userRequest = CreateUserRequest(
                identity_doc = tDni.text.toString().toInt(),
                name = tNom.text.toString(),
                lastname = tApellido.text.toString(),
                cellphone = tCelular.text.toString().toInt(),
                email = tCorreo.text.toString(),
                username = tUsername.text.toString(),
                password = tPassw.text.toString(),
                roles = setOf("user")
            )

            viewModel.createUser(userRequest)

            viewModel.registerResponseMutableLiveData.observe(this, { response ->
                try {
                    if (response.status) {
                        Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al crear usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun validateName(name: String): Boolean {
        if (name.isEmpty()) {
            Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validateLastName(lastName: String): Boolean {
        if (lastName.isEmpty()) {
            Toast.makeText(this, "El apellido es obligatorio", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validateUserName(username: String): Boolean {
        if (username.isEmpty()) {
            Toast.makeText(this, "El usuario es obligatorio", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validateDni(dni: String): Boolean {
        if (dni.isEmpty() || dni.length != 8) {
            Toast.makeText(this, "El DNI debe tener 8 dígitos", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validateCelular(celular: String): Boolean {
        if (celular.isEmpty() || celular.length != 9 || celular[0] != '9') {
            Toast.makeText(this, "Por favor, introduce un número de celular válido", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validateCorreo(correo: String): Boolean {
        if (correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Por favor, introduce un correo electrónico válido", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validatePassword(password: String): Boolean {
        val passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[,.-@#$%^&+=])(?=\\S+$).{8,15}$")
        if (password.isEmpty() || !passwordPattern.matcher(password).matches()) {
            Toast.makeText(this, "La contraseña debe contener entre 8 y 15 caracteres, incluyendo números, letras mayúsculas, y caracteres especiales", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validateCheckbox(isChecked: Boolean): Boolean {
        if (!isChecked) {
            Toast.makeText(this, "Debes aceptar los terminos y condiciones", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}

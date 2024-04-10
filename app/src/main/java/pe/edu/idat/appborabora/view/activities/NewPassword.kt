package pe.edu.idat.appborabora.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.request.PasswordUpdateRequest
import pe.edu.idat.appborabora.viewmodel.UpdatePasswordViewModel

class NewPassword : AppCompatActivity() {

    private lateinit var viewModel: UpdatePasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)

        viewModel = ViewModelProvider(this).get(UpdatePasswordViewModel::class.java)

        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtCelular = findViewById<EditText>(R.id.txtCelular)
        val txtDni = findViewById<EditText>(R.id.txtDni)
        val txtPassword = findViewById<EditText>(R.id.txtPassword)
        val btnActualizar = findViewById<Button>(R.id.btnActualizar)

        btnActualizar.setOnClickListener {
            val email = txtEmail.text.toString()
            val cellphone = txtCelular.text.toString().toInt()
            val identityDoc = txtDni.text.toString().toInt()
            val newPassword = txtPassword.text.toString()

            // Verificar si se ingresaron todos los datos necesarios
            if (email.isNotEmpty() && cellphone > 0 && identityDoc > 0 && newPassword.isNotEmpty()) {
                // Validar la contraseña
                val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[.-@#$%^&+=])(?=\\S+$).{8,15}$"
                if (!newPassword.matches(passwordPattern.toRegex())) {
                    Toast.makeText(this, "La contraseña debe contener entre 8 y 15 caracteres, incluyendo mayúsculas, números y caracteres especiales.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Crear el objeto PasswordUpdateRequest
                val passwordUpdateRequest = PasswordUpdateRequest(email, cellphone, identityDoc, newPassword)

                // Llamar a la función actualizarContraseña del ViewModel
                viewModel.updatePassword(passwordUpdateRequest)

                // Observar los cambios en la respuesta de la actualización de contraseña
                viewModel.updatePasswordResponseMutableLiveData.observe(this) { response ->
                    if (response != null) {
                        // Manejar la respuesta de la solicitud de cambio de contraseña
                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()

                        // Si la actualización de la contraseña fue exitosa, navegar a la actividad de inicio de sesión
                        if (response.status == 200) {
                            val intent = Intent(this, Login::class.java)
                            startActivity(intent)
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
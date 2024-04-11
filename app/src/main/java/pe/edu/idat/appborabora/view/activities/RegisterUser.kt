package pe.edu.idat.appborabora.view.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.request.CreateUserRequest
import pe.edu.idat.appborabora.viewmodel.CreateUserViewModel

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
        val tCorreo = findViewById<EditText>(R.id.t_correo)
        val tPassw = findViewById<EditText>(R.id.t_passw)
        val btnCrear = findViewById<Button>(R.id.btncrear)

        btnCrear.setOnClickListener {
            val userRequest = CreateUserRequest(
                identity_doc = tDni.text.toString().toInt(),
                name = tNom.text.toString(),
                lastname = tApellido.text.toString(),
                cellphone = tCelular.text.toString().toInt(),
                email = tCorreo.text.toString(),
                username = tCorreo.text.toString(),
                password = tPassw.text.toString(),
                roles = setOf("user")
            )

            viewModel.createUser(userRequest)

            viewModel.registerResponseMutableLiveData.observe(this, { response ->
                try {
                    if (response.status) {
                        Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al crear usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}


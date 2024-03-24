package pe.edu.idat.appborabora.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.model.response.CreateUser
import pe.edu.idat.appborabora.data.model.response.PerfilResponse
import pe.edu.idat.appborabora.databinding.FragmentPerfilBinding
import pe.edu.idat.appborabora.viewmodel.PerfilViewModel

class Perfil : Fragment() {

    private lateinit var authViewModel: PerfilViewModel
    private lateinit var btnActualizarPerfil: Button
    private lateinit var nombreEditText: EditText
    private lateinit var apellidoEditText: EditText
    private lateinit var dniEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var celularEditText: EditText
    private lateinit var binding: FragmentPerfilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val view = binding.root

        authViewModel = ViewModelProvider(this).get(PerfilViewModel::class.java)

        // Inicializa las variables con las referencias a los EditTexts
        nombreEditText = view.findViewById(R.id.nombre)
        apellidoEditText = view.findViewById(R.id.apellido)
        dniEditText = view.findViewById(R.id.dni)
        emailEditText = view.findViewById(R.id.email)
        celularEditText = view.findViewById(R.id.celular)
        btnActualizarPerfil = view.findViewById(R.id.btnActualizarPerfil)

        listarUsuario(view)
        btnActualizarPerfil.setOnClickListener {
            actualizarPerfil()
        }

        authViewModel.observeUpdatePerfilResponse().observe(viewLifecycleOwner) { perfilResponse ->
            perfilResponse?.let {
                // Maneja el PerfilResponse aquí, muestra el mensaje en la interfaz de usuario
                //Toast.makeText(requireContext(), perfilResponse.message, Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun saveToSharedPrefs(name: String?, lastname: String?, email: String?, identity_doc: Int, cellphone: Int) {
        val sharedPref = requireActivity().getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString("name", name)
            putString("lastname", lastname)
            putInt("identity_doc", identity_doc)
            putString("email", email)
            putInt("cellphone", cellphone)
            apply()
        }
    }

    private fun listarUsuario(view: View) {
        //PARA OBTENER EL ID DEL USUARIO:
        val sharedPref = requireActivity().getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE)

        // Obtener la referencia de la vista del fragmento para buscar las vistas
        val username = view.findViewById<TextView>(R.id.nombre)
        val userapellido = view.findViewById<TextView>(R.id.apellido)
        val userDni = view.findViewById<TextView>(R.id.dni)
        val userEmail = view.findViewById<TextView>(R.id.email)
        val userCelular = view.findViewById<TextView>(R.id.celular)

        val dniUsuario = sharedPref.getInt("identity_doc", 0)
        val nombreUsuario = sharedPref.getString("name", null)
        val apellidoUsuario = sharedPref.getString("lastname", null)
        val emailUsuario = sharedPref.getString("email", null)
        val cellUsuario = sharedPref.getInt("cellphone", 0)


        if (nombreUsuario != null && apellidoUsuario != null && emailUsuario != null) {
            username.text = nombreUsuario
            userapellido.text = apellidoUsuario
            userEmail.text = emailUsuario

            // Convierte los valores int a String antes de ponerlos en los TextViews
            val dniUsuarioString = dniUsuario.toString()
            val cellUsuarioString = cellUsuario.toString()

            userDni.text = dniUsuarioString
            userCelular.text = cellUsuarioString
        }
    }

    private fun actualizarPerfil() {
        if (validarFormulario()) {
            val name = nombreEditText.text.toString()
            val lastname = apellidoEditText.text.toString()
            val identity_doc = dniEditText.text.toString().toInt()
            val email = emailEditText.text.toString()
            val cellphone = celularEditText.text.toString().toInt()

            val createUser = CreateUser(identity_doc, name, lastname, cellphone, email)
            // Llama al método en el ViewModel para actualizar el perfil
            authViewModel.actualizarPerfil(identity_doc, createUser)

            saveToSharedPrefs(name, lastname, email, identity_doc, cellphone)
        }
    }

    private fun validarFormulario(): Boolean {
        var respuesta = false

        if (!ingresoNombre()) {
            binding.nombre.error = "Ingrese su nombre"
        } else if (!ingresoApellido()) {
            binding.apellido.error = "Ingrese su apellido"
        } else if (!ingresoDni()) {
            binding.dni.error = "Ingrese su DNI"
        } else if (!validarDni()) {
            binding.dni.error = "El DNI debe tener 8 digitos"
        } else if (!ingresoCelular()) {
            binding.celular.error = "Ingrese su número de celular"
        } else if (!validarNumeroCelular()) {
            binding.celular.error = "Numero de celular invalido"
        } else if (!ingresoEmail()) {
            binding.email.error = "Ingrese su correo electrónico"
        } else if (!validarEmail()) {
            binding.email.error = "Correo invalido"
        } else {
            respuesta = true
        }
        return respuesta
    }

    //--VALIDACIONES
    private fun ingresoNombre(): Boolean {
        var respuesta = true
        val nombre = binding.nombre.text.toString().trim()
        if (nombre.isEmpty()) {
            binding.nombre.isFocusableInTouchMode = true
            binding.nombre.requestFocus()
            respuesta = false
        }
        return respuesta
    }

    private fun ingresoApellido(): Boolean {
        var respuesta = true
        val apellido = binding.apellido.text.toString().trim()
        if (apellido.isEmpty()) {
            binding.apellido.isFocusableInTouchMode = true
            binding.apellido.requestFocus()
            respuesta = false
        }
        return respuesta
    }

    private fun ingresoDni(): Boolean {
        var respuesta = true
        val dni = binding.dni.text.toString().trim()
        if (dni.isEmpty()) {
            binding.dni.isFocusableInTouchMode = true
            binding.dni.requestFocus()
            respuesta = false
        }
        return respuesta
    }

    private fun validarDni(): Boolean {
        val dni = binding.dni.text.toString().trim()
        // Verifica que el DNI tenga exactamente 8 dígitos y sean todos números
        return dni.length == 8 && esNumero(dni)
    }

    private fun esNumero(str: String): Boolean {
        return try {
            str.toLong()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun ingresoCelular(): Boolean {
        val telefono = binding.celular.text.toString().trim()
        return telefono.isNotEmpty()
    }

    private fun validarNumeroCelular(): Boolean {
        val numeroCelular = binding.celular.text.toString().trim()
        // valida que el número de celular comience con '9' y tenga 9 dígitos en total
        return numeroCelular.matches("^9\\d{8}$".toRegex())
    }

    private fun ingresoEmail(): Boolean {
        val correo = binding.email.text.toString().trim()
        return correo.isNotEmpty()
    }

    private fun validarEmail(): Boolean {
        val correo = binding.email.text.toString().trim()
        // valida el formato de un correo electrónico
        val patronCorreo = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        // Verifica que el correo cumple con el patrón
        return correo.matches(patronCorreo)
    }
}
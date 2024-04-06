package pe.edu.idat.appborabora.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.json.JSONObject
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.request.CreateUserRequest
import pe.edu.idat.appborabora.databinding.FragmentPerfilBinding
import pe.edu.idat.appborabora.viewmodel.PerfilViewModel
import pe.edu.idat.appborabora.viewmodel.UpdateUserViewModel
import pe.edu.idat.appborabora.viewmodel.ViewModelFactory

class Perfil : Fragment() {

    private lateinit var perfilViewModel: PerfilViewModel
    private lateinit var updateUserViewModel: UpdateUserViewModel
    private lateinit var btnActualizarPerfil: Button
    private lateinit var nombreEditText: EditText
    private lateinit var apellidoEditText: EditText
    private lateinit var dniEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var celularEditText: EditText
    private lateinit var binding: FragmentPerfilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val view = binding.root

        perfilViewModel = ViewModelProvider(this).get(PerfilViewModel::class.java)

        // Inicializa las variables con las referencias a los EditTexts
        nombreEditText = view.findViewById(R.id.nombre)
        apellidoEditText = view.findViewById(R.id.apellido)
        dniEditText = view.findViewById(R.id.dni)
        emailEditText = view.findViewById(R.id.email)
        usernameEditText = view.findViewById(R.id.username)
        celularEditText = view.findViewById(R.id.celular)
        btnActualizarPerfil = view.findViewById(R.id.btnActualizarPerfil)

        listarUsuario(view)

        //--
        val factory = ViewModelFactory(requireContext())
        updateUserViewModel = ViewModelProvider(this, factory).get(UpdateUserViewModel::class.java)

        btnActualizarPerfil.setOnClickListener {
            if (validarFormulario()) {
                val createUserRequest = CreateUserRequest(
                    nombreEditText.text.toString(),
                    apellidoEditText.text.toString(),
                    celularEditText.text.toString().toInt(),
                    emailEditText.text.toString(),
                    usernameEditText.text.toString(),

                )

                updateUserViewModel.updateUser(dniEditText.text.toString().toInt(), createUserRequest)
            }
        }

        updateUserViewModel.apiResponse.observe(viewLifecycleOwner, Observer { apiResponse ->
            Log.d("PerfilFragment", "ApiResponse: $apiResponse")
            Toast.makeText(context, apiResponse.message, Toast.LENGTH_LONG).show()
        })

        updateUserViewModel.error.observe(viewLifecycleOwner, Observer { error ->
            val jsonObject = JSONObject(error)
            val message = jsonObject.getString("message")
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        })
        //--
        return view
    }

    private fun listarUsuario(view: View) {

        //PARA OBTENER EL USERNMA DEL USUARIO LOGUEADO:
        val sharedPref = requireActivity().getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE)

        // Obtener la referencia de la vista del fragmento para buscar las vistas
        val username = view.findViewById<TextView>(R.id.nombre)
        val userapellido = view.findViewById<TextView>(R.id.apellido)
        val userDni = view.findViewById<TextView>(R.id.dni)
        val userEmail = view.findViewById<TextView>(R.id.email)
        val nameuser = view.findViewById<TextView>(R.id.username)
        val userCelular = view.findViewById<TextView>(R.id.celular)

        val usernameUsuario = sharedPref.getString("username", null)

        if (usernameUsuario != null) {
            perfilViewModel.getUserByUsername(usernameUsuario)
            perfilViewModel.user.observe(viewLifecycleOwner, Observer { user ->
                username.text = user.name
                userapellido.text = user.lastname
                userEmail.text = user.email
                nameuser.text = user.username
                userCelular.text = user.cellphone.toString()
                userDni.text = user.identityDoc.toString()
            })
        }
    }

    //--Falta validar el username

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
package pe.edu.idat.appborabora.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.request.CreateUserRequest
import pe.edu.idat.appborabora.databinding.FragmentPerfilBinding
import pe.edu.idat.appborabora.viewmodel.UserViewModel
import pe.edu.idat.appborabora.viewmodel.ViewModelFactory


class UserProfile : Fragment() {

    private lateinit var userViewModel: UserViewModel
    /*private lateinit var updateUserViewModel: UpdateUserViewModel*/
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

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        setupViews(view)
        listUser(view)

        setupUpdateButton()


        return view
    }

    // Inicializa las vistas en el fragmento
    private fun setupViews(view: View) {
        nombreEditText = view.findViewById(R.id.nombre)
        apellidoEditText = view.findViewById(R.id.apellido)
        dniEditText = view.findViewById(R.id.dni)
        emailEditText = view.findViewById(R.id.email)
        usernameEditText = view.findViewById(R.id.username)
        celularEditText = view.findViewById(R.id.celular)
        btnActualizarPerfil = view.findViewById(R.id.btnActualizarPerfil)
    }

    // Configura el botón de guardar para crear un producto cuando se haga clic en él
    private fun setupUpdateButton() {

        val viewModelFactory = ViewModelFactory(requireActivity().application)
        /*val productViewModel = ViewModelProvider(this, viewModelFactory).get(ProductViewModel::class.java)*/

        btnActualizarPerfil.setOnClickListener {
            if (validateForm()) {
                val createUserRequest = CreateUserRequest(
                    nombreEditText.text.toString(),
                    apellidoEditText.text.toString(),
                    celularEditText.text.toString().toInt(),
                    emailEditText.text.toString(),
                    usernameEditText.text.toString(),
                )
            }
        }
    }

    private fun listUser(view: View) {

        //--Preferencias compartidas
        val sharedPref = requireActivity().getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE)

        val username = view.findViewById<TextView>(R.id.nombre)
        val userapellido = view.findViewById<TextView>(R.id.apellido)
        val userDni = view.findViewById<TextView>(R.id.dni)
        val userEmail = view.findViewById<TextView>(R.id.email)
        val nameuser = view.findViewById<TextView>(R.id.username)
        val userCelular = view.findViewById<TextView>(R.id.celular)

        val usernameUsuario = sharedPref.getString("username", null)

        if (usernameUsuario != null) {
            userViewModel.getUserByUsername(usernameUsuario)

            userViewModel.userProfileResponse.observe(viewLifecycleOwner, Observer { userProfileResponse ->

                username.text = userProfileResponse.username
                userapellido.text = userProfileResponse.lastname
                userDni.text = userProfileResponse.identityDoc.toString()
                userEmail.text = userProfileResponse.email
                nameuser.text = userProfileResponse.username
                userCelular.text = userProfileResponse.cellphone.toString()

            })
        }
    }

    //--Falta validar el username

    private fun validateForm(): Boolean {
        var response = false

        if (!entryUsername()) {
            binding.username.error = "Ingrese su username"
        } else if (!entryEmail()) {
            binding.email.error = "Ingrese su correo electrónico"
        } else if (!validateEmail()) {
            binding.email.error = "Correo invalido"
        } else if (!entryName()) {
            binding.nombre.error = "Ingrese su nombre"
        } else if (!entryLastName()) {
            binding.apellido.error = "Ingrese su apellido"
        } else if (!entryPhone()) {
            binding.celular.error = "Ingrese su número de celular"
        } else if (!validatePhone()) {
            binding.celular.error = "Numero de celular invalido"
        } else {
            response = true
        }
        return response
    }

    //--VALIDACIONES
    private fun entryUsername(): Boolean {
        var response = true
        val username = binding.username.text.toString().trim()
        if (username.isEmpty()) {
            binding.username.isFocusableInTouchMode = true
            binding.username.requestFocus()
            response = false
        }
        return response
    }

    private fun entryEmail(): Boolean {
        val email = binding.email.text.toString().trim()
        return email.isNotEmpty()
    }

    private fun validateEmail(): Boolean {
        val email = binding.email.text.toString().trim()
        // valida el formato de un correo electrónico
        val patEmail = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        // Verifica que el correo cumple con el patrón
        return email.matches(patEmail)
    }

    private fun entryName(): Boolean {
        var response = true
        val name = binding.nombre.text.toString().trim()
        if (name.isEmpty()) {
            binding.nombre.isFocusableInTouchMode = true
            binding.nombre.requestFocus()
            response = false
        }
        return response
    }

    private fun entryLastName(): Boolean {
        var response = true
        val lastName = binding.apellido.text.toString().trim()
        if (lastName.isEmpty()) {
            binding.apellido.isFocusableInTouchMode = true
            binding.apellido.requestFocus()
            response = false
        }
        return response
    }

    private fun entryPhone(): Boolean {
        val phone = binding.celular.text.toString().trim()
        return phone.isNotEmpty()
    }

    private fun validatePhone(): Boolean {
        val numberPhone = binding.celular.text.toString().trim()
        // valida que el número de celular comience con '9' y tenga 9 dígitos en total
        return numberPhone.matches("^9\\d{8}$".toRegex())
    }


}
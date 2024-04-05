package pe.edu.idat.appborabora.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.viewmodel.ProductViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CrearProducto : Fragment() {

    private lateinit var productNameEditText: TextInputEditText
    private lateinit var productDescriptionEditText: TextInputEditText
    private lateinit var priceEditText: TextInputEditText
    private lateinit var stockEditText: TextInputEditText
    private lateinit var expirationDateEditText: TextInputEditText
    private lateinit var imageEditText: TextInputEditText
    private lateinit var saveButton: Button
    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crear_producto, container, false)

        // Inicializar vistas
        productNameEditText = view.findViewById(R.id.etProductName)
        productDescriptionEditText = view.findViewById(R.id.etProductDescription)
        priceEditText = view.findViewById(R.id.etPrice)
        stockEditText = view.findViewById(R.id.etStock)
        expirationDateEditText = view.findViewById(R.id.etExpirationDate)
        imageEditText = view.findViewById(R.id.etImagen)
        saveButton = view.findViewById(R.id.btnSave)

        // Inicializar ViewModel
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        // Configurar clic del botón
        saveButton.setOnClickListener {
            guardarProducto()
        }

        return view
    }

    private fun guardarProducto() {
        val productName = productNameEditText.text.toString()
        val productDescription = productDescriptionEditText.text.toString()
        val price = priceEditText.text.toString().toDoubleOrNull()
        val stock = stockEditText.text.toString().toIntOrNull()
        val expirationDateText = expirationDateEditText.text.toString()
        val image = imageEditText.text.toString()

        // Convertir la fecha de caducidad ingresada a un objeto LocalDate
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Ajusta el patrón según el formato de fecha de tu EditText
        val expirationDate = LocalDate.parse(expirationDateText, formatter)

        // Verificar si se ingresaron datos válidos
        if (productName.isNotEmpty() && productDescription.isNotEmpty() && price != null &&
            stock != null && expirationDateText.isNotEmpty() && image.isNotEmpty()) {
            val productDTO = ProductDTO(
                idProduct = 0, // Suponiendo que 0 indica un nuevo producto sin ID asignado
                name = productName,
                description = productDescription,
                price = price,
                stock = stock,
                expirationDate = expirationDate,
                image = image,
                categoryId = 1,
                brandProductId = 1
            )
            productViewModel.createProduct(productDTO)
        }
    }
}

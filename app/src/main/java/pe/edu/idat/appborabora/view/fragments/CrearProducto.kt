package pe.edu.idat.appborabora.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.viewmodel.BrandProductViewModel
import pe.edu.idat.appborabora.viewmodel.CategoryViewModel
import pe.edu.idat.appborabora.viewmodel.ProductViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import java.io.ByteArrayOutputStream


class CrearProducto : Fragment() {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var brandProductViewModel: BrandProductViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var tilProductName: TextInputLayout
    private lateinit var etProductName: TextInputEditText
    private lateinit var tilProductDescription: TextInputLayout
    private lateinit var etProductDescription: TextInputEditText
    private lateinit var tilPrice: TextInputLayout
    private lateinit var etPrice: TextInputEditText
    private lateinit var tilStock: TextInputLayout
    private lateinit var etStock: TextInputEditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerBrandProduct: Spinner
    private lateinit var tilExpirationDate: TextInputLayout
    private lateinit var etExpirationDate: TextInputEditText
    private lateinit var tilImagen: TextInputLayout
    private lateinit var etImagen: ImageView
    private lateinit var btnSave: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crear_producto, container, false)

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        brandProductViewModel = ViewModelProvider(this).get(BrandProductViewModel::class.java)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        tilProductName = view.findViewById(R.id.tilProductName)
        etProductName = view.findViewById(R.id.etProductName)
        tilProductDescription = view.findViewById(R.id.tilProductDescription)
        etProductDescription = view.findViewById(R.id.etProductDescription)
        tilPrice = view.findViewById(R.id.tilPrice)
        etPrice = view.findViewById(R.id.etPrice)
        tilStock = view.findViewById(R.id.tilStock)
        etStock = view.findViewById(R.id.etStock)
        spinnerCategory = view.findViewById(R.id.spinnerCategory)
        spinnerBrandProduct = view.findViewById(R.id.spinnerBrand) // Corrección en el ID
        tilExpirationDate = view.findViewById(R.id.tilExpirationDate)
        etExpirationDate = view.findViewById(R.id.etExpirationDate)
        tilImagen = view.findViewById(R.id.tilImagen)
        etImagen = view.findViewById(R.id.ivImagen)
        btnSave = view.findViewById(R.id.btnSave)


        // Observa la lista de categorías y actualiza el spinner cuando cambie
        categoryViewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            val categoryNames = categories.map { it.name }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = adapter
        })

        // Obtiene las categorías del ViewModel
        categoryViewModel.fetchAllCategories()

        // Observa la lista de marcas de productos y actualiza el spinner cuando cambie
        brandProductViewModel.brandProducts.observe(viewLifecycleOwner, Observer { brandProducts ->
            val brandProductNames = brandProducts.map { it.brand_product }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, brandProductNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerBrandProduct.adapter = adapter
        })

        // Obtiene las marcas de productos del ViewModel
        brandProductViewModel.fetchAllBrandProducts()

        btnSave.setOnClickListener {
            val productName = etProductName.text.toString()
            val productDescription = etProductDescription.text.toString()
            val price = etPrice.text.toString().toDouble()
            val stock = etStock.text.toString().toInt()
            // Obtener la fecha de expiración como una cadena
            val expirationDateText = etExpirationDate.text.toString()

            // Convertir la fecha de expiración de String a LocalDate
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Ajusta el patrón según el formato de fecha de tu EditText
            val expirationDate = LocalDate.parse(expirationDateText, formatter)

            // Obtén la imagen como un ByteArray desde el ImageView
            val bitmap = (etImagen.drawable as BitmapDrawable).bitmap
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()

            // Convierte el byteArray a una cadena Base64
            val base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT)

            val categoryId = spinnerCategory.selectedItemPosition // Obtén la categoría seleccionada
            val brandProductId = spinnerBrandProduct.selectedItemPosition // Obtén la marca del producto seleccionada
            // Crea el objeto ProductDTO con los datos obtenidos

            val productDTO = ProductDTO(
                name = productName,
                description = productDescription,
                price = price,
                stock = stock,
                expirationDate = expirationDate,
                image = base64Image,
                categoryId = categoryId,
                brandProductId = brandProductId
            )

            productViewModel.createProduct(productDTO)
        }

        return view
    }
}



package pe.edu.idat.appborabora.view.fragments

import android.util.Base64
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.app.DatePickerDialog
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.response.BrandProductDTO
import pe.edu.idat.appborabora.data.dto.response.CategoryResponse
import pe.edu.idat.appborabora.viewmodel.BrandProductViewModel
import pe.edu.idat.appborabora.viewmodel.CategoryViewModel
import pe.edu.idat.appborabora.viewmodel.EliminarProductoViewModel
import java.util.Calendar
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.viewmodel.ActualizarProductoViewModel
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ActualizarProducto : Fragment() {
    private lateinit var viewModel: EliminarProductoViewModel
    private lateinit var brandProductViewModel: BrandProductViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var actualizarViewModel: ActualizarProductoViewModel

    private var selectedDate: LocalDate? = null
    private var categories: List<CategoryResponse> = listOf()
    private var brandProducts: List<BrandProductDTO> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(EliminarProductoViewModel::class.java)
        brandProductViewModel = ViewModelProvider(this).get(BrandProductViewModel::class.java)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        actualizarViewModel = ViewModelProvider(this).get(ActualizarProductoViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_actualizar_producto, container, false)

        // Obtener referencias a los campos del formulario
        val etProductName = view.findViewById<EditText>(R.id.etProductName)
        val etProductDescription = view.findViewById<EditText>(R.id.etProductDescription)
        val etPrice = view.findViewById<EditText>(R.id.etPrice)
        val etStock = view.findViewById<EditText>(R.id.etStock)
        val spinnerCategory = view.findViewById<Spinner>(R.id.spinnerCategory)
        val spinnerBrand = view.findViewById<Spinner>(R.id.spinnerBrand)
        val tvSelectedDate = view.findViewById<TextView>(R.id.tvSelectedDate)
        val ivImagen = view.findViewById<ImageView>(R.id.ivImagen)

        val btnExamine = view.findViewById<Button>(R.id.examineButton)

        // Fetch categories
        categoryViewModel.fetchAllCategories()

        // Fetch brands
        brandProductViewModel.fetchAllBrandProducts()

        // Obtener categorías
        categoryViewModel.categories.observe(viewLifecycleOwner, Observer<List<CategoryResponse>> { categories ->
            this.categories = categories
            val categoryNames = categories.map { it.name }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryNames)
            spinnerCategory.adapter = adapter
        })

        // Obtener marca
        brandProductViewModel.brandProducts.observe(viewLifecycleOwner, Observer<List<BrandProductDTO>> { brandProducts ->
            this.brandProducts = brandProducts
            val brandNames = brandProducts.map { it.brand_product }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, brandNames)
            spinnerBrand.adapter = adapter
        })

        tvSelectedDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formattedDate = selectedDate?.format(formatter) ?: "sin fecha"
                tvSelectedDate.text = formattedDate
            }, year, month, day).show()
        }

        // Obtener el producto cuando se hace clic en el botón
        btnExamine.setOnClickListener {
            val productIdText = view.findViewById<EditText>(R.id.productId).text.toString()
            if (productIdText.isNotEmpty()) {
                val productId = productIdText.toInt()
                viewModel.getProductById(productId)
            } else {
                Toast.makeText(context, "Por favor, introduce un ID de producto", Toast.LENGTH_SHORT).show()
            }
        }

        //llenar los elementos con sus valores
        viewModel.product.observe(viewLifecycleOwner, Observer { product ->
            etProductName.setText(product.name)
            etProductDescription.setText(product.description)
            etPrice.setText(product.price.toString())
            etStock.setText(product.stock.toString())

            val categoryIndex = categories.indexOfFirst { it.id_category == product.categoryId }
            spinnerCategory.setSelection(categoryIndex)

            val brandIndex = brandProducts.indexOfFirst { it.cod_brand_product == product.brandProductId }
            spinnerBrand.setSelection(brandIndex)

            tvSelectedDate.text = product.expirationDate
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val expirationDate = selectedDate?.format(formatter) ?: "sin fecha"

            // Decodificar y mostrar la imagen
            val decodedString = Base64.decode(product.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            ivImagen.setImageBitmap(decodedByte)
        })

        viewModel.message.observe(viewLifecycleOwner, Observer { message ->
            // Mostrar el mensaje de error
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        })

        //boton actualizar
        val btnSave = view.findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
            val name = etProductName.text.toString()
            val description = etProductDescription.text.toString()
            val price = etPrice.text.toString().toDouble()
            val stock = etStock.text.toString().toInt()
            val expirationDate = tvSelectedDate.text.toString()
            //imagen a base64
            val bitmap = (ivImagen.drawable as BitmapDrawable).bitmap
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val image = Base64.encodeToString(byteArray, Base64.DEFAULT)

            val categoryId = categories[spinnerCategory.selectedItemPosition].id_category
            val brandProductId = brandProducts[spinnerBrand.selectedItemPosition].cod_brand_product

            val productDTO = ProductDTO(name, description, price, stock, expirationDate, image, categoryId, brandProductId)

            val productIdText = view.findViewById<EditText>(R.id.productId).text.toString()
            if (productIdText.isNotEmpty()) {
                val productId = productIdText.toInt()
                actualizarViewModel.updateProduct(productId, productDTO)
            } else {
                Toast.makeText(context, "Por favor, introduce un ID de producto", Toast.LENGTH_SHORT).show()
            }
        }

        actualizarViewModel.message.observe(viewLifecycleOwner, Observer { message ->
            // Comprobar si el fragmento todavía está en primer plano
            if (isResumed) {
                // Mostrar el mensaje de error
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }


}
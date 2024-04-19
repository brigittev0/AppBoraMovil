package pe.edu.idat.appborabora.view.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.viewmodel.BrandProductViewModel
import pe.edu.idat.appborabora.viewmodel.CategoryViewModel
import pe.edu.idat.appborabora.viewmodel.ProductViewModel
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar


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
    private lateinit var tilImagen: TextInputLayout
    private lateinit var ivImagen: ImageView
    private lateinit var btnSave: Button

    private lateinit var btnShowDatePicker: AppCompatImageButton
    private lateinit var tvSelectedDate: TextView
    private var selectedDate: LocalDate? = null


    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crear_producto, container, false)

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        brandProductViewModel = ViewModelProvider(this).get(BrandProductViewModel::class.java)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        setupViews(view)
        setupCategorySpinner()
        setupBrandProductSpinner()

        setupSaveButton()

        return view
    }


    // Inicializa las vistas en el fragmento
    private fun setupViews(view: View) {
        tilProductName = view.findViewById(R.id.tilProductName)
        etProductName = view.findViewById(R.id.etProductName)
        tilProductDescription = view.findViewById(R.id.tilProductDescription)
        etProductDescription = view.findViewById(R.id.etProductDescription)
        tilPrice = view.findViewById(R.id.tilPrice)
        etPrice = view.findViewById(R.id.etPrice)
        tilStock = view.findViewById(R.id.tilStock)
        etStock = view.findViewById(R.id.etStock)
        spinnerCategory = view.findViewById(R.id.spinnerCategory)
        spinnerBrandProduct = view.findViewById(R.id.spinnerBrand)
        tilImagen = view.findViewById(R.id.tilImagen)
        ivImagen = view.findViewById(R.id.ivImagen)
        btnSave = view.findViewById(R.id.btnSave)
        btnShowDatePicker = view.findViewById(R.id.btnDatePicker)
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate)

        //Seleccion del boton subir imagen
        ivImagen.setOnClickListener {
            selectImage()
        }

        //Seleccion del boton fecha
        btnShowDatePicker.setOnClickListener {
            selectDate()
        }
    }

    // Configura el spinner para las categorías de productos
    private fun setupCategorySpinner() {
        // Observa la lista de categorías y actualiza el spinner cuando cambie
        categoryViewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            val categoryNames = categories.map { it.name }

            spinnerCategory.adapter = object : ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                categoryNames
            ) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent)
                    (view as TextView).setTextColor(resources.getColor(R.color.black))
                    return view
                }

                override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    (view as TextView).setTextColor(resources.getColor(R.color.black))
                    view.background = resources.getDrawable(R.drawable.spinner_dropdown_background)
                    return view
                }
            }
        })

        // Obtiene las categorías del ViewModel
        categoryViewModel.fetchAllCategories()
    }

    // Configura el spinner para las marcas de productos
    private fun setupBrandProductSpinner() {
        // Observa la lista de marcas de productos y actualiza el spinner cuando cambie
        brandProductViewModel.brandProducts.observe(viewLifecycleOwner, Observer { brandProducts ->
            val brandProductNames = brandProducts.map { it.brand_product }

            spinnerBrandProduct.adapter = object : ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                brandProductNames
            ) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent)
                    (view as TextView).setTextColor(resources.getColor(R.color.black))
                    return view
                }

                override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    (view as TextView).setTextColor(resources.getColor(R.color.black))
                    view.background = resources.getDrawable(R.drawable.spinner_dropdown_background)
                    return view
                }
            }
        })

        // Obtiene las marcas de productos del ViewModel
        brandProductViewModel.fetchAllBrandProducts()
    }

    // Configura el botón de guardar para crear un producto cuando se haga clic en él
    private fun setupSaveButton() {

        btnSave.setOnClickListener {
            try {
                Log.d("SaveButton", "Botón Guardar presionado")

                if (!validateForm()) {
                    return@setOnClickListener
                }

                val productName = etProductName.text.toString()
                val productDescription = etProductDescription.text.toString()
                val price = etPrice.text.toString().toDouble()
                val stock = etStock.text.toString().toInt()

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val expirationDate = selectedDate?.format(formatter) ?: "sin fecha"

                // Convertir la imagen a base64
                val bitmap = (ivImagen.drawable as BitmapDrawable).bitmap
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                val base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT)

                val selectedCategory = categoryViewModel.categories.value?.get(spinnerCategory.selectedItemPosition)
                val selectedBrandProduct = brandProductViewModel.brandProducts.value?.get(spinnerBrandProduct.selectedItemPosition)

                if (selectedCategory != null && selectedBrandProduct != null) {
                    val productDTO = ProductDTO(
                        name = productName,
                        description = productDescription,
                        price = price,
                        stock = stock,
                        expirationDate = expirationDate,
                        image = base64Image,
                        categoryId = selectedCategory.id_category,
                        brandProductId = selectedBrandProduct.cod_brand_product
                    )

                    Log.d("SaveButton", "Creando producto: $productDTO")
                    productViewModel.createProduct(productDTO)
                } else {
                    Log.d("SaveButton", "Categoría o marca de producto no seleccionada")
                    Toast.makeText(requireContext(), "Por favor, selecciona una categoría y una marca de producto", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("SaveButton", "Error al guardar el producto", e)
            }
        }

        //--OBSERVAR RESPUESTA DE LA API
        productViewModel.createProductResponse.observe(viewLifecycleOwner, Observer { apiResponse ->
            if (apiResponse.status == 201) {
                Log.d("SaveButton", "Producto creado con éxito")
                Toast.makeText(requireContext(), "Producto creado con éxito", Toast.LENGTH_SHORT).show()
            }
        })

        productViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) {
                Log.d("SaveButton", "Error al crear el producto: $errorMessage")
                Toast.makeText(requireContext(), "$errorMessage", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //--VALIDACIONES
    private fun validateForm(): Boolean {
        val productName = etProductName.text.toString()
        val productDescription = etProductDescription.text.toString()
        val price = etPrice.text.toString()
        val stock = etStock.text.toString()

        if (productName.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, ingrese el nombre del producto", Toast.LENGTH_SHORT).show()
            return false
        }

        if (productDescription.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, ingrese la descripción del producto", Toast.LENGTH_SHORT).show()
            return false
        }

        if (price.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, ingrese el precio del producto", Toast.LENGTH_SHORT).show()
            return false
        }

        if (stock.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, ingrese el stock del producto", Toast.LENGTH_SHORT).show()
            return false
        }

        if (selectedDate == null) {
            Toast.makeText(requireContext(), "Por favor, seleccione una fecha de vencimiento", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    // ---- SELECCIONAR FECHA
    private fun selectDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
            tvSelectedDate.setText("$selectedDate")
        }, year, month, day)

        datePickerDialog.show()
    }

    // ---- SELECCIONAR IMAGEN ----
    // Método para seleccionar una imagen, ya sea tomando una foto o eligiendo de la galería
    private fun selectImage() {
        val items = arrayOf<CharSequence>("Elegir de la galería", "Cancelar")
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Agregar imagen")
        builder?.setItems(items) { dialog, item ->
            when {
                items[item] == "Elegir de la galería" -> dispatchChooseFromGalleryIntent()
                items[item] == "Cancelar" -> dialog.dismiss()
            }
        }
        builder?.show()
    }

    // Método para abrir la galería y elegir una imagen
    private fun dispatchChooseFromGalleryIntent() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    // Método que se llama cuando una actividad que se inició con startActivityForResult() ha terminado
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Verifica si el intent no es nulo y si contiene datos
            if (data != null && data.data != null) {
                try {
                    // Obtiene la URI de la imagen seleccionada
                    val imageUri = data.data

                    // Lee la imagen como un bitmap
                    val imageBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)

                    // Muestra la imagen en el ImageView
                    ivImagen.setImageBitmap(imageBitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(requireContext(), "No se pudo obtener la imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
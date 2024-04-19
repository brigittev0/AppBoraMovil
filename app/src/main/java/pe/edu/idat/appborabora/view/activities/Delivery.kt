package pe.edu.idat.appborabora.view.activities

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import pe.edu.idat.appborabora.R

import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import java.time.LocalDate
import java.util.Calendar

class Delivery : AppCompatActivity() {

    private lateinit var spDistrito: Spinner
    private lateinit var etUbigeo: EditText
    private lateinit var etDireccion: EditText
    private lateinit var btnSaveDelivery: Button

    private lateinit var btnDateDelivery: AppCompatImageButton
    private lateinit var tvSelectDateDelivery: TextView
    private var selectedDate: LocalDate? = null

    private val sharedPreferences by lazy { getSharedPreferences("DeliveryPickup", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

        setupViews()
        setupToolbar()
        setupSpinner()
        setupDateButton()
        setupSaveButton()

        loadFormData()
    }

    //-- Inicializando
    private fun setupViews() {
        spDistrito = findViewById(R.id.spDistrito)
        etUbigeo = findViewById(R.id.etUbigeo)
        etDireccion = findViewById(R.id.etDireccion)
        btnSaveDelivery = findViewById(R.id.btnSaveDelivery)
        btnDateDelivery = findViewById(R.id.btnDateDelivery)
        tvSelectDateDelivery = findViewById(R.id.tvSelectDateDelivery)
    }

    //-- Toolbar
    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Envio a domicilio"
    }

    //-- Spinner
    private fun setupSpinner() {
        val distritos = arrayOf(
            "Asia",
            "Calango",
            "Cerro Azul",
            "Chilca",
            "Coayllo",
            "Imperial",
            "Lunahuaná",
            "Mala",
            "Nuevo Imperial",
            "Pacarán",
            "Quilmaná",
            "San Antonio",
            "San Luis",
            "San Vicente de Cañete",
            "Santa Cruz de Flores",
            "Zúñiga"
        )

        spDistrito.adapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            distritos
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
    }

    //-- Seleccion boton fecha
    private fun setupDateButton() {
        btnDateDelivery.setOnClickListener {
            selectDate()
        }
    }

    //-- Boton Guardar
    private fun setupSaveButton() {
        btnSaveDelivery.setOnClickListener {
            val departamento = "Lima"
            val provincia = "Cañete"
            val distrito = spDistrito.selectedItem as String
            val distritoPosition = spDistrito.selectedItemPosition
            val ubigeoIngresado = etUbigeo.text.toString()
            val direccionIngresada = etDireccion.text.toString()
            val fechaSeleccionada = selectedDate

            if (!validateFields(ubigeoIngresado, direccionIngresada, fechaSeleccionada)) {
                return@setOnClickListener
            }

            saveDataSharedPref(departamento, provincia, distrito, distritoPosition, ubigeoIngresado, direccionIngresada, fechaSeleccionada)
        }
    }

    // Cargar los datos de las preferencias compartidas
    private fun loadFormData() {
        val distritoPosition = sharedPreferences.getInt("distritoPosition", 1)
        spDistrito.setSelection(distritoPosition)
        etUbigeo.setText(sharedPreferences.getString("ubigeo", ""))
        etDireccion.setText(sharedPreferences.getString("direccion", ""))
        val fecha = sharedPreferences.getString("fechaDelivery", null)
        if (fecha != null) {
            selectedDate = LocalDate.parse(fecha)
            tvSelectDateDelivery.text = fecha
        }
    }

    //----- METODOS -----

    //-- Validaciones
    private fun validateFields(ubigeo: String, direccion: String, fecha: LocalDate?): Boolean {

        if (ubigeo.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el ubigeo", Toast.LENGTH_SHORT).show()
            return false
        }

        if (ubigeo.length != 6) {
            Toast.makeText(
                this,
                "El ubigeo debe tener exactamente 6 dígitos",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (direccion.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese la dirección", Toast.LENGTH_SHORT).show()
            return false
        }

        if (fecha == null) {
            Toast.makeText(this, "Por favor, seleccione la fecha", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    //-- Guardar datos en Preferencias compartidas
    private fun saveDataSharedPref(departamento: String, provincia: String, distrito: String, distritoPosition: Int, ubigeo: String, direccion: String, fechaDelivery: LocalDate?) {
        Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()

        // Guardar los datos en las preferencias compartidas
        sharedPreferences.edit().apply {
            putString("departamento", departamento)
            putString("provincia", provincia)
            putString("distrito", distrito)
            putInt("distritoPosition", distritoPosition)
            putString("ubigeo", ubigeo)
            putString("direccion", direccion)
            putString("fechaDelivery", fechaDelivery.toString())
            apply()
        }
        navigateTo(Purchase::class.java)
    }

    //-- Seleccionar fecha
    private fun selectDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
            tvSelectDateDelivery.text = "$selectedDate"
        }, year, month, day)

        datePickerDialog.show()
    }

    //-- Navegacion
    private fun navigateTo(activity: Class<*>) {
        val intent = Intent(this, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(intent)
    }

    // Maneja el clic en la flecha de retroceso
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

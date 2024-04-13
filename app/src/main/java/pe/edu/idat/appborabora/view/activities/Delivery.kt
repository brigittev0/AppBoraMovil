package pe.edu.idat.appborabora.view.activities

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import pe.edu.idat.appborabora.R

import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContentProviderCompat.requireContext
import java.time.LocalDate
import java.util.Calendar

class Delivery : AppCompatActivity() {

    private lateinit var spDepartamento: EditText
    private lateinit var spProvincia: EditText
    private lateinit var spDistrito: Spinner
    private lateinit var etUbigeo: EditText
    private lateinit var etDireccion: EditText
    private lateinit var btnGuardar: Button

    private lateinit var btnDateDelivery: AppCompatImageButton
    private lateinit var tvSelectDateDelivery: TextView
    private var selectedDate: LocalDate? = null

    private val sharedPreferences by lazy { getSharedPreferences("DeliveryForm", Context.MODE_PRIVATE) }

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

    private fun setupViews() {
        spDistrito = findViewById(R.id.spDistrito)
        etUbigeo = findViewById(R.id.etUbigeo)
        etDireccion = findViewById(R.id.etDireccion)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnDateDelivery = findViewById(R.id.btnDateDelivery)
        tvSelectDateDelivery = findViewById(R.id.tvSelectDateDelivery)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Envio a domicilio"
    }

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

        spDistrito.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, distritos)
    }

    private fun setupDateButton() {
        btnDateDelivery.setOnClickListener {
            selectDate()
        }
    }

    private fun setupSaveButton() {
        btnGuardar.setOnClickListener {
            val departamento = "Lima"
            val provincia = "Cañete"
            val distritoSeleccionado = spDistrito.selectedItem.toString()
            val ubigeoIngresado = etUbigeo.text.toString()
            val direccionIngresada = etDireccion.text.toString()
            val fechaSeleccionada = selectedDate

            if (!validateFields(ubigeoIngresado, direccionIngresada, fechaSeleccionada)) {
                return@setOnClickListener
            }

            saveDataSharedPref(departamento, provincia, distritoSeleccionado, ubigeoIngresado, direccionIngresada, fechaSeleccionada)
        }
    }

    private fun validateFields(ubigeo: String, direccion: String, fecha: LocalDate?): Boolean {

        if (ubigeo.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el ubigeo", Toast.LENGTH_SHORT).show()
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

    private fun saveDataSharedPref(departamento: String, provincia: String, distrito: String, ubigeo: String, direccion: String, fecha: LocalDate?) {
        Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, Compra::class.java)

        // Guardar los datos en las preferencias compartidas
        sharedPreferences.edit().apply {
            putString("departamento", departamento)
            putString("provincia", provincia)
            putString("distrito", distrito)
            putString("ubigeo", ubigeo)
            putString("direccion", direccion)
            putString("fecha", fecha.toString())
            apply()
        }
        startActivity(intent)
    }

    // Cargar los datos de las preferencias compartidas
    private fun loadFormData() {
        val distrito = sharedPreferences.getString("distrito", "")
        spDistrito.setSelection((spDistrito.adapter as ArrayAdapter<String>).getPosition(distrito))
        etUbigeo.setText(sharedPreferences.getString("ubigeo", ""))
        etDireccion.setText(sharedPreferences.getString("direccion", ""))
        val fecha = sharedPreferences.getString("fecha", null)
        if (fecha != null) {
            selectedDate = LocalDate.parse(fecha)
            tvSelectDateDelivery.text = fecha
        }
    }

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

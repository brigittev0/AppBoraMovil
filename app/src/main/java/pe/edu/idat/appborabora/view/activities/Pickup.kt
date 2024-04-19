package pe.edu.idat.appborabora.view.activities

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import pe.edu.idat.appborabora.R
import java.time.LocalDate
import java.util.Calendar

class Pickup : AppCompatActivity() {

    private lateinit var spPickup: Spinner
    private lateinit var btnSavePickup: Button

    private lateinit var btnDatePickup: AppCompatImageButton
    private lateinit var tvSelectDatePickup: TextView
    private var selectedDate: LocalDate? = null

    private val sharedPreferences by lazy { getSharedPreferences("DeliveryPickup", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pickup)

        setupViews()
        setupToolbar()
        setupSpinner()
        setupDateButton()
        setupSaveButton()

        loadFormData()
    }

    //-- Inicializando
    private fun setupViews() {
        spPickup= findViewById(R.id.spPickup)
        btnSavePickup = findViewById(R.id.btnSavePickup)
        btnDatePickup = findViewById(R.id.btnDatePickup)
        tvSelectDatePickup = findViewById(R.id.tvSelectDatePickup)
    }

    //-- Toolbar
    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Retiro en tienda"
    }

    //-- Spinner
    private fun setupSpinner() {
        val sedes = arrayOf(
            "Bodega BoraBora - Asia",
            "Bodega BoraBora - Cerro Azul",
            "Bodega BoraBora - Lunahuaná",
        )

        spPickup.adapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            sedes
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
        btnDatePickup.setOnClickListener {
            selectDate()
        }
    }

    //-- Boton Guardar
    private fun setupSaveButton() {
        btnSavePickup.setOnClickListener {
            val sedePosition = spPickup.selectedItemPosition
            val fechaSeleccionada = selectedDate
            val sede = spPickup.selectedItem as String

            if (!validateFields(fechaSeleccionada)) {
                return@setOnClickListener
            }

            saveDataSharedPref(sede, sedePosition, fechaSeleccionada)
        }
    }

    // Cargar los datos de las preferencias compartidas
    private fun loadFormData() {
        val sedePosition = sharedPreferences.getInt("sedePosition", 1)
        spPickup.setSelection(sedePosition)
        val fecha = sharedPreferences.getString("fechaPickup", null)
        if (fecha != null) {
            selectedDate = LocalDate.parse(fecha)
            tvSelectDatePickup.text = fecha
        }
    }

    //----- METODOS -----

    //-- Validaciones
    private fun validateFields(fechaPickup: LocalDate?): Boolean {

        if (fechaPickup == null) {
            Toast.makeText(this, "Por favor, seleccione la fecha", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    //-- Guardar datos en Preferencias compartidas
    private fun saveDataSharedPref(sede: String, sedePosition: Int, fechaPickup: LocalDate?) {
        Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()

        // Guardar los datos en las preferencias compartidas
        sharedPreferences.edit().apply {
            putString("sede", sede)
            putInt("sedePosition", sedePosition)
            putString("fechaPickup", fechaPickup.toString())
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
            tvSelectDatePickup.text = "$selectedDate"
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
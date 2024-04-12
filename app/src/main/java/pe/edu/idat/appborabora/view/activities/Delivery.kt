package pe.edu.idat.appborabora.view.activities

import android.app.DatePickerDialog
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

        spDepartamento = findViewById(R.id.spDepartamento)
        spProvincia = findViewById(R.id.spProvincia)
        spDistrito = findViewById(R.id.spDistrito)
        etUbigeo = findViewById(R.id.etUbigeo)
        etDireccion = findViewById(R.id.etDireccion)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnDateDelivery = findViewById(R.id.btnDateDelivery)
        tvSelectDateDelivery = findViewById(R.id.tvSelectDateDelivery)

        //--Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Envio a domicilio"

        val departamentos = "Lima"
        val provincias = "Cañete"
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

        spDistrito.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, distritos)

        //Seleccion del boton fecha
        btnDateDelivery.setOnClickListener {
            selectDate()
        }

        // Configurar el botón de guardar
        btnGuardar.setOnClickListener {
            val departamentoSeleccionado = spDepartamento.text.toString()
            val provinciaSeleccionada = spProvincia.text.toString()
            val distritoSeleccionado = spDistrito.selectedItem.toString()
            val ubigeoIngresado = etUbigeo.text.toString()
            val direccionIngresada = etDireccion.text.toString()
            val fechaSeleccionada = selectedDate

            // ----GUARDAR DATOS

            Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Compra::class.java)
            startActivity(intent)
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


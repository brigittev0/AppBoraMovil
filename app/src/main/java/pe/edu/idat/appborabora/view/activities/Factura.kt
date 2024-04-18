package pe.edu.idat.appborabora.view.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.FileProvider
import com.google.firebase.BuildConfig
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.view.HomeNavigation
import java.io.File
import java.io.FileOutputStream

class Factura : AppCompatActivity() {

    private val sPPayment by lazy { getSharedPreferences("Payment", Context.MODE_PRIVATE) }
    private val sPUser by lazy { getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factura)

        val btnVolver = findViewById<Button>(R.id.btnVolverMenu)
        val btnEnviarWhatsApp = findViewById<Button>(R.id.btnEnviarWhatsApp)

        val purchaseNumber = sPPayment.getString("purchaseNumber", "N/A") ?: "N/A"
        val purchaseDate = sPPayment.getString("purchaseDate", "N/A") ?: "N/A"
        val fullName = sPUser.getString("fullname", "N/A")
        val email = sPUser.getString("email", "N/A")
        val document = sPUser.getString("identityDoc", "N/A")
        val phone = sPUser.getString("phone", "N/A")

        val cardTypeString = sPPayment.getString("cardType", "N/A")
        var cardT = ""
        if(cardTypeString == "C"){
            cardT = "Tarjeta"
        }else if(cardTypeString == "D"){
            cardT = "Yape"
        }

        val subtotal = sPPayment.getFloat("subtotal", 0.0f) ?: "0.0"
        val igv = sPPayment.getFloat("igv", 0.0f) ?: "0.0"
        val total = sPPayment.getFloat("total", 0.0f) ?: "0.0"


        findViewById<TextView>(R.id.tvnumerocompra).text = purchaseNumber
        findViewById<TextView>(R.id.tvfechacompra).text = purchaseDate
        findViewById<TextView>(R.id.tvnombresapellidos).text = fullName
        findViewById<TextView>(R.id.tvcorreo).text = email
        findViewById<TextView>(R.id.tvdocumento).text = document
        findViewById<TextView>(R.id.tvTelefono).text = phone
        findViewById<TextView>(R.id.tvmetodopago).text = cardT
        findViewById<TextView>(R.id.tvsubtotal).text = subtotal.toString()
        findViewById<TextView>(R.id.tvigv).text = igv.toString()
        findViewById<TextView>(R.id.tvtotalcompra).text = total.toString()

        // botón para enviar a WhatsApp
        btnEnviarWhatsApp.setOnClickListener {
            val facturaView = findViewById<View>(R.id.facturaLayout)
            val facturaFile = captureScreen(facturaView)
            if (facturaFile != null) {
                sendToWhatsApp(facturaFile)
            }
        }

        // botón volver al menú
        btnVolver.setOnClickListener {
            val intent = Intent(this, HomeNavigation::class.java)
            startActivity(intent)
            finish()
        }
    }

    // En Factura
    override fun onDestroy() {
        super.onDestroy()
        // Borra los datos cuando la actividad Factura se destruye
        sPPayment.edit().clear().apply()
    }

    //metodo para capturar en imagen a factura
    fun captureScreen(view: View): File? {
        // Oculta los botones
        val btnVolver = findViewById<Button>(R.id.btnVolverMenu)
        val btnEnviarWhatsApp = findViewById<Button>(R.id.btnEnviarWhatsApp)
        btnVolver.visibility = View.GONE
        btnEnviarWhatsApp.visibility = View.GONE

        // Cambia el color de fondo a blanco
        val originalColor = view.background
        view.setBackgroundColor(Color.WHITE)

        // Captura la vista
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)

        // Guarda la captura en un archivo
        val file = File(externalCacheDir, "factura.png")
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos)
        fos.close()

        // Restablece la visibilidad de los botones y el color de fondo
        btnVolver.visibility = View.VISIBLE
        btnEnviarWhatsApp.visibility = View.VISIBLE
        view.background = originalColor

        return file
    }

    //metodo para enviarlo a whatsapp
    fun sendToWhatsApp(file: File) {
        val uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file)

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.setPackage("com.whatsapp")

        startActivity(Intent.createChooser(intent, "Share Image"))
    }
}
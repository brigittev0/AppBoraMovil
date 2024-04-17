package pe.edu.idat.appborabora.view.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import pe.edu.idat.appborabora.R

class Factura : AppCompatActivity() {

    private val sPPayment by lazy { getSharedPreferences("Payment", Context.MODE_PRIVATE) }
    private val sPUser by lazy { getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factura)

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
    }

    // En Factura
    override fun onDestroy() {
        super.onDestroy()
        // Borra los datos cuando la actividad Factura se destruye
        sPPayment.edit().clear().apply()
    }
}
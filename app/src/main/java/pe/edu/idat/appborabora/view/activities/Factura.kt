package pe.edu.idat.appborabora.view.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import pe.edu.idat.appborabora.R

class Factura : AppCompatActivity() {

    private val sPPayment by lazy { getSharedPreferences("Payment", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factura)

        val purchaseNumber = sPPayment.getString("purchaseNumber", "N/A") ?: "N/A"
        val purchaseDate = sPPayment.getString("purchaseDate", "N/A") ?: "N/A"
        val fullName = sPPayment.getString("fullName", "N/A") ?: "N/A"
        val email = sPPayment.getString("email", "N/A") ?: "N/A"
        val document = sPPayment.getString("document", "N/A") ?: "N/A"
        val phone = sPPayment.getString("phone", "N/A") ?: "N/A"
        val paymentMethod = sPPayment.getString("paymentMethod", "N/A") ?: "N/A"
        val subtotal = sPPayment.getFloat("subtotal", 0.0f) ?: "0.0"
        val igv = sPPayment.getFloat("igv", 0.0f) ?: "0.0"
        val total = sPPayment.getFloat("total", 0.0f) ?: "0.0"

        findViewById<TextView>(R.id.tvnumerocompra).text = purchaseNumber
        findViewById<TextView>(R.id.tvfechacompra).text = purchaseDate
        findViewById<TextView>(R.id.tvnombresapellidos).text = fullName
        findViewById<TextView>(R.id.tvcorreo).text = email
        findViewById<TextView>(R.id.tvdocumento).text = document
        findViewById<TextView>(R.id.tvTelefono).text = phone
        findViewById<TextView>(R.id.tvmetodopago).text = paymentMethod
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
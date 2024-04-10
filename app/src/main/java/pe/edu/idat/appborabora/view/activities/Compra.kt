package pe.edu.idat.appborabora.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import lib.visanet.com.pe.visanetlib.VisaNet
import lib.visanet.com.pe.visanetlib.data.custom.Channel
import lib.visanet.com.pe.visanetlib.presentation.custom.VisaNetViewAuthorizationCustom
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.adapter.CartAdapter
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.integrationniubiz.providers.Visanet
import java.lang.Exception
import java.util.HashMap

class Compra : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compra)

        val payButton: Button = findViewById(R.id.pay)
        payButton.setOnClickListener {
            Visanet().getTokenSecurityProvider(this)
        }

        // Inicializa la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Habilita la flecha de retroceso en la barra de herramientas
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Cambia el título en la barra de herramientas
        supportActionBar?.title = "Carrito de compras"

        //-----------------
        val rvproductoscart: RecyclerView = findViewById(R.id.rvproductoscart)
        rvproductoscart.layoutManager = LinearLayoutManager(this)
        val productList = listOf(
            ProductDTO("Arroz ", "Arroz Extra Faraón de 5 Kg", 25.20,123,"2023-12-24", "https://mundoabarrotes.com/wp-content/uploads/2019/09/Arroz-Faraon-Naranja-50-kg-ver2.webp",  1, 2),
            ProductDTO("Arroz ", "Avena Hojuelas Gruesas Grano de Oro 900 g",17.20, 34, "2025-01-15", "https://plazavea.vteximg.com.br/arquivos/ids/561742-450-450/20192034.jpg?v=637427442451830000", 1, 2),
            ProductDTO("Arroz ", "Avena Tradicional Quaker de 900 g",15.70, 72, "2024-02-12", "https://wongfood.vtexassets.com/arquivos/ids/606790/Mantequilla-con-Sal-Gloria-90g-1-351640701.jpg?v=638074246405000000", 1, 2),
        )
        val adapter = CartAdapter(productList)
        rvproductoscart.adapter = adapter
        //-----------------
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

    fun receiveToken(token : String , pinHash : String){

        val TAG = "NIUBIZ"

        val data: MutableMap<String, Any> = HashMap()
        data[VisaNet.VISANET_SECURITY_TOKEN] = token
        data[VisaNet.VISANET_CHANNEL] = Channel.MOBILE
        data[VisaNet.VISANET_COUNTABLE] = true
        data[VisaNet.VISANET_MERCHANT] = "456879852"
        data[VisaNet.VISANET_PURCHASE_NUMBER] = "2020111701"
        data[VisaNet.VISANET_AMOUNT] = 10.50

        val MDDdata = HashMap<String, String>()
        MDDdata["19"] = "LIM"
        MDDdata["20"] = "AQP"
        MDDdata["21"] = "AFKI345"
        MDDdata["94"] = "ABC123DEF"

        data[VisaNet.VISANET_MDD] = MDDdata
        data[VisaNet.VISANET_ENDPOINT_URL] = "https://apisandbox.vnforappstest.com/"
        data[VisaNet.VISANET_CERTIFICATE_HOST] = "apisandbox.vnforappstest.com"
        data[VisaNet.VISANET_CERTIFICATE_PIN] =
            "sha256/p4Sev8TEZTsSi51XmL0mfAP0RzRoMIR9n/3Nj8M/C2Q="       //Ingreso de hash manualmente
        //"sha256/$pinHash"

        val custom = VisaNetViewAuthorizationCustom()
        custom.logoImage = R.drawable.lg_logotipo_black
        custom.buttonColorMerchant = R.color.black

        //Invocación al método autorización
        try {
            Log.d("MyApp", "Metodo autorizacion")
            VisaNet.authorization(this, data, custom)

        } catch (e: Exception) {
            Log.d("MyApp", "Excepcion")
            Log.i(TAG, "onClick: " + e.message)
            disableComponents()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VisaNet.VISANET_AUTHORIZATION) {
            if (data != null) {
                val gson = GsonBuilder().setPrettyPrinting().create()
                if (resultCode == RESULT_OK) {
                    disableComponents()
                    val JSONString = data.extras!!.getString("keySuccess")
                    val jsonElement = JsonParser.parseString(JSONString)
                    Log.d("Success JSON", gson.toJson(jsonElement)) // Imprime el JSON de éxito
                    Log.d("MyApp", "Pago exitoso")
                    val toast1 = Toast.makeText(applicationContext, "¡Pago exitoso!", Toast.LENGTH_LONG)
                    toast1.show()

                    // Iniciar la nueva actividad
                    val intent = Intent(this, CompraExitosa::class.java)
                    startActivity(intent)

                } else {
                    disableComponents()
                    var JSONString = data.extras!!.getString("keyError")
                    JSONString = JSONString ?: ""
                    val jsonElement = JsonParser.parseString(JSONString)
                    Log.d("MyApp", "Error JSON")
                    Log.d("Error JSON", gson.toJson(jsonElement)) // Imprime el JSON de error
                    val toast1 = Toast.makeText(applicationContext, "Error al procesar el pago", Toast.LENGTH_LONG)
                    toast1.show()
                }
            } else {
                Log.d("MyApp", "Cancelando pago")
                val toast1 = Toast.makeText(applicationContext, "Cancelado...", Toast.LENGTH_SHORT)
                toast1.show()
                disableComponents()
            }
        }
    }

    private fun disableComponents(){
        val button : Button =  findViewById(R.id.pay)
        val progressBar : ProgressBar = findViewById(R.id.progress)

        button.isEnabled = true
        progressBar.visibility = View.GONE
    }
}
package pe.edu.idat.appborabora.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import lib.visanet.com.pe.visanetlib.VisaNet
import lib.visanet.com.pe.visanetlib.data.custom.Channel
import lib.visanet.com.pe.visanetlib.presentation.custom.VisaNetViewAuthorizationCustom
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.adapter.CartAdapter
import pe.edu.idat.appborabora.data.dto.request.CardType
import pe.edu.idat.appborabora.data.dto.request.District
import pe.edu.idat.appborabora.data.dto.request.Headquarter
import pe.edu.idat.appborabora.data.dto.request.Order
import pe.edu.idat.appborabora.data.dto.request.Payment
import pe.edu.idat.appborabora.data.dto.request.Product
import pe.edu.idat.appborabora.data.dto.request.PurchaseRequest
import pe.edu.idat.appborabora.data.dto.request.Status
import pe.edu.idat.appborabora.data.dto.request.User
import pe.edu.idat.appborabora.util.Cart
import pe.edu.idat.appborabora.view.HomeNavigation
import pe.edu.idat.appborabora.viewmodel.ProductViewModel
import pe.edu.idat.appborabora.viewmodel.PurchaseViewModel
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.HashMap
import kotlin.math.round

class Purchase : AppCompatActivity() {

    private lateinit var adapter: CartAdapter

    private lateinit var subtotalTextView: TextView
    private lateinit var igvTextView: TextView
    private lateinit var shippingTextView: TextView
    private lateinit var totalTextView: TextView
    private lateinit var purchaseViewModel: PurchaseViewModel

    private val sPDeliveryPickup by lazy { getSharedPreferences("DeliveryPickup", Context.MODE_PRIVATE) }
    private val sPUserLogged by lazy { getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase)

        purchaseViewModel = ViewModelProvider(this).get(PurchaseViewModel::class.java)

        initViews()
        setupToolbar()
        setupRecyclerView()
        updateTotals()
        setupRadioGroup()

        setupPayButton()
    }

    override fun onResume() {
        super.onResume()

        updateTotals()
    }

    //-- Inicializando
    private fun initViews() {
        subtotalTextView = findViewById(R.id.tvsubtotal)
        igvTextView = findViewById(R.id.tvigv)
        shippingTextView = findViewById(R.id.tvgastoenvio)
        totalTextView = findViewById(R.id.tvtotalcompra)
    }

    //-- Toolbar
    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Mi Carrito de compras"
    }

    //-- Carrito de compras
    private fun setupRecyclerView() {
        val rvproductoscart: RecyclerView = findViewById(R.id.rvproductoscart)
        rvproductoscart.layoutManager = LinearLayoutManager(this)
        adapter = CartAdapter(Cart.obtenerProductos()) {
            updateTotals()
        }
        rvproductoscart.adapter = adapter
    }

    //-- Actualizar resumen compra
    fun updateTotals() {
        val subtotal = Cart.obtenerProductos().sumOf { it.subtotal }
        val igv = Cart.obtenerProductos().sumOf { it.igv }
        var shipping = Cart.obtenerProductos().sumOf { it.shipping }
        val total = Cart.obtenerProductos().sumOf { it.total }

        // Verifica la opción seleccionada en las preferencias compartidas
        val selectedOption = sPDeliveryPickup.getString("optionOrder", "")
        if (selectedOption == "DELIVERY") {
            // Si la opción seleccionada es "delivery", agrega 5.90 al costo de envío
            shipping += 5.90
        } else if (selectedOption == "PICKUP") {
            // Si la opción seleccionada es "recojo en tienda", deja el costo de envío en 0
            shipping = 0.0
        }

        // Actualiza los totales en la interfaz de usuario
        subtotalTextView.text = "S/. ${String.format("%.2f", subtotal)}"
        igvTextView.text = "S/. ${String.format("%.2f", igv)}"
        shippingTextView.text =  "S/. ${String.format("%.2f", shipping)}"
        totalTextView.text = "S/. ${String.format("%.2f", total + shipping)}" // Agrega el costo de envío al total
    }

    //-- Radio Button -- redirigir interfaz
    private fun setupRadioGroup() {
        val rgOptionSelect: RadioGroup = findViewById(R.id.rgOptionSelect)

        rgOptionSelect.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButtonStorePickup -> {
                    Toast.makeText(this, "Retiro en tienda seleccionada", Toast.LENGTH_SHORT).show()
                    navigateTo(Pickup::class.java)
                    saveSelectedOption("PICKUP")
                }
                R.id.radioButtonHomeDelivery -> {
                    Toast.makeText(this, "Entrega a domicilio seleccionada", Toast.LENGTH_SHORT).show()
                    navigateTo(Delivery::class.java)
                    saveSelectedOption( "DELIVERY")
                }
            }
        }
    }

    //-- Boton Pagar
    private fun setupPayButton() {
        val payButton: Button = findViewById(R.id.pay)
        payButton.setOnClickListener {
            val purchaseRequest = createPurchaseRequest()
            createPurchase(purchaseRequest)
            observePurchaseResponse()
            observeErrorMessage()

            //NIUBIZ
        }
    }

    //-- Crear compra
    private fun createPurchaseRequest(): PurchaseRequest {

        //--USER
        val identityDoc = sPUserLogged.getString("identityDoc", "0")?.toInt() ?: 0
        val user = User(identityDoc)

        //--NIUBIZ
        val cardType = CardType(1)
        val status = Status(1)
        val payment = Payment(100.0, "1234567812345678", "PEN", "1", "123456", "2022-12-31", cardType, status)

        //--PICKUP
        val headquarterId = sPDeliveryPickup.getInt("sedePosition", 1)
        val headquarter = Headquarter(headquarterId+1)

        //--DELIVERY
        val districtId = sPDeliveryPickup.getInt("distritoPosition", 1)
        val district = District(districtId+1)
        val deliveryDate = sPDeliveryPickup.getString("fechaDelivery", "2022-12-31") ?: "2022-12-31"
        val address = sPDeliveryPickup.getString("direccion", "empty") ?: "empty"
        val province = sPDeliveryPickup.getString("provincia", "empty") ?: "empty"
        val department = sPDeliveryPickup.getString("departamento", "empty") ?: "empty"

        //--ORDER
        val optionOrder = sPDeliveryPickup.getString("optionOrder", "") ?: ""
        val order = Order(optionOrder, optionOrder, deliveryDate, headquarter, address, department, district, province, 987569)

        //--CARRITO
        val products = Cart.obtenerProductos().map { Product(it.idProducto, it.quantity) }
        val subtotal = (round(Cart.obtenerProductos().sumOf { it.subtotal } * 100) / 100)
        val igv = (round(Cart.obtenerProductos().sumOf { it.igv } * 100) / 100)
        var shipping = Cart.obtenerProductos().sumOf { it.shipping }


        // Verifica la opción seleccionada en las preferencias compartidas
        val selectedOption = sPDeliveryPickup.getString("optionOrder", "")
        if (selectedOption == "DELIVERY") {
            // Si la opción seleccionada es "delivery", agrega 5.90 al costo de envío
            shipping += 5.90
        } else if (selectedOption == "PICKUP") {
            // Si la opción seleccionada es "recojo en tienda", deja el costo de envío en 0
            shipping = 0.0
        }

        val total = (round((Cart.obtenerProductos().sumOf { it.total } + shipping) * 100) / 100)

        return PurchaseRequest(total, igv, subtotal, deliveryDate, user, payment, order, products)
    }

    //Crear compra segun typeorder
    private fun createPurchase(purchaseRequest: PurchaseRequest) {
        purchaseViewModel.createPurchase("DELIVERY", purchaseRequest)
    }

    //-- Observadores
    private fun observePurchaseResponse() {
        purchaseViewModel.createPurchaseResponse.observe(this, Observer { apiResponse ->
            Toast.makeText(this, "Compra creada con éxito", Toast.LENGTH_SHORT).show()
        })
    }

    private fun observeErrorMessage() {
        purchaseViewModel.errorMessage.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        })
    }



    //----- METODOS -----

    //-- Guardar Opcion seleccionada
    private fun saveSelectedOption(optionType: String) {
        sPDeliveryPickup.edit().apply {
            putString("optionOrder", optionType)
            apply()
        }
    }

    //-- Navegacion
    private fun navigateTo(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    // Maneja el clic en la flecha de retroceso
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navigateTo(HomeNavigation::class.java)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    //--------------------- PROCESO DE NIUBIZ ---------------------
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
    //--
}
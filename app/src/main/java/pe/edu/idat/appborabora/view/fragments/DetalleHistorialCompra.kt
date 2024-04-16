package pe.edu.idat.appborabora.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.adapter.DetailPurchaseAdapter
import pe.edu.idat.appborabora.adapter.PurchaseAdapter
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.data.dto.response.PurchasetResponse
import pe.edu.idat.appborabora.databinding.FragmentDetalleHistorialCompraBinding
import pe.edu.idat.appborabora.util.Cart
import pe.edu.idat.appborabora.viewmodel.PurchaseViewModel


class DetalleHistorialCompra : Fragment() {
    private lateinit var purchaseResponse: PurchasetResponse
    private lateinit var viewModel: PurchaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detalle_historial_compra, container, false)

        // Inicializa el ViewModel
        viewModel = ViewModelProvider(this).get(PurchaseViewModel::class.java)



        // Observa los cambios en purchaseResponse
        viewModel.purchaseResponse.observe(viewLifecycleOwner, Observer { purchaseResponses ->
            // Supongamos que quieres mostrar la primera respuesta de compra
            if (!purchaseResponses.isNullOrEmpty()) {
                purchaseResponse = purchaseResponses[0]

                // Supongamos que tienes los siguientes TextViews en tu layout
                val purchaseIdTextView = view.findViewById<TextView>(R.id.tvnumerocompra)
                val totalTextView = view.findViewById<TextView>(R.id.tvtotalcompra)
                val igvTextView = view.findViewById<TextView>(R.id.tvigv)
                val subtotalTextView = view.findViewById<TextView>(R.id.tvsubtotal)
                val purchaseDateTextView = view.findViewById<TextView>(R.id.tvfechacompra)
                val paymentIdTextView = view.findViewById<TextView>(R.id.tvmetodopago)
                val identityDocTextView = view.findViewById<TextView>(R.id.tvdocumento)

                // Establecer los valores
                purchaseIdTextView.text = purchaseResponse.purchase_id.toString()
                totalTextView.text = purchaseResponse.total.toString()
                igvTextView.text = purchaseResponse.igv.toString()
                subtotalTextView.text = purchaseResponse.subtotal.toString()
                purchaseDateTextView.text = purchaseResponse.purchaseDate
                paymentIdTextView.text = purchaseResponse.paymentId.toString()
                identityDocTextView.text = purchaseResponse.identityDoc.toString()
            }
        })

        return view
    }
}
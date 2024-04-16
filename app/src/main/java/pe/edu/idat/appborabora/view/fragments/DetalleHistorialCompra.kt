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

    private lateinit var DetailPurchaseAdapter: DetailPurchaseAdapter

    private lateinit var tvNumeroCompra: TextView
    private lateinit var tvMetodoPago: TextView
    private lateinit var tvSubtotal: TextView
    private lateinit var tvIgv: TextView
    private lateinit var tvTotalCompra: TextView
    private lateinit var purchaseViewModel: PurchaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_historial_compra, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvNumeroCompra = view.findViewById(R.id.textView26)
        tvMetodoPago = view.findViewById(R.id.tvmetodopago)
        tvSubtotal = view.findViewById(R.id.tvsubtotal)
        tvIgv = view.findViewById(R.id.tvigv)
        tvTotalCompra = view.findViewById(R.id.tvtotalcompra)

        purchaseViewModel = ViewModelProvider(this).get(PurchaseViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvproductoscart)
        val adapter = DetailPurchaseAdapter(requireContext(), Cart.obtenerProductos())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Supongamos que tienes un documento de identidad para buscar las compras
        val identityDoc = 12345678

        purchaseViewModel.fetchAllPurchases(identityDoc)

        purchaseViewModel.purchaseResponse.observe(viewLifecycleOwner, Observer { purchases ->
            // Aquí puedes manejar la lista de compras
            // Por ejemplo, puedes tomar la primera compra y mostrarla en los TextViews
            val purchaseResponse = purchases.firstOrNull()
            if (purchaseResponse != null) {
                tvNumeroCompra.text = purchaseResponse.purchase_id.toString()
                tvMetodoPago.text = purchaseResponse.paymentId.toString() // Necesitarás convertir el ID de pago en el método de pago real
                tvSubtotal.text = purchaseResponse.subtotal.toString()
                tvIgv.text = purchaseResponse.igv.toString()
                tvTotalCompra.text = purchaseResponse.total.toString()
            }
        })
    }
}
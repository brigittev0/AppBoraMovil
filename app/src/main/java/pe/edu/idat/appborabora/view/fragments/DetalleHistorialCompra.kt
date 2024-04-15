package pe.edu.idat.appborabora.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.adapter.DetailPurchaseAdapter
import pe.edu.idat.appborabora.adapter.PurchaseAdapter
import pe.edu.idat.appborabora.databinding.FragmentDetalleHistorialCompraBinding
import pe.edu.idat.appborabora.viewmodel.PurchaseViewModel

class DetalleHistorialCompra : Fragment() {
    private lateinit var binding: FragmentDetalleHistorialCompraBinding
    private val detalleHistorialCompraAdapter = DetailPurchaseAdapter()
    private lateinit var viewModel: PurchaseViewModel
    private var compraId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetalleHistorialCompraBinding.inflate(inflater, container, false)

        binding.rvproductoscompra.layoutManager = LinearLayoutManager(requireActivity())
        /*binding.rvproductoscompra.adapter = detalleHistorialCompraAdapter */

        viewModel = ViewModelProvider(this).get(PurchaseViewModel::class.java)

        arguments?.let {
            compraId = it.getInt("compraId")
        }

        viewModel.purchaseResponse.observe(viewLifecycleOwner, Observer { purchases ->
            purchases?.let {
                if (it.isNotEmpty()) {
                    val compra = it[0]
                    binding.tvnumerocompra.text = compra.orderId.toString()
                    binding.tvfechacompra.text = compra.purchaseDate
                    binding.tvdocumento.text = compra.identityDoc.toString()
                    binding.tvsubtotal.text = "S/ ${compra.subtotal}"
                    /*detalleHistorialCompraAdapter.setData(compra.productIds) */
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        })

        viewModel.fetchAllPurchases(compraId)

        return binding.root
    }
}
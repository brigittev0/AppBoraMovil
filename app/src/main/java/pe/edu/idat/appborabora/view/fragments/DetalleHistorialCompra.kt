package pe.edu.idat.appborabora.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
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
import pe.edu.idat.appborabora.viewmodel.DetailPurchaseViewModel
import pe.edu.idat.appborabora.viewmodel.PurchaseViewModel
import pe.edu.idat.appborabora.viewmodel.UserViewModel


class DetalleHistorialCompra : Fragment() {
    private lateinit var purchaseResponse: PurchasetResponse
    private lateinit var viewModel: DetailPurchaseViewModel
    private lateinit var userViewModel: UserViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalle_historial_compra, container, false)

        viewModel = ViewModelProvider(this).get(DetailPurchaseViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        viewModel.purchaseResponse.observe(viewLifecycleOwner, Observer { purchaseResponse ->
            purchaseResponse?.let {
                val purchaseIdTextView = view.findViewById<TextView>(R.id.tvnumerocompra)
                val totalTextView = view.findViewById<TextView>(R.id.tvtotalcompra)
                val igvTextView = view.findViewById<TextView>(R.id.tvigv)
                val subtotalTextView = view.findViewById<TextView>(R.id.tvsubtotal)
                val purchaseDateTextView = view.findViewById<TextView>(R.id.tvfechacompra)
                val paymentIdTextView = view.findViewById<TextView>(R.id.tvmetodopago)
                val identityDocTextView = view.findViewById<TextView>(R.id.tvdocumento)

                purchaseIdTextView.text = purchaseResponse.purchase_id.toString()
                totalTextView.text = purchaseResponse.total.toString()
                igvTextView.text = purchaseResponse.igv.toString()
                subtotalTextView.text = purchaseResponse.subtotal.toString()
                purchaseDateTextView.text = purchaseResponse.purchaseDate
                paymentIdTextView.text = purchaseResponse.paymentId.toString()
                identityDocTextView.text = purchaseResponse.identityDoc.toString()
            }
        })

        // Preferencias compartidas
        val sharedPref = requireActivity().getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE)
        val usernameUsuario = sharedPref.getString("username", null)

        if (usernameUsuario != null) {
            userViewModel.getUserByUsername(usernameUsuario)

            userViewModel.userProfileResponse.observe(viewLifecycleOwner, Observer { userProfileResponse ->
                // Aquí puedes rellenar los campos con los datos del usuario
                // Por ejemplo:
                val  nombreTextView = view.findViewById<TextView>(R.id.tvnombresapellidos)
               // val  apellidoTextView = view.findViewById<TextView>(R.id.tvnombresapellidos)
                val  emailTextView = view.findViewById<TextView>(R.id.tvcorreo)
                val  celularTextView = view.findViewById<TextView>(R.id.tvTelefono)

                nombreTextView.text = userProfileResponse.username
                // apellidoTextView.text = userProfileResponse.lastname
                emailTextView.text = userProfileResponse.email
                celularTextView.text = userProfileResponse.cellphone.toString()
            })
        }

        val purchaseId = arguments?.getInt("purchaseId") ?: return view
        viewModel.fetchPurchaseById(purchaseId)

        return view
    }
}
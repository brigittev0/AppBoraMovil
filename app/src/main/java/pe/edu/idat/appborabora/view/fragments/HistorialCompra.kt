package pe.edu.idat.appborabora.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.adapter.PurchaseAdapter
import pe.edu.idat.appborabora.viewmodel.PurchaseViewModel

class HistorialCompra : Fragment() {
    private lateinit var historialCompraAdapter: PurchaseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historial_compra, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historialCompraAdapter = PurchaseAdapter(requireContext())

        val rvHistorialCompras: RecyclerView = view.findViewById(R.id.rvhistorialcompras)
        rvHistorialCompras.layoutManager = LinearLayoutManager(context)
        rvHistorialCompras.adapter = historialCompraAdapter

        val purchaseViewModel = ViewModelProvider(this).get(PurchaseViewModel::class.java)
        purchaseViewModel.purchaseResponse.observe(viewLifecycleOwner, Observer { compras ->
            historialCompraAdapter.setProductList(compras)
        })

        val sharedPreferences = requireActivity().getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE)
        val identityDoc = sharedPreferences.getString("identityDoc", "0")?.toInt() ?: 0
        // Aquí obtienes tus datos de las compras
        // Asegúrate de reemplazar "userId" con el ID de usuario correcto
        purchaseViewModel.fetchAllPurchases(identityDoc)
    }
}

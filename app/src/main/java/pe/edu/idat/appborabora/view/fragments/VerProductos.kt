package pe.edu.idat.appborabora.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.adapter.ProductoAdapter
import pe.edu.idat.appborabora.adapter.ProductoDashboardAdapter
import pe.edu.idat.appborabora.viewmodel.ProductViewModel
import pe.edu.idat.appborabora.viewmodel.VerProductoViewModel

class VerProductos : Fragment() {

    private lateinit var verProductoViewModel: VerProductoViewModel
    private lateinit var productoAdapter: ProductoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver_productos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el ViewModel
        verProductoViewModel = ViewModelProvider(this).get(VerProductoViewModel::class.java)

        // Inicializa el adaptador
        productoAdapter = ProductoAdapter(requireContext())

        // Configura el RecyclerView
        val rvProductos = view.findViewById<RecyclerView>(R.id.rvProductos)
        rvProductos.layoutManager = LinearLayoutManager(requireContext())
        rvProductos.adapter = productoAdapter

        // Observa los cambios en la lista de productos
        verProductoViewModel.products.observe(viewLifecycleOwner, Observer { products ->
            // Actualiza la lista de productos en el adaptador
            productoAdapter.setProductList(products)
        })
    }
}
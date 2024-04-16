package pe.edu.idat.appborabora.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.adapter.ListProductAdapter
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.viewmodel.ProdCategViewModel

class ListarProdCategoria : Fragment() {
    private lateinit var adapter: ListProductAdapter
    private lateinit var viewModel: ProdCategViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listar_prod_categoria, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProdCategViewModel::class.java)

        // Inicializa el adaptador aquí
        adapter = ListProductAdapter(listOf()) { product ->
            // Crea un Bundle y coloca el ID del producto en él
            val bundle = Bundle().apply {
                putInt("productId", product.id_product)
            }
            findNavController().navigate(R.id.detalleProducto, bundle)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.rcvPlatillosPorCategoria)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        val categoryId = arguments?.getInt("categoryId") ?: return
        viewModel.products.observe(viewLifecycleOwner, Observer { products ->
            // Actualiza los datos del adaptador
            adapter.updateProducts(products)
        })

        viewModel.fetchProductsByCategoryId(categoryId)
    }
}
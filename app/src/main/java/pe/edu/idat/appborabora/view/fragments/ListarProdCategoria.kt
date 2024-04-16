package pe.edu.idat.appborabora.view.fragments

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listar_prod_categoria, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el ViewModel
        viewModel = ViewModelProvider(this).get(ProdCategViewModel::class.java)

        val categoryId = arguments?.getInt("categoryId") ?: return
        // Observa los cambios en los productos
        viewModel.products.observe(viewLifecycleOwner, Observer { products ->
            // Actualiza el adaptador con los nuevos productos
            adapter = ListProductAdapter(products)
            val recyclerView = view.findViewById<RecyclerView>(R.id.rcvPlatillosPorCategoria)

            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter

        })

        // Llama a la función para obtener los productos por categoría
        // Deberías reemplazar este valor con el ID de la categoría que quieres
        viewModel.fetchProductsByCategoryId(categoryId)
    }
}
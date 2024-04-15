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
import pe.edu.idat.appborabora.viewmodel.ListProdViewModel

class ListarProdCategoria : Fragment() {
    private lateinit var productAdapter: ListProductAdapter
    private lateinit var rcvPlatillosPorCategoria: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_listar_prod_categoria, container, false)
        rcvPlatillosPorCategoria = view.findViewById(R.id.rcvPlatillosPorCategoria)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryId = arguments?.getInt("categoryId") ?: return
        val viewModel = ViewModelProvider(this).get(ListProdViewModel::class.java)
        viewModel.products.observe(viewLifecycleOwner, Observer { productList ->
            productAdapter = ListProductAdapter(productList, object : ListProductAdapter.OnCategoryClickListener {
                override fun onCategoryClick(categoryId: Int) {
                    // Aquí manejas el clic en la categoría
                    // Por ejemplo, puedes navegar a un nuevo fragmento que muestra los productos de esa categoría
                    val bundle = Bundle()
                    bundle.putInt("categoryId", categoryId)
                    val navController = findNavController()
                    navController.navigate(R.id.listarProdCategoria, bundle)
                }
            })
            rcvPlatillosPorCategoria.adapter = productAdapter
        })
        viewModel.getProductsByCategoryId(categoryId)
        rcvPlatillosPorCategoria.layoutManager = LinearLayoutManager(context)
    }
}
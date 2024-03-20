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
import pe.edu.idat.appborabora.adapter.CategoryAdapter
import pe.edu.idat.appborabora.viewmodel.CategoryViewModel

class Catalogo : Fragment() {

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalogo, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvcategoria = view.findViewById<RecyclerView>(R.id.rvcategoria)

        categoryAdapter = CategoryAdapter(listOf())
        rvcategoria.layoutManager = LinearLayoutManager(context)
        rvcategoria.adapter = categoryAdapter

        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        categoryViewModel.category.observe(viewLifecycleOwner, Observer { category ->
            categoryAdapter.updateData(listOf(category))
        })

        categoryViewModel.fetchCategoryById(1) // Reemplaza 1 con el ID de la categoría que quieres obtener
    }
    }

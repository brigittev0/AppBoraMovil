package pe.edu.idat.appborabora.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.viewmodel.EliminarProductoViewModel

class EliminarProducto : Fragment() {
    private lateinit var viewModel: EliminarProductoViewModel
    private lateinit var productName: TextView
    private lateinit var productDescription: TextView
    private lateinit var productPrice: TextView
    private lateinit var productStock: TextView
    private lateinit var deleteButton: Button
    private lateinit var examineButton: Button
    private lateinit var productId: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_eliminar_producto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the views
        productName = view.findViewById(R.id.productName)
        productDescription = view.findViewById(R.id.productDescription)
        productPrice = view.findViewById(R.id.productPrice)
        productStock = view.findViewById(R.id.productStock)
        deleteButton = view.findViewById(R.id.deleteButton)
        examineButton = view.findViewById(R.id.examineButton)
        productId = view.findViewById(R.id.productId)

        viewModel = ViewModelProvider(this).get(EliminarProductoViewModel::class.java)

        viewModel.product.observe(viewLifecycleOwner, Observer { product ->
            productName.text = "Producto: ${product.name}"
            productDescription.text = "Descripción: ${product.description}"
            productPrice.text = "Precio: ${product.price}"
            productStock.text = "Stock: ${product.stock}"

            // Make the views visible
            productName.visibility = View.VISIBLE
            productDescription.visibility = View.VISIBLE
            productPrice.visibility = View.VISIBLE
            productStock.visibility = View.VISIBLE
            deleteButton.visibility = View.VISIBLE
        })

        viewModel.deleteStatus.observe(viewLifecycleOwner, Observer { success ->
            if (success) {
                // Handle the successful delete
            } else {
                // Handle the error
            }
        })

        examineButton.setOnClickListener {
            val id = productId.text.toString().toInt()
            viewModel.getProductById(id)
        }

        deleteButton.setOnClickListener {
            val id = productId.text.toString().toInt()
            viewModel.deleteProduct(id)
        }
    }
}
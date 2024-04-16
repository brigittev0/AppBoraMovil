package pe.edu.idat.appborabora.view.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.viewmodel.DetailProductViewModel


class DetalleProducto : Fragment() {

    private lateinit var viewModel: DetailProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detalle_producto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar el ViewModel
        viewModel = ViewModelProvider(this).get(DetailProductViewModel::class.java)

        val productId = arguments?.getInt("productId") ?: return

        // Observar el LiveData product en el ViewModel
        viewModel.product.observe(viewLifecycleOwner, Observer { product ->
            // Actualizar la UI con el producto
            val tvNombre = view.findViewById<TextView>(R.id.tvNombre)
            val tvDescripcion = view.findViewById<TextView>(R.id.tvDescripcion)
            val tvPrecio = view.findViewById<TextView>(R.id.tvPrecio)
            val tvStock = view.findViewById<TextView>(R.id.tvStock)
            val tvExpirationDate = view.findViewById<TextView>(R.id.tvExpirationDate)
            val tvBrandProductName = view.findViewById<TextView>(R.id.tvBrandProduct)
            val imgProducto = view.findViewById<ImageView>(R.id.ivProducto) // Asegúrate de que este ID es correcto
            val imageBitmap = decodeImage(product.image)
            imgProducto.setImageBitmap(imageBitmap) // Corrección aquí

            tvNombre.text = product.name
            tvDescripcion.text = product.description
            tvPrecio.text = "S/. ${product.price}"
            tvStock.text = product.stock.toString()
            tvExpirationDate.text = product.expirationDate
            tvBrandProductName.text = product.brandProductName
        })

        // Obtener el producto
        viewModel.fetchProduct(productId)
    }

    private fun decodeImage(imageString: String): Bitmap {
        val decodedString = Base64.decode(imageString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }
}
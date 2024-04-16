package pe.edu.idat.appborabora.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import android.util.Base64
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Log
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.util.ProductPurchase
import pe.edu.idat.appborabora.data.dto.response.ProductoDashboardResponse
import pe.edu.idat.appborabora.util.Cart
import pe.edu.idat.appborabora.util.ProductCart


class ProductoDashboardAdapter(private val context: Context) : RecyclerView.Adapter<ProductoDashboardAdapter.ViewHolder>() {
    private var productList: List<ProductoDashboardResponse> = ArrayList()

    fun setProductList(productList: List<ProductoDashboardResponse>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.tvNombre.text = product.name
        holder.tvPrecio.text = "S/. ${product.price}"
        // Decodifica la imagen en base64 y la carga en ivProducto
        val imageBitmap = decodeImage(product.image)
        holder.ivProducto.setImageBitmap(imageBitmap)

        // Carrito
        // Botón de ordenar
        holder.btnOrdenar.setOnClickListener {
            val productCart = ProductCart(
                ProductPurchase(product.id_product, product.name, product.description,
                    product.price, product.stock, product.expirationDate, product.image, product.categoryId,
                    product.brandProductId), product.id_product,1
            )

            val productoAgregado = Cart.agregarProducto(productCart)
            if (productoAgregado) {
                Toast.makeText(context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "El producto ya está en el carrito", Toast.LENGTH_SHORT).show()
            }

        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecio)
        val ivProducto: ImageView = view.findViewById(R.id.ivProducto)
        val btnOrdenar: Button = view.findViewById(R.id.btnOrdenar)
    }

    private fun decodeImage(imageString: String): Bitmap {
        val decodedString = Base64.decode(imageString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }
}




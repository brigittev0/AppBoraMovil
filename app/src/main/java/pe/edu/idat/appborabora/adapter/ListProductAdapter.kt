package pe.edu.idat.appborabora.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.data.dto.response.ProductResponse
import pe.edu.idat.appborabora.util.Cart
import pe.edu.idat.appborabora.util.ProductCart
import pe.edu.idat.appborabora.util.ProductPurchase

class ListProductAdapter(private var productList: List<ProductResponse>, private val onProductClick: (ProductResponse) -> Unit
) : RecyclerView.Adapter<ListProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProducto: ImageView = itemView.findViewById(R.id.imgProducto)
        val nombreProducto: TextView = itemView.findViewById(R.id.nombreProducto)
        val txtPrecioProducto: TextView = itemView.findViewById(R.id.txtPrecioProducto)
        val btnOrdenar: Button = itemView.findViewById(R.id.btnOrdenar)
        val btnVerDetalle: Button = itemView.findViewById(R.id.btnVerDetalle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto_por_categoria, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        // Establecer el nombre del producto
        holder.nombreProducto.text = product.name
        // Establecer el precio del producto
        holder.txtPrecioProducto.text = "S/. ${product.price}"
        // Establecer la imagen del producto
        val imageBitmap = decodeImage(product.image)
        holder.imgProducto.setImageBitmap(imageBitmap)
        // Establecer los listeners de los botones
        holder.btnOrdenar.setOnClickListener {
            val productCart = ProductCart(
                ProductPurchase(product.id_product, product.name, product.description,
                    product.price, product.stock, product.expirationDate, product.image, product.categoryId,
                    product.brandProductId), product.id_product,1
            )

            val productoAgregado = Cart.agregarProducto(productCart)
            if (productoAgregado) {
                Toast.makeText(holder.itemView.context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(holder.itemView.context, "El producto ya está en el carrito", Toast.LENGTH_SHORT).show()
            }
        }
        holder.btnVerDetalle.setOnClickListener {
            onProductClick(product)
        }
    }
    override fun getItemCount() = productList.size

    private fun decodeImage(imageString: String): Bitmap {
        val decodedString = Base64.decode(imageString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    fun updateProducts(newProducts: List<ProductResponse>) {
        productList= newProducts
        notifyDataSetChanged()
    }
}
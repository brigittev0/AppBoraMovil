package pe.edu.idat.appborabora.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.data.dto.response.ProductoDashboardResponse
import pe.edu.idat.appborabora.util.Cart
import pe.edu.idat.appborabora.util.ProductCart

class CartAdapter(private var productList: List<ProductCart>, private val onProductQuantityChanged: () -> Unit) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvProdCard: TextView = itemView.findViewById(R.id.tvProdCart)
        val tvPrecioPDC: TextView = itemView.findViewById(R.id.tvPrecioPDC)
        val imgcart: ImageView = itemView.findViewById(R.id.img_cart)
        val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidad)

        val incrementButton: CardView = itemView.findViewById(R.id.increment)
        val decrementButton: CardView = itemView.findViewById(R.id.decrement)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.tvProdCard.text = currentItem.producto.description
        val unitPrice = currentItem.producto.price
        holder.tvPrecioPDC.text = "S/. ${String.format("%.2f", currentItem.quantity * unitPrice)}"
        holder.tvCantidad.text = currentItem.quantity.toString()

        // Decodifica la imagen en base64
        val imageBitmap = decodeImage(currentItem.producto.image)
        holder.imgcart.setImageBitmap(imageBitmap)

        // Eliminar
        val btnDelete: ImageView = holder.itemView.findViewById(R.id.btnDeleteCart)
        btnDelete.setOnClickListener {
            // Elimina el producto de la lista en Cart
            Cart.eliminarProducto(position)
            // Actualiza la lista en el adaptador
            productList = Cart.obtenerProductos()
            notifyDataSetChanged()
            Toast.makeText(holder.itemView.context, "Producto eliminado", Toast.LENGTH_SHORT).show()
            onProductQuantityChanged.invoke() // Llama al callback cuando se elimina un producto
        }

        //Incremento - Decremento
        holder.tvCantidad.text = currentItem.quantity.toString()

        holder.incrementButton.setOnClickListener {
            if (currentItem.quantity < currentItem.producto.stock) {
                currentItem.quantity++
                Log.d("CartAdapter", "Incrementar cantidad: ${currentItem.quantity}") // Agrega un mensaje de registro
                Cart.actualizarCantidadProducto(currentItem, currentItem.quantity)
                holder.tvCantidad.text = currentItem.quantity.toString()
                holder.tvPrecioPDC.text = "S/. ${String.format("%.2f", currentItem.subtotal)}"
                notifyItemChanged(position)
                onProductQuantityChanged.invoke() // Llama al callback cuando se incrementa la cantidad
            } else {
                Toast.makeText(holder.itemView.context, "No puedes agregar más de este producto. Stock limitado.", Toast.LENGTH_SHORT).show()
            }
        }

        holder.decrementButton.setOnClickListener {
            if (currentItem.quantity > 1) {
                currentItem.quantity--
                Log.d("CartAdapter", "Decrementar cantidad: ${currentItem.quantity}") // Agrega un mensaje de registro
                Cart.actualizarCantidadProducto(currentItem, currentItem.quantity)
                holder.tvCantidad.text = currentItem.quantity.toString()
                holder.tvPrecioPDC.text = "S/. ${String.format("%.2f", currentItem.subtotal)}"
                notifyItemChanged(position)
                onProductQuantityChanged.invoke() // Llama al callback cuando se decrementa la cantidad
            }
        }
    }

    override fun getItemCount() = productList.size

    fun actualizarProductos(productos: List<ProductCart>) {
        this.productList = productos
        notifyDataSetChanged()
    }

    private fun decodeImage(imageString: String): Bitmap {
        val decodedString = Base64.decode(imageString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }
}
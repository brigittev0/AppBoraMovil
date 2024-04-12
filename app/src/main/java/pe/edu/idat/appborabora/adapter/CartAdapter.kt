package pe.edu.idat.appborabora.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.data.dto.response.ProductoDashboardResponse
import pe.edu.idat.appborabora.util.Cart
import pe.edu.idat.appborabora.util.ProductCart

class CartAdapter(private var productList: List<ProductCart>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvProdCard: TextView = itemView.findViewById(R.id.tvProdCart)
        val tvPrecioPDC: TextView = itemView.findViewById(R.id.tvPrecioPDC)
        val imgcart: ImageView = itemView.findViewById(R.id.img_cart)
        val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidad) // Asegúrate de tener un TextView para la cantidad en tu layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.tvProdCard.text = currentItem.producto.description
        holder.tvPrecioPDC.text = "S/. ${currentItem.producto.price}"
        holder.tvCantidad.text = currentItem.cantidad.toString()

        Glide.with(holder.itemView.context)
            .load(currentItem.producto.image)
            .into(holder.imgcart)
    }

    override fun getItemCount() = productList.size

    fun actualizarProductos(productos: List<ProductCart>) {
        this.productList = productos
        notifyDataSetChanged()
    }
}
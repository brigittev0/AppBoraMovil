package pe.edu.idat.appborabora.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import pe.edu.idat.appborabora.R
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
class ProductoAdapter(private val context: Context) : RecyclerView.Adapter<ProductoAdapter.ProductViewHolder>() {
    private var productList: List<ProductDTO> = ArrayList()

    fun setProductList(productList: List<ProductDTO>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecio)
        val  tvStock: TextView = view.findViewById(R.id.tvStock)
        val  tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val ivProducto: ImageView = view.findViewById(R.id.ivProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product_admin, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.tvNombre.text = product.name
        holder.tvPrecio.text = "Precio: ${product.price}"
        holder.tvStock.text = "Stock: ${product.stock}"
        holder.tvDescripcion.text = product.description
        Glide.with(holder.itemView.context).load(product.image).into(holder.ivProducto)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
package pe.edu.idat.appborabora.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.response.ProductoDashboardResponse

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
        holder.tvPrecio.text = product.price.toString()
        Glide.with(holder.itemView.context).load(product.image).into(holder.ivProducto)
        // Aquí puedes cargar la imagen del producto usando Glide o Picasso
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecio)
        val ivProducto: ImageView = view.findViewById(R.id.ivProducto)
    }
}
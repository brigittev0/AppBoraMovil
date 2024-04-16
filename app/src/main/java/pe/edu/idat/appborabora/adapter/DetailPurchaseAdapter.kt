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
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.databinding.ItemCompraProductoBinding
import pe.edu.idat.appborabora.util.ProductCart

class DetailPurchaseAdapter(private val context: Context, private val productList: List<ProductCart>) :
    RecyclerView.Adapter<DetailPurchaseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.tvnombreproducto.text = product.producto.name
        holder.tvmarca.text = product.producto.name
        holder.tvcantidad.text = product.cantidad.toString()
    }

    override fun getItemCount() = productList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvnombreproducto: TextView = itemView.findViewById(R.id.tvnombreproducto)
        val tvmarca: TextView = itemView.findViewById(R.id.tvmarca)
        val tvprecioproducto: TextView = itemView.findViewById(R.id.tvprecioproducto)
        val tvcantidad: TextView = itemView.findViewById(R.id.tvcantidad)
        val imgdetalleprod: ImageView= itemView.findViewById(R.id.imgdetalleprod)
    }
}
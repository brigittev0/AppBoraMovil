package pe.edu.idat.appborabora.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.response.ProductDTO

class ListProductAdapter (
    private val productList: List<ProductDTO>,
    private val onCategoryClickListener: OnCategoryClickListener
)  : RecyclerView.Adapter<ListProductAdapter.ProductViewHolder>() {

    interface OnCategoryClickListener {
        fun onCategoryClick(categoryId: Int)
    }

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
        Glide.with(holder.itemView)
            .load(product.image)
            .into(holder.imgProducto)
        holder.nombreProducto.text = product.name
        holder.txtPrecioProducto.text = product.price.toString()
        holder.btnOrdenar.setOnClickListener {
            // Aquí debes manejar el evento de clic en el botón Ordenar
        }
        holder.btnVerDetalle.setOnClickListener {
            // Aquí llamas a onCategoryClick con el ID de la categoría del producto
            onCategoryClickListener.onCategoryClick(product.categoryId)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
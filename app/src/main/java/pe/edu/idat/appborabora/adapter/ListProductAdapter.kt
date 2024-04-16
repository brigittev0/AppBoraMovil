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

class ListProductAdapter(private val productList: List<ProductDTO>) : RecyclerView.Adapter<ListProductAdapter.ProductViewHolder>() {

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
        holder.txtPrecioProducto.text = product.price.toString()
        // Establecer la imagen del producto
        // Esto asume que tienes una forma de convertir una URL de imagen o un identificador de recurso en un Drawable
        // Si tu 'image' es una URL, puedes usar una biblioteca como Glide o Picasso para cargar la imagen
        // Si tu 'image' es un identificador de recurso, puedes usar `ContextCompat.getDrawable()`
        // Aquí hay un ejemplo con Glide:
        // Glide.with(holder.itemView.context).load(product.image).into(holder.imgProducto)
        // Establecer los listeners de los botones
        holder.btnOrdenar.setOnClickListener {
            // Aquí puedes manejar el clic en el botón 'Ordenar'
        }
        holder.btnVerDetalle.setOnClickListener {
            // Aquí puedes manejar el clic en el botón 'Ver Detalle'
        }
    }
    override fun getItemCount() = productList.size
}
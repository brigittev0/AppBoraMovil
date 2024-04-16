package pe.edu.idat.appborabora.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.response.CategoryResponse

class CategoryAdapter(
    private var categories: List<CategoryResponse>,
    private val onCategoryClick: (CategoryResponse) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombreCategoria: TextView = view.findViewById(R.id.txtNombreCategoria)
        val imgCategoria: ImageView = view.findViewById(R.id.imgCategoria)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_categorias, parent, false)

        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.txtNombreCategoria.text = category.name
        Glide.with(holder.itemView.context).load(category.image).into(holder.imgCategoria)
        holder.itemView.setOnClickListener {
            onCategoryClick(category)
        }
    }
    override fun getItemCount() = categories.size

    fun updateData(newCategories: List<CategoryResponse>) {
        categories = newCategories
        notifyDataSetChanged()
    }
}
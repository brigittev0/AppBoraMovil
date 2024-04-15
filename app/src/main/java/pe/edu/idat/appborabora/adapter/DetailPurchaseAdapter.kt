package pe.edu.idat.appborabora.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.databinding.ItemCompraProductoBinding

class DetailPurchaseAdapter /*: RecyclerView.Adapter< DetailPurchaseAdapter.ViewHolder>()*/ {

    /*
    private var lista: ArrayList<ProductDTO> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCompraProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val compra = lista[position]
        holder.binding.tvnombreproducto.text = compra.nombre
        holder.binding.tvmarca.text = compra.marca

        val precioSimbolo = "S/ ${compra.precio}"
        holder.binding.tvprecioproducto.text = precioSimbolo

        val cantidadSimbolo = "Unidades: ${compra.cantidad}"
        holder.binding.tvcantidad.text = cantidadSimbolo

        Glide.with(holder.itemView.context)
            .load(compra.imagen)
            .into(holder.binding.imgdetalleprod)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    fun setData(data: ArrayList<ProductoCompraResponse>) {
        lista.clear()
        lista.addAll(data)
        notifyDataSetChanged()
    }



    inner class ViewHolder(val binding: ItemCompraProductoBinding) : RecyclerView.ViewHolder(binding.root) */
}
package pe.edu.idat.appborabora.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.response.PurchaseDTO

class PurchaseAdapter(private val context: Context) : RecyclerView.Adapter<PurchaseAdapter.CompraViewHolder>() {


    private var listaCompras: List<PurchaseDTO> = ArrayList()

    fun setProductList(listaCompras: List<PurchaseDTO>) {
        this.listaCompras = listaCompras
        notifyDataSetChanged()
    }
    class CompraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNroCompra: TextView = itemView.findViewById(R.id.tvnrocompra)
        val tvFCompra: TextView = itemView.findViewById(R.id.tvfcompra)
        val tvMPago: TextView = itemView.findViewById(R.id.tvmpago)
        val tvTotal: TextView = itemView.findViewById(R.id.tvtotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompraViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_historialcompra, parent, false)
        return CompraViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompraViewHolder, position: Int) {
        val compra = listaCompras[position]
        holder.tvNroCompra.text = compra.orderId.toString()
        holder.tvFCompra.text = compra.purchaseDate
        holder.tvMPago.text = compra.paymentId.toString()
        holder.tvTotal.text = compra.total.toString()
            // Aquí puedes manejar el evento de clic en el botón
        }

    override fun getItemCount() = listaCompras.size

}

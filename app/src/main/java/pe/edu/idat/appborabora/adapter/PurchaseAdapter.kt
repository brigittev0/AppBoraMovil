package pe.edu.idat.appborabora.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.data.dto.response.PurchaseDTO
import pe.edu.idat.appborabora.data.dto.response.PurchasetResponse

class PurchaseAdapter(
    private val context: Context,
    private var listener: OnDetalleCompraClickListener?
) : RecyclerView.Adapter<PurchaseAdapter.CompraViewHolder>() {

    private var listaCompras: List<PurchasetResponse> = ArrayList()

    fun setProductList(listaCompras: List<PurchasetResponse>) {
        this.listaCompras = listaCompras
        notifyDataSetChanged()
    }

    fun setOnDetalleCompraClickListener(listener: OnDetalleCompraClickListener) {
        this.listener = listener
    }

    class CompraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNroCompra: TextView = itemView.findViewById(R.id.tvnrocompra)
        val tvFCompra: TextView = itemView.findViewById(R.id.tvfcompra)
        val tvMPago: TextView = itemView.findViewById(R.id.tvmpago)
        val tvTotal: TextView = itemView.findViewById(R.id.tvtotal)
        val btnDetalleProducto: AppCompatImageButton = itemView.findViewById(R.id.btnDetalleProductos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompraViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_historialcompra, parent, false)
        return CompraViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompraViewHolder, position: Int) {
        val compra = listaCompras[position]
        holder.tvNroCompra.text = compra.purchase_id.toString()
        holder.tvFCompra.text = compra.purchaseDate.toString()
        holder.tvMPago.text = compra.paymentId.toString()
        holder.tvTotal.text = compra.total.toString()

        // Aquí puedes manejar el evento de clic en el botón
        holder.btnDetalleProducto.setOnClickListener {
            listener?.onDetalleCompraClick(compra)
        }
    }

    override fun getItemCount() = listaCompras.size

    interface OnDetalleCompraClickListener {
        fun onDetalleCompraClick(compra: PurchasetResponse)
    }
}
package pe.edu.idat.appborabora.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.slider.SliderItem

class SliderAdapter(private val context: Context) : SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {
    private val mSliderItems: MutableList<SliderItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_image_slider, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem: SliderItem = mSliderItems[position]

        viewHolder.textView.text = sliderItem.titulo
        viewHolder.textView.textSize = 16f
        viewHolder.textView.setTextColor(Color.WHITE)
        Glide.with(viewHolder.itemView)
            .load(sliderItem.imagen)
            .fitCenter()
            .into(viewHolder.imageView)
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    fun updateItem(lista: List<SliderItem>) {
        mSliderItems.clear()
        mSliderItems.addAll(lista)
        notifyDataSetChanged()
    }

    inner class SliderAdapterVH(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_auto_image_slider)
        val textView: TextView = itemView.findViewById(R.id.tv_auto_image_slider)
    }
}
package pe.edu.idat.appborabora.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.smarteist.autoimageslider.SliderViewAdapter
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.slider.SliderItem

class SliderAdapter(private val context: Context, private var mSliderItems: MutableList<SliderItem>) : SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {

    fun renewItems(sliderItems: MutableList<SliderItem>) {
        this.mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_image_slider, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem = mSliderItems[position]

        viewHolder.imageViewBackground.setImageResource(sliderItem.image)
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    class SliderAdapterVH(var itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        var imageViewBackground: ImageView = itemView.findViewById(R.id.iv_auto_image_slider)
    }
}
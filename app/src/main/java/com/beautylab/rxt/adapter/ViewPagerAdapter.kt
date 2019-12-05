package com.beautylab.rxt.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beautylab.rxt.App
import com.beautylab.rxt.R
import kotlinx.android.synthetic.main.item_view_pager.view.*

class ViewPagerAdapter(private val itemClickListener: (Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val array_image_product = intArrayOf(
        R.drawable.scroll_ad_01,
        R.drawable.scroll_ad_02,
        R.drawable.scroll_ad_03,
        R.drawable.scroll_ad_04,
        R.drawable.scroll_ad_05
    )

    private val array_title_product = App.instance.resources.getStringArray(R.array.view_holder_recp)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false))

    override fun getItemCount(): Int = array_image_product.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = holder.itemView.run {
        ivPicVH2.setImageDrawable(App.instance.getDrawable(array_image_product[position]))
        tvNameVH2.text = array_title_product[position]
    }

    private inner class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                Log.d("logi", "Click!") // WORKS!!!
                itemClickListener(adapterPosition)
            }
        }
    }
}
package com.beauty.lab.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beauty.lab.R
import com.beauty.lab.models.RecipeModelForRVGroup
import kotlinx.android.synthetic.main.item_view_group_second.view.*

class GroupAdapter(val recipesForRV: ArrayList<RecipeModelForRVGroup>, private val itemClickListener: (Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        GroupVH(LayoutInflater.from(parent.context).inflate(R.layout.item_view_group_second, parent, false))

    override fun getItemCount() = recipesForRV.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)= holder.itemView.run {
        ivGroupSecond.setImageDrawable(recipesForRV[position].pic)
        tvNameItem.text = recipesForRV[position].title
        ratingBar.rating = recipesForRV[position].rate.toFloat()
        tvDescription.text = recipesForRV[position].description
    }

    private inner class GroupVH(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                Log.d("logi", "Click!") // WORKS!!!
                itemClickListener(adapterPosition)
            }
        }
    }
}

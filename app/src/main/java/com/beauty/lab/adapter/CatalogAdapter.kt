package com.beauty.lab.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beauty.lab.R
import com.beauty.lab.models.RecipeModelForRV
import kotlinx.android.synthetic.main.item_view_group_main.view.*

class CatalogAdapter(val groupsForRV: ArrayList<RecipeModelForRV>, private val itemClickListener: (Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        GroupVH(LayoutInflater.from(parent.context).inflate(R.layout.item_view_group_main, parent, false))

    override fun getItemCount() = groupsForRV.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)= holder.itemView.run {
        ivGroupMain.setImageDrawable(groupsForRV[position].pic)
        tvGroupMain.text = groupsForRV[position].title
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

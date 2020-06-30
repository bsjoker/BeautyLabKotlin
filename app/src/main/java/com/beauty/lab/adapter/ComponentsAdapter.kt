package com.beauty.lab.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beauty.lab.R
import com.beauty.lab.models.ComponentsModelForRV
import kotlinx.android.synthetic.main.item_steps.view.*

class ComponentsAdapter(val steps: ArrayList<ComponentsModelForRV>) : RecyclerView.Adapter<ComponentsAdapter.StepsViewHolder>() {
    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        holder.component.text = steps[position].component
        holder.count.text = steps[position].count
    }

    override fun getItemCount() = steps.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder{
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_steps, parent, false)
        return StepsViewHolder(view)
    }

    class StepsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val component: TextView = itemView.component
        val count: TextView = itemView.count
        
    }


}

package com.example.foodorder

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.DB.UserData
import com.example.foodorder.DB.UserDataViewModel

class MyAdapterPopularItems(var popularArrayList: ArrayList<PopularItems>, var context: Context, var listener: AddMyAdapterPopular) :
RecyclerView.Adapter<MyAdapterPopularItems.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = MyViewHolder(LayoutInflater.from(context).inflate(R.layout.each_popular, parent, false))
        itemView.addBtn.setOnClickListener {
            listener.onItemClicked(popularArrayList[itemView.adapterPosition])
        }
        return itemView
    }

    override fun getItemCount(): Int {
        return popularArrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = popularArrayList[position]
        holder.popTitle.text = currentItem.titlePop
        holder.popImage.setImageResource(currentItem.picPop)
        holder.popFee.text = currentItem.fee.toString()
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var popTitle: TextView = itemView.findViewById<TextView>(R.id.titlePopular)
        var popImage: ImageView = itemView.findViewById<ImageView>(R.id.itemsPopular)
        var popFee: TextView = itemView.findViewById<TextView>(R.id.fee)
        var addBtn: TextView = itemView.findViewById<TextView>(R.id.plusadd)
    }
}
interface AddMyAdapterPopular {
    fun onItemClicked(pItem: PopularItems)
}
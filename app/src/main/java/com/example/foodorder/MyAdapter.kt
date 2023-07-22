package com.example.foodorder
import android.app.Activity
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(var categoryArrayList: ArrayList<CategoryItems>, var context: Activity, var listener: AddMyAdapterCategory) :
RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val itemView = MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.each_category, parent, false))
        itemView.catPic.setOnClickListener {
            listener.onCategoryItemClicked(categoryArrayList[itemView.adapterPosition])
        }
        return (itemView)
    }

    override fun getItemCount(): Int {
        return categoryArrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = categoryArrayList[position]
        holder.catTitle.text = currentItem.title
        holder.catPic.setImageResource(currentItem.pic)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catTitle: TextView = itemView.findViewById<TextView>(R.id.cateoryTitle)
        val catPic: ImageView = itemView.findViewById<ImageView>(R.id.categoryPic)

    }

}
interface AddMyAdapterCategory {
    fun onCategoryItemClicked(categoryItem: CategoryItems)
}
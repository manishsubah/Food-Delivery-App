package com.example.foodorder.DBcart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.R
import java.io.File


class CartDataAdapter(private val context: Context, private val listenerDelete: ICartDataAdapter) :
RecyclerView.Adapter<CartDataAdapter.CartViewHolder>() {

    private val allCart = ArrayList<CartData>()
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val viewHolder = CartViewHolder(LayoutInflater.from(context).inflate(R.layout.each_cart, parent, false))
        viewHolder.deleteCart.setOnClickListener {
            listenerDelete.onCartItemClicked(allCart[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return allCart.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentCart = allCart[position]

        holder.titleFood.text = currentCart.titleFood.toString()

        val imagePath = currentCart.imageFood
        val file = File(imagePath)
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
        holder.imageFood.setImageBitmap(bitmap)

        val unit: Int = currentCart.units.toInt()
        val rate: Double = currentCart.unitPrice.toDouble()
        val totalPrice: Double = unit * rate
        holder.units.text = currentCart.units.toString()
        holder.rate.text = currentCart.unitPrice.toString()
        holder.total.text = totalPrice.toString()

    }

    inner class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleFood: TextView = itemView.findViewById<TextView>(R.id.titleFood)
        val imageFood: ImageView = itemView.findViewById<ImageView>(R.id.imageFood)
        val units: TextView = itemView.findViewById<TextView>(R.id.units)
        val rate: TextView = itemView.findViewById<TextView>(R.id.rate)
        val total: TextView = itemView.findViewById<TextView>(R.id.total)
        val deleteCart:ImageView = itemView.findViewById<ImageView>(R.id.deleteCart)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<CartData>) {
        allCart.clear()
        allCart.addAll(newList)
        notifyDataSetChanged()
    }

//    fun setOnItemClickListener(listener: OnItemClickListener) {
//        onItemClickListener = listener
//    }
    interface OnItemClickListener {
        fun onItemClick(data: CartData)
    }
    fun getItem(position: Int): CartData {
        return allCart[position]
    }

}
interface ICartDataAdapter {
    fun onCartItemClicked(cartData: CartData)
}


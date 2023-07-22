package com.example.foodorder

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.util.query
import com.example.foodorder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AddMyAdapterPopular, AddMyAdapterCategory {
    private lateinit var binding: ActivityMainBinding
    lateinit var categoryArrayList: ArrayList<CategoryItems>
    lateinit var popularArrayList: ArrayList<PopularItems>
    lateinit var userName: String
    private lateinit var container: LinearLayout
    private lateinit var itemList: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userName = intent.extras?.getString("usernam").toString()
        binding.username.text = intent.extras?.getString("usernam")


        container = findViewById(R.id.container)
        itemList = ArrayList()
        itemList.add("Biryani")
        itemList.add("Dhosa")
        itemList.add("Idly")
        itemList.add("Jalebi")
        itemList.add("Laddo")
        itemList.add("Rasgulla")
        itemList.add("Samosa")
        itemList.add("Pizza")
        itemList.add("Burger")
        itemList.add("Lassi")


        binding.findFood.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    filterItems(it)
                }
                return true
            }
        })

        categoryItemsShow()
        popularItemsShow()

    }
    private fun categoryItemsShow() {
        binding.categoriesView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        val tiles = arrayOf("Biryani", "Dhosa", "Idly", "Jalebi", "Laddo", "Rasgulla", "Samosa")
        val pics = arrayOf(R.drawable.pic1biryani, R.drawable.pic2dhosa, R.drawable.pic3idlli, R.drawable.pic4jalebi, R.drawable.pic5ladoo,
            R.drawable.pic6rasgolla, R.drawable.pic7samosa)
        val fee = arrayOf(150, 100, 20, 15, 20, 20, 25)
        categoryArrayList = arrayListOf<CategoryItems>()

        for(index in tiles.indices) {
            val categories = CategoryItems(tiles[index], pics[index], fee[index])
            categoryArrayList.add(categories)
        }
        binding.categoriesView.adapter = MyAdapter(categoryArrayList, this, this)
    }
    private fun popularItemsShow() {
        binding.popularView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val title = arrayOf("Pizza", "Samosa", "Burger", "Lassi")
        val pic = arrayOf(R.drawable.pizza, R.drawable.samosaa, R.drawable.pop_2, R.drawable.lassinew)
        val description = arrayOf("Pizza is western food.", "Samossa is indian food.", "Burger is western food.", "lassssi is indian drink.")
        val fee = arrayOf(120, 25, 55, 25)

        popularArrayList = arrayListOf<PopularItems>()
        for(index in title.indices) {
            val categories = PopularItems(title[index], pic[index], description[index], fee[index])
            popularArrayList.add(categories)
        }
        binding.popularView.adapter = MyAdapterPopularItems(popularArrayList, this, this)

        binding.cartListBtn.setOnClickListener {
            val intent = Intent(this, CartList::class.java)
            intent.putExtra("username", userName)
            startActivity(intent)
        }
        binding.personpic.setOnClickListener {
            val intent = Intent(this, PersonDetail::class.java)
            intent.putExtra("username", userName)
            startActivity(intent)
        }
        binding.homeTab.setOnClickListener {
            if(this is MainActivity) {
                Toast.makeText(this, "main running.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("username", userName)
                startActivity(intent)
                userName = intent.extras?.getString("username").toString()
                binding.username.text = userName
            }
        }
        binding.support.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:"+91970800198)
            startActivity(intent)
        }
    }

    override fun onItemClicked(pItem: PopularItems) {
        val intent = Intent(this, ShowDetailItem::class.java)
        intent.putExtra("title", pItem.titlePop.toString())
        intent.putExtra("price", pItem.fee.toString())
        intent.putExtra("pic", pItem.picPop.toInt())
        intent.putExtra("username", userName)
        startActivity(intent)
    }

    override fun onCategoryItemClicked(categoryItem: CategoryItems) {
        val intent = Intent(this, ShowDetailItem::class.java)
        intent.putExtra("title", categoryItem.title.toString())
        intent.putExtra("price", categoryItem.fee.toString())
        intent.putExtra("pic", categoryItem.pic.toInt())
        intent.putExtra("username", userName)
        startActivity(intent)
    }

    fun filterItems(query: String) {
        container.removeAllViews()

        if (query.isNotEmpty()) {
            itemList.forEach { item ->
                if (item.contains(query, ignoreCase = true)) {
                    val itemView = LayoutInflater.from(this).inflate(R.layout.searchview_itemlayout, container, false)
                    val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)
                    textViewItem.text = item
                    val cardViewItem: CardView = itemView.findViewById(R.id.cartViewItem)
                    cardViewItem.setOnClickListener {
                        Toast.makeText(this, "Clicked: $item", Toast.LENGTH_SHORT).show()
                        if(item == "Biryani") {
                            val intent = Intent(this, ShowDetailItem::class.java)
                            intent.putExtra("title", "Biryani")
                            intent.putExtra("price", "120")
                            intent.putExtra("pic", R.drawable.pic1biryani)
                            intent.putExtra("username", userName)
                            startActivity(intent)
                        }
                    }
                    container.addView(itemView)
                }
            }
        }
    }

}



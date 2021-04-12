package com.techpig.rogys.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techpig.rogys.R
import com.techpig.rogys.models.Product
import com.techpig.rogys.utils.GlideLoader
import kotlinx.android.synthetic.main.item_list_layout.view.*

open class ProductListAdapter(
    private val context: Context,
    private var listOfProducts: ArrayList<Product>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = listOfProducts[position]

        if (holder is ProductViewHolder) {
            GlideLoader(context).loadProductPicture(model.image, holder.itemView.product_image)
            holder.itemView.product_title.text = model.title
            holder.itemView.product_price.text = "$${model.price}"
        }
    }

    override fun getItemCount(): Int {
        return listOfProducts.size
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
package com.example.myapplication.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.model.Product
import com.example.myapplication.data.model.ProductDM

class HomeAdapter (
    private val products: List<Product>,
    private val onFavoriteClick: (Product) -> Unit
) : RecyclerView.Adapter<HomeAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.product_title)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val favoriteButton: ImageView = itemView.findViewById(R.id.overlay_iconHeart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_adapter, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productName.text = product.title
        holder.productPrice.text = "$${product.price}"

        Glide.with(holder.itemView.context)
            .load(product.thumbnail)
            .into(holder.productImage)
        // Update the heart icon based on the isFavorite status
        holder.favoriteButton.setImageResource(
            if (product.isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
        )


        // Handle favorite button click
        holder.favoriteButton.setOnClickListener {
            onFavoriteClick(product)
            // Update the heart icon immediately after the click
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = products.size
}
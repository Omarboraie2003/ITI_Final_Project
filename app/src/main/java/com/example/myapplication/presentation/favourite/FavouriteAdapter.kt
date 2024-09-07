package com.example.myapplication.presentation.favourite

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

class FavouriteAdapter (
    private val products: List<Product>,
    private val onFavoriteClick: (Product) -> Unit
) : RecyclerView.Adapter<FavouriteAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val favoriteButton: ImageView = itemView.findViewById(R.id.fav_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_adapter, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int =products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productName.text = product.title
        holder.productPrice.text = "$${product.price}"
        Glide.with(holder.itemView.context)
            .load(product.image)
            .into(holder.productImage)

        // Load the product image using Glide or any image loading library
        holder.favoriteButton.setImageResource(
           R.drawable.ic_heart_outline
        )
        // Handle click on the favorite button
        holder.favoriteButton.setOnClickListener {
            // Call the callback function
            onFavoriteClick(product)
            notifyItemRemoved(position)
    }
        }
    }

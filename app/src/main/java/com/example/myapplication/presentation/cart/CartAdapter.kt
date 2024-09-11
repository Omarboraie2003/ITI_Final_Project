package com.example.myapplication.presentation.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.model.Product
import com.example.myapplication.presentation.favourite.FavouriteAdapter
import com.example.myapplication.presentation.favourite.FavouriteAdapter.ProductViewHolder

class CartAdapter (
    private val products: MutableList<Product>,
    private val onInCartClick: (Product) -> Unit
) : RecyclerView.Adapter<CartAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.product_name_cart)
        val productPrice: TextView = itemView.findViewById(R.id.product_price_cart)
        val productImage: ImageView = itemView.findViewById(R.id.product_image_cart)
        val InCartButton: ImageView = itemView.findViewById(R.id.cart_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_adapter, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int =products.size
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productName.text = product.title
        holder.productPrice.text = "$${product.price}"

        Glide.with(holder.itemView.context)
            .load(product.thumbnail)
            .into(holder.productImage)
        // Load the product image using Glide or any image loading library

        // Handle click on the favorite button
        holder.InCartButton.setOnClickListener {
            // Call the callback function
          onInCartClick(product)
            holder.InCartButton.setImageResource(
                if (product.inCart) R.drawable.baseline_shopping_cart_24 else R.drawable.ic_shopping_cart
            )
           // notifyItemRemoved(position)
        }
    }
    // Method to update the product list and refresh the adapter
    fun updateProducts(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }
    }

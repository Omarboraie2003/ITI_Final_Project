package com.example.myapplication.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProductDetailsBinding


class ProductDetailsFragment : Fragment() {
    private lateinit var  binding : FragmentProductDetailsBinding
    private val viewPageAdapter by lazy { ViewPageToImage() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductDetailsBinding.inflate(inflater)


        val productName = arguments?.getString("productName")
        val productDescription = arguments?.getString("productDescription")
        val productPrice = arguments?.getDouble("productPrice")
        val productCategory = arguments?.getString("productCategory")
        val productBrand = arguments?.getString("productBrand")
        val productImage = arguments?.getString("productImage")
        val productDisc = arguments?.getDouble("productDisc")
        val productRating = arguments?.getDouble("productRating")

        binding.tvProductName.text = productName
        binding.tvProductDescription.text = productDescription
        binding.tvProductPrice.text = "$productPrice $"
        binding.tvProductCategory.text = productCategory
        binding.tvProductBrand.text = productBrand
        binding.tvrating.text = "$productRating "
        binding.tvProductdisc.text = "$productDisc % "

        //val imageView = view?.findViewById<ImageView>(R.id.viewPagerProductImages)

        productImage?.let {
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.placeholder_image)
                .into(binding.viewPagerProductImages)
        }

        binding.imageClose.setOnClickListener {
            findNavController().navigateUp()
        }
//        binding.buttonAddToCart.setOnClickListener {
//
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Details" // Set your fragment's title here
    }
}
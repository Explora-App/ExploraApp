package com.example.explora.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.explora.R
import com.example.explora.data.repository.HomeRepository
import com.example.explora.databinding.ActivityDetailPlantBinding
import com.example.explora.ui.viewmodel.HomeViewModel
import com.example.explora.ui.viewmodel.HomeViewModelFactory

class DetailPlantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPlantBinding
//    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlantBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val name = intent.getStringExtra("name")
        val imageUrl = intent.getStringExtra("imageUrl")

        val imageUriString = intent.getStringExtra("imageUri")
        val imageUri = Uri.parse(imageUriString)

//        val name = intent.getStringExtra("plantName")
//        val name = intent.getStringExtra("latinName")
//        val name = intent.getStringExtra("description")
//        val name = intent.getStringExtra("funFact")
//        val name = intent.getStringExtra("rootType")
//        val name = intent.getStringExtra("seedType")
//        val name = intent.getStringExtra("leafType")
//        val name = intent.getStringExtra("stemType")

//        val repository = HomeRepository()
//        val factory = HomeViewModelFactory(repository)
//        val viewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)


        // Observe LiveData dari ViewModel
//        viewModel.currentImageUri.observe(this) { uri ->
//            Log.d("Image URI", "showImage: $uri")
//            binding.imageView.setImageURI(uri)
//        }

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

                Glide.with(this)
            .load(imageUri)
            .into(binding.imageView)

//        Glide.with(this)
//            .load(imageUrl)
//            .into(binding.imageView)

        // Tampilkan nama di TextView
        binding.descriptionTextView.text = name
//        binding.descriptionTextView.text = "Nama Tanaman: $name"
    }
}
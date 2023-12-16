package com.example.explora.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.explora.R
import com.example.explora.databinding.ActivityDetailPlantBinding

class DetailPlantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPlantBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlantBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val name = intent.getStringExtra("name")
        val imageUrl = intent.getStringExtra("imageUrl")


        // Gunakan Glide atau library lainnya untuk memuat gambar dari URL
        Glide.with(this)
            .load(imageUrl)
            .into(binding.imageView)

        // Tampilkan nama di TextView
        binding.descriptionTextView.text = name
    }
}
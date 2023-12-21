package com.example.explora.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.explora.databinding.ActivityDetailPlantBinding

class DetailPlantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPlantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlantBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val imageUriString = intent.getStringExtra("imageUri")
        val imageUri = Uri.parse(imageUriString)

        val plantName = intent.getStringExtra("plantName")
        val latinName = intent.getStringExtra("latinName")
        val description = intent.getStringExtra("description")
        val funFact = intent.getStringExtra("funFact")
        val rootType = intent.getStringExtra("rootType")
        val seedType = intent.getStringExtra("seedType")
        val leafType = intent.getStringExtra("leafType")
        val stemType = intent.getStringExtra("stemType")
        val uses = intent.getStringExtra("kegunaan")

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        Glide.with(this)
            .load(imageUri)
            .into(binding.imageView)


        // Tampilkan nama di TextView
        binding.plantName.text = plantName
        binding.latinName.text = latinName
        binding.description.text = description
        binding.funFact.text = funFact
        binding.rootType.text = rootType
        binding.seedType.text = seedType
        binding.leafType.text = leafType
        binding.stemType.text = stemType
        binding.uses.text = uses
    }
}
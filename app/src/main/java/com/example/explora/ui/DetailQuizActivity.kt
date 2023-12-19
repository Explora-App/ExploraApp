package com.example.explora.ui

import android.animation.Animator
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.explora.databinding.ActivityDetailQuizBinding

class DetailQuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailQuizBinding
    private var isBackVisible = false // Boolean untuk mengecek apakah sisi belakang kartu sedang ditampilkan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Atur cameraDistance untuk efek 3D yang lebih baik
        val distance = 16000
        val scale = resources.displayMetrics.density * distance
        binding.frontCard.cameraDistance = scale
        binding.backCard.cameraDistance = scale

        // Mengambil data yang dikirim melalui Intent
        val pertanyaan = intent.getStringExtra("pertanyaan")
        val pilihanBenar = intent.getStringExtra("pilihan_benar")

        // Gunakan data yang diambil untuk menampilkan informasi pada tampilan Anda
        binding.questionTextView.text = pertanyaan
        binding.answerTextView.text = pilihanBenar

        binding.backButton.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        binding.cardView.setOnClickListener {
            val animatorOut = ObjectAnimator.ofFloat(
                binding.cardView,
                "rotationY",
                0f,
                90f
            ) // Membuat animator untuk efek flip keluar
            animatorOut.duration = 400 // Durasi animasi

            val animatorIn = ObjectAnimator.ofFloat(
                binding.cardView,
                "rotationY",
                -90f,
                0f
            ) // Membuat animator untuk efek flip masuk
            animatorIn.duration = 400 // Durasi animasi

            animatorOut.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    // Mengubah visibilitas kartu setelah animasi selesai
                    if (isBackVisible) {
                        binding.backCard.visibility = View.INVISIBLE
                        binding.frontCard.visibility = View.VISIBLE
                        animatorIn.start() // Memulai animasi flip masuk
                    } else {
                        binding.frontCard.visibility = View.INVISIBLE
                        binding.backCard.visibility = View.VISIBLE
                        animatorIn.start() // Memulai animasi flip masuk
                    }
                    isBackVisible = !isBackVisible
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })

            animatorOut.start() // Memulai animasi flip keluar
        }
    }
}

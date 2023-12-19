package com.example.explora.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.explora.R

class SplashActivity : AppCompatActivity() {
    private lateinit var splashImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashImage = findViewById(R.id.splashImage)

        // Load animation
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)

        // Set animation listener
        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                splashImage.startAnimation(fadeOut)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                // Redirect to MainActivity
                splashImage.visibility = View.INVISIBLE

                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        // Start animation
        splashImage.startAnimation(fadeIn)
    }
}
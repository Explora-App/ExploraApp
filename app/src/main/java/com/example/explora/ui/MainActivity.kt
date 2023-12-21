package com.example.explora.ui

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.content.ContextCompat
import android.view.OrientationEventListener
import android.view.Surface
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.explora.R
import com.example.explora.databinding.ActivityMainBinding
import com.example.explora.ui.adapter.MyAdapter
import com.example.explora.ui.util.getImageUri
import com.example.explora.ui.viewmodel.HomeViewModel
import com.example.explora.ui.viewmodel.HomeViewModelFactory
import com.example.explora.ui.viewmodel.UploadResult
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: HomeViewModel
    private var currentImageUri: Uri? = null
    private var imageCapture: ImageCapture? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, HomeViewModelFactory())
            .get(HomeViewModel::class.java)

        // Atur RecyclerView
        binding.recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.layoutManager = layoutManager
        val adapter = MyAdapter(listOf())
        binding.recyclerView.adapter = adapter

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                // Tampilkan ProgressBar
                binding.progressBar.visibility = View.VISIBLE
            } else {
                // Sembunyikan ProgressBar
                binding.progressBar.visibility = View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.getQuiz()
        }

        // Amati quizResponse dan perbarui Adapter setiap kali data berubah
        viewModel.quizResponse.observe(this) { response ->
            adapter.updateData(response.data ?: listOf())
        }

        binding.captureImage.setOnClickListener {
            if (allPermissionsGranted()) {
                hideSystemUI()
                startCamera()
            } else {
                requestCameraPermission()
            }
        }

        // Observe uploadResult LiveData
        viewModel.uploadResult.observe(this) { result ->
            when (result) {
                is UploadResult.Success -> {
                    // Tampilkan hasil jika berhasil
                    Toast.makeText(this, "Upload successful: ${result.plantName}", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, DetailPlantActivity::class.java)
                    intent.putExtra("imageUri", viewModel.currentImageUri.value.toString())


                    intent.putExtra("plantName", result.plantName)
                    intent.putExtra("latinName", result.latinName)
                    intent.putExtra("description", result.description)
                    intent.putExtra("funFact", result.funFact)
                    intent.putExtra("rootType", result.rootType)
                    intent.putExtra("seedType", result.seedType)
                    intent.putExtra("leafType", result.leafType)
                    intent.putExtra("stemType", result.stemType)
                    intent.putExtra("kegunaan", result.uses)

                    startActivity(intent)
                }

                is UploadResult.Error -> {
                    // Tampilkan pesan error jika gagal
                    Toast.makeText(
                        this,
                        "Upload failed: ${result.errorMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_custom_dialog)
        dialog.setCancelable(false)

        viewModel.isUploading.observe(this) { isUploading ->
            if (isUploading) {
                // Tampilkan dialog
                dialog.show()
            } else {
                // Sembunyikan dialog
                dialog.dismiss()
            }
        }

    }

    // Fungsi untuk membuat file gambar
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            "JPEG_${System.currentTimeMillis()}_",
            ".jpg",
            storageDir
        )
        return image
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            viewModel.setImageUri(currentImageUri) // Update ViewModel
            if (viewModel.currentImageUri.value != null) {
                lifecycleScope.launch {
                    viewModel.uploadImage(viewModel.currentImageUri.value!!, this@MainActivity)
                }
            }
        }
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG)
                    .show()
                hideSystemUI()
                startCamera()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG)
                    .show()
            }
        }

    private fun requestCameraPermission() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }


    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            this.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        (this as? AppCompatActivity)?.supportActionBar?.hide()
    }


    private val orientationEventListener by lazy {
        object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }

                val rotation = when (orientation) {
                    in 45 until 135 -> Surface.ROTATION_270
                    in 135 until 225 -> Surface.ROTATION_180
                    in 225 until 315 -> Surface.ROTATION_90
                    else -> Surface.ROTATION_0
                }

                imageCapture?.targetRotation = rotation
            }
        }
    }

    override fun onStart() {
        super.onStart()
        orientationEventListener.enable()
    }

    override fun onStop() {
        super.onStop()
        orientationEventListener.disable()
    }

    companion object {
        private const val TAG = "CameraActivity"
    }
}
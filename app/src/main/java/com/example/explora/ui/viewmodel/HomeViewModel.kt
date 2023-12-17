package com.example.explora.ui.viewmodel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.explora.data.network.ApiConfig
import com.example.explora.data.network.ApiService
import com.example.explora.data.repository.HomeRepository
import com.example.explora.ui.util.reduceFileImage
import com.example.explora.ui.util.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

sealed class UploadResult {
    data class Success(val name: String, val imageUrl: String) : UploadResult()
    data class Error(val errorMessage: String) : UploadResult()
}

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _uploadResult = MutableLiveData<UploadResult>()
    val uploadResult: LiveData<UploadResult> get() = _uploadResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _currentImageUri = MutableLiveData<Uri?>()
    val currentImageUri: LiveData<Uri?> = _currentImageUri

    private val _isUploading = MutableLiveData<Boolean>()
    val isUploading: LiveData<Boolean> get() = _isUploading


    fun setImageUri(uri: Uri?) {
        _currentImageUri.value = uri
    }

    suspend fun uploadImage(imageUri: Uri, context: Context) {
        _isUploading.value = true

        // Ubah Uri menjadi File
        val file = uriToFile(imageUri, context)

        // Kurangi ukuran file gambar jika perlu
        val reducedFile = file.reduceFileImage()

        // Buat RequestBody dari File
        val requestFile = reducedFile.asRequestBody("image/jpeg".toMediaTypeOrNull())

        // Buat MultipartBody.Part dari RequestBody
        val body = MultipartBody.Part.createFormData("image", reducedFile.name, requestFile)

        // Dapatkan instance ApiService
        val apiService = ApiConfig.retrofit.create(ApiService::class.java)

        try {
            // Panggil fungsi uploadImage
            val response = apiService.uploadImage(body)

            if (response.status?.code == 200) {
                val name = response.data?.information?.name.toString()
                val imageUrl = response.data?.information?.image.toString()

//                val plantName = response.data?.information?.name.toString()
//                val latinName = response.data?.information?.name.toString()
//                val description = response.data?.information?.name.toString()
//                val uses = response.data?.information?.name.toString()
//                val funFact = response.data?.information?.name.toString()
//                val rootType = response.data?.information?.name.toString()
//                val seedType = response.data?.information?.name.toString()
//                val leafType = response.data?.information?.name.toString()
//                val stemType = response.data?.information?.name.toString()


                _uploadResult.value = UploadResult.Success(name, imageUrl)
            } else {
                // Tampilkan pesan kesalahan ke pengguna
                _uploadResult.value =
                    UploadResult.Error("HTTP Error: ${response.status?.message}")
            }
        } catch (e: Exception) {
            // error
        }
        _isUploading.value = false


    }
}


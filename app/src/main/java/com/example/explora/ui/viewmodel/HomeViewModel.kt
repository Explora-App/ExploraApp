package com.example.explora.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.explora.data.model.QuizResponse
import com.example.explora.data.network.ApiConfig
import com.example.explora.data.network.ApiService
import com.example.explora.ui.util.reduceFileImage
import com.example.explora.ui.util.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

sealed class UploadResult {
    data class Success(
        val plantName: String,
        val latinName: String,
        val description: String,
        val uses: String,
        val funFact: String,
        val rootType: String,
        val seedType: String,
        val leafType: String,
        val stemType: String
    ) : UploadResult()

    data class Error(val errorMessage: String) : UploadResult()
}

class HomeViewModel() : ViewModel() {

    private val _uploadResult = MutableLiveData<UploadResult>()
    val uploadResult: LiveData<UploadResult> get() = _uploadResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _currentImageUri = MutableLiveData<Uri?>()
    val currentImageUri: LiveData<Uri?> = _currentImageUri

    private val _isUploading = MutableLiveData<Boolean>()
    val isUploading: LiveData<Boolean> get() = _isUploading

    private val _quizResponse = MutableLiveData<QuizResponse>()
    val quizResponse: LiveData<QuizResponse> get() = _quizResponse



    fun setImageUri(uri: Uri?) {
        _currentImageUri.value = uri
    }



    suspend fun uploadImage(imageUri: Uri, context: Context) {
        _isUploading.value = true

        // Ubah Uri menjadi File
        val file = uriToFile(imageUri, context)

        val reducedFile = file.reduceFileImage()

        // Buat RequestBody dari File
        val requestFile = reducedFile.asRequestBody("image/jpeg".toMediaTypeOrNull())

        // Buat MultipartBody.Part dari RequestBody
        val body = MultipartBody.Part.createFormData("file", reducedFile.name, requestFile)

        // Dapatkan instance ApiService
        val apiService = ApiConfig.retrofit.create(ApiService::class.java)

        try {
            val response = apiService.uploadImage(body)

            if (response.status?.code == 200) {
                val plantName = response.data?.className?.get(0)?.namaTanaman.toString()
                val latinName = response.data?.className?.get(0)?.namaLatin.toString()
                val description = response.data?.className?.get(0)?.deskripsi.toString()
                val uses = response.data?.className?.get(0)?.kegunaan.toString()
                val funFact = response.data?.className?.get(0)?.funfact.toString()
                val rootType = response.data?.className?.get(0)?.tipeAkar.toString()
                val seedType = response.data?.className?.get(0)?.tipeBiji.toString()
                val leafType = response.data?.className?.get(0)?.tipeDaun.toString()
                val stemType = response.data?.className?.get(0)?.tipeBatang.toString()

                _uploadResult.value = UploadResult.Success(
                    plantName,
                    latinName,
                    description,
                    uses,
                    funFact,
                    rootType,
                    seedType,
                    leafType,
                    stemType
                )

            } else {
                // Tampilkan pesan kesalahan ke pengguna
                _uploadResult.value =
                    UploadResult.Error("HTTP Error: ${response.status?.message}")
            }
        } catch (e: Exception) {
        }
        _isUploading.value = false
    }

    suspend fun getQuiz() {
        _isLoading.value = true

        // Dapatkan instance ApiService
        val apiService = ApiConfig.retrofit.create(ApiService::class.java)

        try {
            // Panggil fungsi getQuiz
            val response = apiService.getQuiz()

            if (response.status?.code == 200) {
                _quizResponse.value = response
            } else {
                _quizResponse.value = QuizResponse(status = QuizResponse.Status(code = response.status?.code, message = "HTTP Error: ${response.status?.message}"))
            }
        } catch (e: Exception) {
        }
        _isLoading.value = false
    }
}


package com.example.explora.ui.home.viewmodel

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.explora.data.repository.HomeRepository
import com.example.explora.ui.detail.DetailPlantActivity
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

sealed class UploadResult {
    data class Success(val name: String, val imageUrl: String) : UploadResult()
    data class Error(val errorMessage: String) : UploadResult()
}

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _uploadResult = MutableLiveData<UploadResult>()
    val uploadResult: LiveData<UploadResult> get() = _uploadResult

    fun uploadImage(filePart: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                val response = repository.uploadImage(filePart)
                if (response.status?.code == 200) {
                    val name = response.data?.information?.name.toString()
                    val imageUrl = response.data?.information?.image.toString()

                    _uploadResult.value = UploadResult.Success(name, imageUrl)
                } else {
                    _uploadResult.value =
                        UploadResult.Error("HTTP Error: ${response.status?.message}")
                }
            } catch (e: Exception) {
                _uploadResult.value = UploadResult.Error("Error: ${e.message}")
            }
        }
    }
}


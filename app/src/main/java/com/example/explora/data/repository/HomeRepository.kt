package com.example.explora.data.repository

import com.example.explora.data.models.dummydata.ScanUploadResponse
import com.example.explora.data.network.ApiConfig
import com.example.explora.data.network.ApiService
import okhttp3.MultipartBody

class HomeRepository {

    private val apiService: ApiService = ApiConfig.retrofit.create(ApiService::class.java)

    suspend fun uploadImage(filePart: MultipartBody.Part): ScanUploadResponse {
        return apiService.uploadImage(filePart)
    }
}


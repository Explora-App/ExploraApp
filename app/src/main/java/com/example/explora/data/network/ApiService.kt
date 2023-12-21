package com.example.explora.data.network

import com.example.explora.data.model.QuizResponse
import com.example.explora.data.model.ScanResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): ScanResponse

    @GET("database/quiz")
    suspend fun getQuiz(): QuizResponse
}

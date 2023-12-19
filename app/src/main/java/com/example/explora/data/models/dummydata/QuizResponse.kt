package com.example.explora.data.models.dummydata

import com.google.gson.annotations.SerializedName

data class QuizResponse(

	@field:SerializedName("QuizResponse")
	val quizResponse: List<QuizResponseItem?>? = null
)

data class QuizResponseItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("pilihan_benar")
	val pilihanBenar: String? = null,

	@field:SerializedName("pertanyaan")
	val pertanyaan: String? = null
)

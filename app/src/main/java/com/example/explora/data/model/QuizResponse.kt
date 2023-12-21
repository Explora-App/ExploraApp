package com.example.explora.data.model

import com.google.gson.annotations.SerializedName

data class QuizResponse(
	@field:SerializedName("data")
	val data: List<DataItem?>? = null,
	@field:SerializedName("status")
	val status: Status? = null
) {
	data class Status(
		@field:SerializedName("code")
		val code: Int? = null,
		@field:SerializedName("message")
		val message: String? = null
	)
}

data class DataItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("jawaban_benar")
	val jawabanBenar: String? = null,

	@field:SerializedName("pertanyaan")
	val pertanyaan: String? = null
)

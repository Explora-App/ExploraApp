package com.example.explora.data.model

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: Status? = null
)

data class Status(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ClassNameItem(

	@field:SerializedName("tipe_daun")
	val tipeDaun: String? = null,

	@field:SerializedName("kegunaan")
	val kegunaan: String? = null,

	@field:SerializedName("tipe_biji")
	val tipeBiji: String? = null,

	@field:SerializedName("tipe_batang")
	val tipeBatang: String? = null,

	@field:SerializedName("nama_tanaman")
	val namaTanaman: String? = null,

	@field:SerializedName("nama_latin")
	val namaLatin: String? = null,

	@field:SerializedName("tipe_akar")
	val tipeAkar: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("funfact")
	val funfact: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("label")
	val label: String? = null
)

data class Data(

	@field:SerializedName("class_name")
	val className: List<ClassNameItem?>? = null
)

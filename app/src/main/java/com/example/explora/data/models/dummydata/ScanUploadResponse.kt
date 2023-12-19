package com.example.explora.data.models.dummydata

import com.google.gson.annotations.SerializedName

data class ScanUploadResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: Status? = null
)

data class Contact(

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("contact_link")
	val contactLink: String? = null
)

data class Information(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Data(

	@field:SerializedName("confidence_score")
	val confidenceScore: Any? = null,

	@field:SerializedName("contact")
	val contact: Contact? = null,

	@field:SerializedName("information")
	val information: Information? = null,

	@field:SerializedName("class_name")
	val className: String? = null
)

data class Status(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)

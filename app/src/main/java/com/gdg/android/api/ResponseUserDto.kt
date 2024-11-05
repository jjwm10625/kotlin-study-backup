package com.gdg.android.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserDto (
    @SerialName("page") val page: Int,
    @SerialName("per_page") val perPage: Int,
    @SerialName("total") val total: Int,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("data") val data: List<User>, // User 객체를 리스트로 받기
    @SerialName("support") val support: Support // Support 객체로 받기
)

@Serializable
data class User ( // User 객체
    @SerialName("id") val id: Int,
    @SerialName("email") val email: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("avatar") val avatar: String
)

@Serializable
data class Support ( // Support 객체
    @SerialName("url") val url: String,
    @SerialName("text") val text: String
)
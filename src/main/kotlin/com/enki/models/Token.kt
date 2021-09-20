package com.enki.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class TokenInfo(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val refreshToken: String,
    @SerialName("expires_in") val expiresIn: Int,
    @SerialName("scope") val scope: String,
    @SerialName("created_at") val createdAt: Int,
)

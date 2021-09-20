package com.enki.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UserInfo(
    @SerialName("login") val login: String,
    @SerialName("displayname") val displayName: String,
    @SerialName("staff?") val isStaff: Boolean,
    @SerialName("image_url") val profileImage: String,
    @SerialName("correction_point") val correctionPoints: Int,
    @SerialName("wallet") val wallet: Int,
)

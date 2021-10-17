package com.abhas.retro_compose.model

import com.google.gson.annotations.SerializedName

data class MemesResponse (
    @SerializedName("success") val success : Boolean,
    @SerializedName("data") val data : Data
)
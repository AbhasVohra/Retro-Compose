package com.abhas.retro_compose.model

import com.google.gson.annotations.SerializedName

data class Memes (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("url") val url : String,
    @SerializedName("width") val width : Int,
    @SerializedName("height") val height : Int,
    @SerializedName("box_count") val box_count : Int
)
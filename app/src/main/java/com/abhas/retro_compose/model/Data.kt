package com.abhas.retro_compose.model

import com.google.gson.annotations.SerializedName

data class Data (
    @SerializedName("memes") val memes : List<Memes>
)
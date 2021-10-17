package com.abhas.retro_compose.repository

import com.abhas.retro_compose.model.MemesResponse
import retrofit2.Call
import retrofit2.http.GET

interface DataService {

    @GET("get_memes")
    fun getMemes(): Call<MemesResponse>
}
package com.abhas.retro_compose.repository

import com.abhas.retro_compose.model.MemesResponse
import com.abhas.retro_compose.utils.AppConstants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    companion object {
        private lateinit var retrofit: Retrofit

        fun getInstance(): Retrofit {
            if (!::retrofit.isInitialized) {
                retrofit  = Retrofit
                    .Builder()
                    .baseUrl(AppConstants.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
        fun getMemes(): Call<MemesResponse> {
            val retrofit = getInstance()
            val dataService = retrofit.create(DataService::class.java)
            return dataService.getMemes()
        }
    }
}
package com.example.uas_pppb_p5_guide.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppClient {
    private const val BASE_URL = "https://ppbo-api.vercel.app/"

    private var retrofit: Retrofit? = null

    fun getInstance(): ApiService {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL) // Base URL only
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(ApiService::class.java)
    }
}
package com.example.uas_pppb_p5_guide.network

import com.example.uas_pppb_p5_guide.model.Persona
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("60GpU/persona/") // Endpoint API yang sesuai
    fun getPersonaList(): Call<List<Persona>>
}
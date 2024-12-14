package com.example.uas_pppb_p5_guide.network

import com.example.uas_pppb_p5_guide.model.Persona
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("60GpU/persona/")
    fun getPersonaList(): Call<List<Persona>>

    @POST("60GpU/persona/")
    fun addPersona(@Body persona: Persona): Call<Persona>

    @GET("60GpU/persona/{id}")
    fun getPersonaDetails(@Path("id") id: String): Call<Persona>

    @POST("60GpU/persona/{id}")
    fun updatePersona(@Path("id") id: String, @Body persona: Persona): Call<Persona>

    @DELETE("60GpU/persona/{id}")
    fun deletePersona(@Path("id") id: String): Call<Void> // Metode untuk menghapus persona
}
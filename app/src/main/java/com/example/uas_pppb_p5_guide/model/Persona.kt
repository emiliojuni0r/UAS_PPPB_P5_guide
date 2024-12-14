package com.example.uas_pppb_p5_guide.model

import com.google.gson.annotations.SerializedName

data class Persona (
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("arcana")
    val arcana: String,
    @SerializedName("level")
    val level: String,
    @SerializedName("stats")
    val stats: String,
    @SerializedName("skills")
    val skills: String,
    @SerializedName("fusionRecipe")
    val fusionRecipe: String,
    @SerializedName("elements")
    val elements: String,
)
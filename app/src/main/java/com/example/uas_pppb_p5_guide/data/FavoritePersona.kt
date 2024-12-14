package com.example.uas_pppb_p5_guide.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_persona")
data class FavoritePersona(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "arcana")
    val arcana: String,
    @ColumnInfo(name = "level")
    val level: String,
    @ColumnInfo(name = "stats")
    val stats: String,
    @ColumnInfo(name = "skills")
    val skills: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "fusionRecipe")
    val fusionRecipe: String,
    @ColumnInfo(name = "elements")
    val elements: String,
)

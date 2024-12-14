package com.example.uas_pppb_p5_guide.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritePersonaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoritePersona: FavoritePersona)

    @Query("DELETE FROM favorite_persona WHERE name = :name")
    suspend fun deleteByName(name: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_persona WHERE name = :name)")
    suspend fun isFavorite(name: String): Boolean

    @Query("SELECT * FROM favorite_persona")
    suspend fun getAllFavorites(): List<FavoritePersona>
}
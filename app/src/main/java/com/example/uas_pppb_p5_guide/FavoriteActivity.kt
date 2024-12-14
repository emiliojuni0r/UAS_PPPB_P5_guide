package com.example.uas_pppb_p5_guide

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uas_pppb_p5_guide.adapter.FavoritePersonaAdapter
import com.example.uas_pppb_p5_guide.data.AppDatabase
import com.example.uas_pppb_p5_guide.data.FavoritePersona
import com.example.uas_pppb_p5_guide.databinding.ActivityFavoriteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var favoriteDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteDatabase = AppDatabase.getDatabase(applicationContext)

        binding.rcFavPersona.layoutManager = GridLayoutManager(this, 2)

        fetchFavoritePersonas()

        with(binding){
            bottomNavPersona.setOnClickListener{
                val intent = Intent(this@FavoriteActivity,MainActivity::class.java)
                startActivity(intent)
            }
            bottomNavFavorite.setOnClickListener{
                val intent = Intent(this@FavoriteActivity,FavoriteActivity::class.java)
                startActivity(intent)
            }
            bottomNavProfile.setOnClickListener{
                val intent = Intent(this@FavoriteActivity,ProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun fetchFavoritePersonas() {
        CoroutineScope(Dispatchers.IO).launch {
            val favoritePersonas = favoriteDatabase.favoritePersonaDao().getAllFavorites()
            withContext(Dispatchers.Main) {
                binding.rcFavPersona.adapter = FavoritePersonaAdapter(favoritePersonas) { favoritePersona ->
                    // Navigasi ke DetailActivity saat item diklik
                    val intent = Intent(this@FavoriteActivity, DetailActivity::class.java).apply {
                        putExtra("PERSONA_NAME", favoritePersona.name)
                        putExtra("PERSONA_IMAGE", favoritePersona.image)
                        putExtra("PERSONA_ARCANA", favoritePersona.arcana)
                        putExtra("PERSONA_LEVEL", favoritePersona.level)
                        putExtra("PERSONA_STATS", favoritePersona.stats)
                        putExtra("PERSONA_SKILLS", favoritePersona.skills)
                        putExtra("PERSONA_FUSION_RECIPE", favoritePersona.fusionRecipe)
                        putExtra("PERSONA_ELEMENTS", favoritePersona.elements)
                    }
                    startActivity(intent)
                }
            }
        }
    }
}
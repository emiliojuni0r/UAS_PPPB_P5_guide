package com.example.uas_pppb_p5_guide

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.uas_pppb_p5_guide.data.AppDatabase
import com.example.uas_pppb_p5_guide.data.FavoritePersona
import com.example.uas_pppb_p5_guide.databinding.ActivityDetailBinding
import com.example.uas_pppb_p5_guide.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private lateinit var favoriteDatabase: AppDatabase
    private var isFavorite: Boolean = false
//    private lateinit var personaId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteDatabase = AppDatabase.getDatabase(applicationContext)

        val name = intent.getStringExtra("PERSONA_NAME")
        val image = intent.getStringExtra("PERSONA_IMAGE")
        val arcana = intent.getStringExtra("PERSONA_ARCANA")
        val level = intent.getStringExtra("PERSONA_LEVEL")
        val stats = intent.getStringExtra("PERSONA_STATS")
        val skills = intent.getStringExtra("PERSONA_SKILLS")
        val fusion = intent.getStringExtra("PERSONA_FUSION_RECIPE")
        val elements = intent.getStringExtra("PERSONA_ELEMENTS")



        with(binding){
            personaDetailName.text = name
            Glide.with(binding.root.context).load(image).into(personaDetailImg)
            personaDetailArcana.text = arcana
            personaDetailStats.text = stats
            personaDetailLevel.text = level
            personaDetailSkills.text = skills
            personaDetailFusion.text = fusion
            personaDetailElements.text = elements

            btnBack.setOnClickListener{
                finish()
            }

            bottomNavPersona.setOnClickListener{
                val intent = Intent(this@DetailActivity,MainActivity::class.java)
                startActivity(intent)
            }
            bottomNavFavorite.setOnClickListener{
                val intent = Intent(this@DetailActivity,FavoriteActivity::class.java)
                startActivity(intent)
            }
            bottomNavProfile.setOnClickListener{
                val intent = Intent(this@DetailActivity,ProfileActivity::class.java)
                startActivity(intent)
            }


            btnAddToFavorites.setOnClickListener {
                if (isFavorite) {
                    removeFromFavorites(name)
                } else {
                    addToFavorites(name, arcana, level, stats, skills, image, fusion, elements)
                }
                isFavorite = !isFavorite // Toggle status
            }
        }

        checkIfFavorite(name)
    }

    private fun addToFavorites(name: String?, arcana: String?, level: String?, stats: String?, skills: String?, image: String?, fusion: String?, elements: String?) {
        val favoritePersona = FavoritePersona(
            name = name ?: "",
            arcana = arcana ?: "",
            level = level ?: "",
            stats = stats ?: "",
            skills = skills ?: "",
            image = image ?: "",
            fusionRecipe = fusion ?: "",
            elements = elements ?: ""
        )
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDatabase.favoritePersonaDao().insert(favoritePersona)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@DetailActivity, "$name added to favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeFromFavorites(name: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDatabase.favoritePersonaDao().deleteByName(name ?: "")
            withContext(Dispatchers.Main) {
                Toast.makeText(this@DetailActivity, "$name removed from favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkIfFavorite(name: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            isFavorite = favoriteDatabase.favoritePersonaDao().isFavorite(name ?: "") // Check if already favorite
            withContext(Dispatchers.Main) {
                binding.btnAddToFavorites.text = if (isFavorite) "Remove from Favorites" else "Add to Favorites"
            }
        }
    }
}
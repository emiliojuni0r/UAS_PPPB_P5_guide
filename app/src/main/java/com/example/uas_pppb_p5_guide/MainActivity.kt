package com.example.uas_pppb_p5_guide

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uas_pppb_p5_guide.adapter.PersonaAdapter
import com.example.uas_pppb_p5_guide.databinding.ActivityLoginBinding
import com.example.uas_pppb_p5_guide.databinding.ActivityMainBinding
import com.example.uas_pppb_p5_guide.model.Persona
import com.example.uas_pppb_p5_guide.network.AppClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rcPersona.layoutManager = GridLayoutManager(this,2)

        fetchPersonaList()

        with(binding){
            bottomNavPersona.setOnClickListener{
                val intent = Intent(this@MainActivity,MainActivity::class.java)
                startActivity(intent)
            }
            bottomNavFavorite.setOnClickListener{
                val intent = Intent(this@MainActivity,FavoriteActivity::class.java)
                startActivity(intent)
            }
            bottomNavProfile.setOnClickListener{
                val intent = Intent(this@MainActivity,ProfileActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun fetchPersonaList() {
        AppClient.getInstance().getPersonaList().enqueue(object : Callback<List<Persona>> {
            override fun onResponse(call: Call<List<Persona>>, response: Response<List<Persona>>) {
                if (response.isSuccessful) {
                    // Ambil daftar Persona dari response
                    val personaList = response.body() ?: listOf()

                    // Set adapter untuk RecyclerView
                    binding.rcPersona.adapter = PersonaAdapter(personaList) { persona ->
                        // Siapkan intent untuk mengirim data ke DetailActivity
                        val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                            putExtra("PERSONA_ID", persona.id)
                            putExtra("PERSONA_ARCANA", persona.arcana)
                            putExtra("PERSONA_NAME", persona.name)
                            putExtra("PERSONA_IMAGE", persona.image)
                            putExtra("PERSONA_LEVEL", persona.level)
                            putExtra("PERSONA_STATS", persona.stats)
                            putExtra("PERSONA_SKILLS", persona.skills)
                            putExtra("PERSONA_FUSION_RECIPE", persona.fusionRecipe)
                            putExtra("PERSONA_ELEMENTS", persona.elements)
                            // Tambahkan data lain yang ingin Anda kirim
                        }
                        startActivity(intent)
                    }
                } else {
                    Log.e("MainActivity", "Response not successful: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Persona>>, t: Throwable) {
                // Tangani kesalahan
                Log.e("MainActivity", "Error fetching data: ${t.message}")
            }
        })
    }
}
package com.example.uas_pppb_p5_guide.Admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uas_pppb_p5_guide.LoginActivity
import com.example.uas_pppb_p5_guide.R
import com.example.uas_pppb_p5_guide.SharedPreferencesManager
import com.example.uas_pppb_p5_guide.adapter.AdminPersonaAdapter
import com.example.uas_pppb_p5_guide.databinding.ActivityAdminMainBinding
import com.example.uas_pppb_p5_guide.databinding.ActivityMainBinding
import com.example.uas_pppb_p5_guide.model.Persona
import com.example.uas_pppb_p5_guide.network.AppClient
import com.example.uas_pppb_p5_guide.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding
    private lateinit var apiService: ApiService
    private lateinit var personaList: List<Persona>
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = AppClient.getInstance()

        sharedPreferencesManager = SharedPreferencesManager(this)


        binding.btnAddPersona.setOnClickListener {
            val intent = Intent(this, AdminCreateActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener{
            sharedPreferencesManager.logout() // Panggil fungsi logout
            Toast.makeText(this@AdminMainActivity, "Logged Out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        fetchPersonaList()
    }

    private fun fetchPersonaList() {
        apiService.getPersonaList().enqueue(object : Callback<List<Persona>> {
            override fun onResponse(call: Call<List<Persona>>, response: Response<List<Persona>>) {
                if (response.isSuccessful) {
                    personaList = response.body() ?: emptyList()
                    setupRecyclerView(personaList)
                } else {
                    Toast.makeText(this@AdminMainActivity, "Failed to load persona list", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Persona>>, t: Throwable) {
                Toast.makeText(this@AdminMainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        fetchPersonaList()
    }

    private fun setupRecyclerView(personas: List<Persona>) {
        binding.rcAdminPersona.layoutManager = LinearLayoutManager(this)
        val adapter = AdminPersonaAdapter(personas, { persona ->
            // Handle view persona
            val intent = Intent(this, AdminViewActivity::class.java).apply {
                putExtra("PERSONA_ID", persona.id)
                putExtra("PERSONA_ARCANA", persona.arcana)
                putExtra("PERSONA_NAME", persona.name)
                putExtra("PERSONA_IMAGE", persona.image)
                putExtra("PERSONA_LEVEL", persona.level)
                putExtra("PERSONA_STATS", persona.stats)
                putExtra("PERSONA_SKILLS", persona.skills)
                putExtra("PERSONA_FUSION_RECIPE", persona.fusionRecipe)
                putExtra("PERSONA_ELEMENTS", persona.elements)
            }
            startActivity(intent)
        }, { persona ->
            // Handle edit persona
            val intent = Intent(this, AdminEditActivity::class.java).apply {
                putExtra("PERSONA_ID", persona.id)
            }
            startActivity(intent)
        },
            { id ->
            // Handle delete persona
            deletePersona(id)
        }
        )
        binding.rcAdminPersona.adapter = adapter
    }

    private fun deletePersona(id: String) {
        apiService.deletePersona(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AdminMainActivity, "Persona deleted successfully", Toast.LENGTH_SHORT).show()
                    fetchPersonaList() // Refresh daftar persona
                } else {
                    Toast.makeText(this@AdminMainActivity, "Failed to delete persona", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@AdminMainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
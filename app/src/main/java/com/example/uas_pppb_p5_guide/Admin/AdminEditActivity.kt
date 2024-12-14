package com.example.uas_pppb_p5_guide.Admin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uas_pppb_p5_guide.R
import com.example.uas_pppb_p5_guide.databinding.ActivityAdminEditBinding
import com.example.uas_pppb_p5_guide.model.Persona
import com.example.uas_pppb_p5_guide.network.AppClient
import com.example.uas_pppb_p5_guide.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminEditBinding
    private lateinit var apiService: ApiService
    private lateinit var personaId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = AppClient.getInstance()

        // Get the persona ID from the intent
        personaId = intent.getStringExtra("PERSONA_ID") ?: return

        Toast.makeText(this@AdminEditActivity, "hol up, let us load the data...", Toast.LENGTH_LONG).show()

        // Fetch the persona details
        fetchPersonaDetails()
        Toast.makeText(this@AdminEditActivity, "Persona Data loaded", Toast.LENGTH_SHORT).show()

        binding.btnSaveEditPersona.setOnClickListener {
            updatePersona()
            Toast.makeText(this@AdminEditActivity, "loading...", Toast.LENGTH_SHORT).show()
        }

        binding.btnBack.setOnClickListener {
            finish() // Go back to the previous activity
        }
    }

    private fun fetchPersonaDetails() {
        apiService.getPersonaDetails(personaId).enqueue(object : Callback<Persona> {
            override fun onResponse(call: Call<Persona>, response: Response<Persona>) {
                if (response.isSuccessful) {
                    val persona = response.body()
                    persona?.let {
                        // Populate the fields with the current persona data
                        binding.editPersonaName.setText(it.name)
                        binding.editPersonaArcana.setText(it.arcana)
                        binding.editPersonaImage.setText(it.image)
                        binding.editPersonaLevel.setText(it.level)
                        binding.editPersonaStats.setText(it.stats)
                        binding.editPersonaSkills.setText(it.skills)
                        binding.editPersonaFusion.setText(it.fusionRecipe)
                        binding.editPersonaElements.setText(it.elements)
                    }
                } else {
                    Toast.makeText(this@AdminEditActivity, "Failed to load persona details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Persona>, t: Throwable) {
                Toast.makeText(this@AdminEditActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updatePersona() {
        val updatedPersona = Persona(
            id = personaId, // Use the existing ID
            name = binding.editPersonaName.text.toString(),
            image = binding.editPersonaImage.text.toString(),
            arcana = binding.editPersonaArcana.text.toString(),
            level = binding.editPersonaLevel.text.toString(),
            stats = binding.editPersonaStats.text.toString(),
            skills = binding.editPersonaSkills.text.toString(),
            fusionRecipe = binding.editPersonaFusion.text.toString(),
            elements = binding.editPersonaElements.text.toString()
        )

        apiService.updatePersona(personaId, updatedPersona).enqueue(object : Callback<Persona> {
            override fun onResponse(call: Call<Persona>, response: Response<Persona>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AdminEditActivity, "Persona updated successfully", Toast.LENGTH_SHORT).show()
                    finish() // Go back to the previous activity
                } else {
                    Toast.makeText(this@AdminEditActivity, "Failed to update persona", Toast.LENGTH_SHORT).show()
                    val errorBody = response.errorBody()?.string()
                    Log.e("AdminEditActivity", "Error: ${response.code()} - $errorBody")
                    Toast.makeText(this@AdminEditActivity, "Failed to update persona: $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Persona>, t: Throwable) {
                Toast.makeText(this@AdminEditActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
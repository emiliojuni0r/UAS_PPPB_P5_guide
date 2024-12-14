package com.example.uas_pppb_p5_guide.Admin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uas_pppb_p5_guide.R
import com.example.uas_pppb_p5_guide.databinding.ActivityAdminCreateBinding
import com.example.uas_pppb_p5_guide.model.Persona
import com.example.uas_pppb_p5_guide.network.ApiService
import com.example.uas_pppb_p5_guide.network.AppClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminCreateActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var binding: ActivityAdminCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = AppClient.getInstance()

        binding.btnSaveCreatePersona.setOnClickListener {
            val name = binding.inputPersonaName.text.toString()
            val arcana = binding.inputPersonaArcana.text.toString()
            val image = binding.inputPersonaImage.text.toString()
            val level = binding.inputPersonaLevel.text.toString()
            val stats = binding.inputPersonaStats.text.toString()
            val skills = binding.inputPersonaSkills.text.toString()
            val fusion = binding.inputPersonaFusion.text.toString()
            val elements = binding.inputPersonaElements.text.toString()

            val newPersona = Persona(
                name = name,
                image = image,
                arcana = arcana,
                level = level,
                stats = stats,
                skills = skills,
                fusionRecipe = fusion,
                elements = elements
            )

            addPersona(newPersona)
        }

        binding.btnBack.setOnClickListener {
            finish() // Go back to the previous activity
        }
    }

    private fun addPersona(persona: Persona) {
        apiService.addPersona(persona).enqueue(object : Callback<Persona> {
            override fun onResponse(call: Call<Persona>, response: Response<Persona>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AdminCreateActivity, "Persona added successfully", Toast.LENGTH_SHORT).show()
                    finish() // Go back to the admin main activity
                } else {
                    // Log the error response
                    val errorBody = response.errorBody()?.string()
                    Log.e("AdminCreateActivity", "Error: $errorBody")
                    Toast.makeText(this@AdminCreateActivity, "Failed to add persona: $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Persona>, t: Throwable) {
                Toast.makeText(this@AdminCreateActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
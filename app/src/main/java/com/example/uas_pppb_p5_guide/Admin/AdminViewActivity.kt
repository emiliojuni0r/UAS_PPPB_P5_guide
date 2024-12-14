package com.example.uas_pppb_p5_guide.Admin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.uas_pppb_p5_guide.R
import com.example.uas_pppb_p5_guide.databinding.ActivityAdminMainBinding
import com.example.uas_pppb_p5_guide.databinding.ActivityAdminViewBinding

class AdminViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            btnBackToAdminList.setOnClickListener{
                finish()
            }
        }
    }
}
package com.example.uas_pppb_p5_guide

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uas_pppb_p5_guide.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager(this)

        val (username, _) = sharedPreferencesManager.checkLoginStatus()
        binding.usernameValue.text = username ?: "Guest"

        binding.btnLogout.setOnClickListener {
            sharedPreferencesManager.logout() // Panggil fungsi logout
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.bottomNavPersona.setOnClickListener{
            val intent = Intent(this@ProfileActivity,MainActivity::class.java)
            startActivity(intent)
        }
        binding.bottomNavFavorite.setOnClickListener{
            val intent = Intent(this@ProfileActivity,FavoriteActivity::class.java)
            startActivity(intent)
        }
        binding.bottomNavProfile.setOnClickListener{
            val intent = Intent(this@ProfileActivity,ProfileActivity::class.java)
            startActivity(intent)
        }

    }
}
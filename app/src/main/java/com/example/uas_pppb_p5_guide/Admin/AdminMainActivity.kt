package com.example.uas_pppb_p5_guide.Admin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uas_pppb_p5_guide.LoginActivity
import com.example.uas_pppb_p5_guide.R
import com.example.uas_pppb_p5_guide.SharedPreferencesManager
import com.example.uas_pppb_p5_guide.databinding.ActivityAdminMainBinding
import com.example.uas_pppb_p5_guide.databinding.ActivityLoginBinding

class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager(this)


        with(binding) {
            // button logout
            btnLogout.setOnClickListener{
                sharedPreferencesManager.logout()
                startActivity(Intent(this@AdminMainActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}
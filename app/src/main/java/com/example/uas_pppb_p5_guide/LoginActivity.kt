package com.example.uas_pppb_p5_guide

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uas_pppb_p5_guide.Admin.AdminMainActivity
import com.example.uas_pppb_p5_guide.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager(this)

        checkLoginStatus()

        with(binding){
            // login validation
            btnLogin.setOnClickListener{
                // ambil data dari edit text nya
                val inputUsername = binding.inputUsername.text.toString()
                val inputPassword = binding.inputPassword.text.toString()
                if(inputUsername == "admin" && inputPassword == "123"){
                    sharedPreferencesManager.saveLoginStatus(inputUsername, true)
                    Toast.makeText(this@LoginActivity, "Logged in as Admin", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, AdminMainActivity::class.java))
                    finish()
                } else if (inputUsername == "" && inputPassword != ""){
                    errorText.text = "username can't empty"
                } else if (inputUsername != "" && inputPassword == ""){
                    errorText.text = "password can't empty"
                } else if (inputUsername == "" && inputPassword == ""){
                    errorText.text = "username n password can't empty"
                }
                else {
                    sharedPreferencesManager.saveLoginStatus(inputUsername,false)
                    Toast.makeText(this@LoginActivity, "Logged in as user", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
    }


    private fun checkLoginStatus() {
        val (username, isAdmin) = sharedPreferencesManager.checkLoginStatus()

        if (isAdmin == true) {
            // Jika admin, arahkan ke AdminMainActivity
            startActivity(Intent(this, AdminMainActivity::class.java))
            finish() // Tutup LoginActivity
        } else if (username != null) {
            // Jika pengguna biasa, arahkan ke MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Tutup LoginActivity
        }
    }
}
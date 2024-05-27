package com.example.apporientacinvocacional

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apporientacinvocacional.databinding.ActivityOpcionesLoginBinding

class OpcionesLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpcionesLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpcionesLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.opcionUser.setOnClickListener {
            startActivity(Intent(applicationContext, LoginUserActivity::class.java))
        }

        binding.opcionAdmin.setOnClickListener {
            startActivity(Intent(applicationContext, LoginUserActivity::class.java))
        }
    }
}
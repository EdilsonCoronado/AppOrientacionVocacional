package com.example.apporientacinvocacional

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apporientacinvocacional.databinding.ActivityTest3Binding

class Test3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityTest3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTest3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.t3BotonEnviar.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finishAffinity()
        }
    }
}
package com.example.apporientacinvocacional

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apporientacinvocacional.databinding.ActivityTest6Binding
import com.example.apporientacinvocacional.databinding.ActivityTest7Binding

class Test7Activity : AppCompatActivity() {

    private lateinit var binding: ActivityTest7Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTest7Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardar.setOnClickListener {
            startActivity(Intent(applicationContext, MainAdminActivity::class.java))
            finishAffinity()
        }
    }
}
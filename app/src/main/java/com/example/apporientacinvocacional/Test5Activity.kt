package com.example.apporientacinvocacional

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apporientacinvocacional.Fragmentos.FragmentFeedback
import com.example.apporientacinvocacional.databinding.ActivityTest5Binding

class Test5Activity : AppCompatActivity() {

    private lateinit var binding: ActivityTest5Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTest5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.t5BotonEnviar.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finishAffinity()
        }
    }
}
package com.example.apporientacinvocacional

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apporientacinvocacional.Fragmentos.FragmentFeedback
import com.example.apporientacinvocacional.databinding.ActivityTest6Binding

class Test6Activity : AppCompatActivity() {

    private lateinit var binding: ActivityTest6Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTest6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.t6BotonEnviar.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finishAffinity()
        }
    }
}
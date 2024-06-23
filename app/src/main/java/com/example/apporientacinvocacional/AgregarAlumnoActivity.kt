package com.example.apporientacinvocacional

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apporientacinvocacional.Fragmentos.FragmentAdd
import com.example.apporientacinvocacional.databinding.ActivityAgregarAlumnoBinding
import com.google.firebase.firestore.FirebaseFirestore

class AgregarAlumnoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarAlumnoBinding
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAgregarAlumnoBinding.inflate(layoutInflater)
        firestore = FirebaseFirestore.getInstance()
        setContentView(binding.root)
        binding.buttonGuardarNotas.setOnClickListener {
            startActivity(Intent(applicationContext, MainAdminActivity::class.java))
            finishAffinity()
        }
    }
}
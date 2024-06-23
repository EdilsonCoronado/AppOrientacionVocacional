package com.example.apporientacinvocacional

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apporientacinvocacional.databinding.ActivityLoginAdminBinding
import com.example.apporientacinvocacional.databinding.ActivityLoginUserBinding
import com.google.firebase.auth.FirebaseAuth

class LoginAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginAdminBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnIngresar.setOnClickListener {
            validarInformacion()
        }

        binding.tvRegistrarme.setOnClickListener {
            startActivity(Intent(applicationContext, RegistroAdminActivity::class.java))
        }
    }

    private var email = ""
    private var password = ""
    private fun validarInformacion() {
        email = binding.etEmail.text.toString()
        password = binding.etPassword.text.toString()
        if (email.isEmpty()) {
            binding.etEmail.error = "Ingrese email"
            binding.etEmail.requestFocus()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Email no válido"
            binding.etEmail.requestFocus()
        }
        else if (password.isEmpty()) {
            binding.etPassword.error = "Ingrese contraseña"
            binding.etPassword.requestFocus()
        }
        else {
            logearAdmin()
        }
    }

    private fun logearAdmin() {
        progressDialog.setMessage("Ingresando")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainAdminActivity::class.java))
                finishAffinity()

            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se realizó el logeo debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
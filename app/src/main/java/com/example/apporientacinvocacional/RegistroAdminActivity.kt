package com.example.apporientacinvocacional

import android.R
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apporientacinvocacional.databinding.ActivityRegistroAdminBinding
import com.example.apporientacinvocacional.databinding.ActivityRegistroUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroAdminBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.btnRegistrar.setOnClickListener {
            validarInfomacion()
        }

    }

    private var email = ""
    private var password = ""
    private var r_password = ""
    private var dni = ""
    private var ap_paterno = ""
    private var ap_materno = ""
    private var nombres = ""
    private var telefono = ""

    private fun validarInfomacion() {
        nombres = binding.etNombres.text.toString().trim()
        email = binding.etEmail .text.toString().trim()
        password = binding.etPassword.text.toString().trim()
        r_password = binding.etRPassword.text.toString().trim()
        dni = binding.etDNI.text.toString().trim()
        ap_paterno = binding.etApellidoPat.text.toString().trim()
        ap_materno = binding.etApelidoMat.text.toString().trim()
        telefono = binding.etTelefono.text.toString().trim()

        if (nombres.isEmpty()) {
            binding.etNombres.error = "Ingrese nombre"
            binding.etNombres.requestFocus()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Correo inválido"
            binding.etEmail.requestFocus()
        }
        else if (email.isEmpty()) {
            binding.etEmail.error = "Ingrese correo"
            binding.etEmail.requestFocus()
        }
        else if (password.isEmpty()) {
            binding.etPassword.error = "Ingrese contraseña"
            binding.etPassword.requestFocus()
        }
        else if (r_password.isEmpty()) {
            binding.etRPassword.error = "Ingrese contraseña"
            binding.etRPassword.requestFocus()
        }
        else if (password != r_password) {
            binding.etRPassword.error = "No coinciden las contraseñas"
            binding.etRPassword.requestFocus()
        }
        else if (dni.isEmpty()) {
            binding.etPassword.error = "Ingrese DNI"
            binding.etPassword.requestFocus()
        }
        else if (ap_paterno.isEmpty()) {
            binding.etPassword.error = "Ingrese su apellido"
            binding.etPassword.requestFocus()
        }
        else if (ap_materno.isEmpty()) {
            binding.etPassword.error = "Ingrese su apellido"
            binding.etPassword.requestFocus()
        }
        else if (telefono.isEmpty()) {
            binding.etPassword.error = "Ingrese teléfono"
            binding.etPassword.requestFocus()
        }
        else {
            registrarAdmin()
        }
    }

    private fun registrarAdmin() {
        progressDialog.setMessage("Creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                actualizarInformacion()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Falló la creación de la cuenta debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun actualizarInformacion() {
        progressDialog.setMessage("Guardando información")

        val nombresA = nombres
        val emailA = firebaseAuth.currentUser!!.email
        val dniA = dni
        val apPaternoA = ap_paterno
        val apMaternoA = ap_materno
        val telefonoA = telefono

        val datosAdmin = hashMapOf(
            "uid" to dniA,
            "nombres" to nombresA,
            "email" to emailA,
            "apPaterno" to apPaternoA,
            "apMaterno" to apMaternoA,
            "telefono" to telefonoA,
            // Agrega más campos aquí según lo necesites
        )

        firestore.collection("admin")
            .document(dniA!!)
            .set(datosAdmin)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(applicationContext, MainAdminActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Falló la creación de la cuenta debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
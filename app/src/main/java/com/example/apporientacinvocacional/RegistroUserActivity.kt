package com.example.apporientacinvocacional

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apporientacinvocacional.databinding.ActivityRegistroUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.intellij.lang.annotations.Pattern

class RegistroUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroUserBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        val items = arrayOf("Masculino", "Femenino")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        binding.etGenero.adapter = adapter

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
    private var genero = ""
    private var colegio = ""

    private fun validarInfomacion() {
        nombres = binding.etNombres.text.toString().trim()
        email = binding.etEmail .text.toString().trim()
        password = binding.etPassword.text.toString().trim()
        r_password = binding.etRPassword.text.toString().trim()
        genero = binding.etGenero.selectedItem.toString()
        dni = binding.etDNI.text.toString().trim()
        ap_paterno = binding.etApellidoPat.text.toString().trim()
        ap_materno = binding.etApelidoMat.text.toString().trim()
        telefono = binding.etTelefono.text.toString().trim()
        colegio = binding.etColegio.text.toString().trim()

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
        else if (genero.isEmpty()) {
            binding.etPassword.error = "Elija su género"
            binding.etPassword.requestFocus()
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
        else if (colegio.isEmpty()) {
            binding.etPassword.error = "Ingrese colegio"
            binding.etPassword.requestFocus()
        }
        else {
            registrarUser()
        }
    }

    private fun registrarUser() {
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

        val nombresU = nombres
        val emailU = firebaseAuth.currentUser!!.email
        val generoU = genero
        val dniU = dni
        val apPaternoU = ap_paterno
        val apMaternoU = ap_materno
        val telefonoU = telefono
        val colegioU = colegio

        val datosUsuario = hashMapOf(
            "uid" to dniU,
            "nombres" to nombresU,
            "email" to emailU,
            "genero" to generoU,
            "apPaterno" to apPaternoU,
            "apMaterno" to apMaternoU,
            "telefono" to telefonoU,
            "colegio" to colegioU
            // Agrega más campos aquí según lo necesites
        )

        firestore.collection("usuario")
            .document(dniU!!)
            .set(datosUsuario)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(applicationContext, MainActivity::class.java))
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

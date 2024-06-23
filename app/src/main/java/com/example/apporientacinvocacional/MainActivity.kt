package com.example.apporientacinvocacional

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apporientacinvocacional.Fragmentos.FragmentFeedback
import com.example.apporientacinvocacional.Fragmentos.FragmentPerfil
import com.example.apporientacinvocacional.Fragmentos.FragmentPerfilAdmin
import com.example.apporientacinvocacional.Fragmentos.FragmentTests
import com.example.apporientacinvocacional.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        comprobarSesion()


        binding.bottomNV.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.item_perfil->{
                    //Visualizar el fragmento Perfil
                    verFragmentoPerfil()
                    true
                }
                R.id.item_tests->{
                    //Visualizar el fragmento Tests
                    verFragmentoTests()
                    true
                }
                R.id.item_feedback->{
                    //Visualizar el fragmento Feedback
                    verFragmentoFeedback()
                    true
                }
                else->{
                    false
                }
            }
        }
    }

    private fun comprobarSesion() {
        if (firebaseAuth.currentUser == null) {
            startActivity(Intent(applicationContext, OpcionesLoginActivity::class.java))
            finishAffinity()
        } else {
            agregarToken()
            solicitarPermisoNotificaciones()

            val userEmail = firebaseAuth.currentUser?.email ?: return

            // Consultar la colección "usuario"
            firestore.collection("usuario")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (!task.result.isEmpty) {
                            verFragmentoPerfil()
                        } else {
                            // Consultar la colección "admin" si no se encuentra en "usuario"
                            firestore.collection("admin")
                                .whereEqualTo("email", userEmail)
                                .get()
                                .addOnCompleteListener { adminTask ->
                                    if (adminTask.isSuccessful) {
                                        if (!adminTask.result.isEmpty) {
                                            startActivity(Intent(applicationContext, MainAdminActivity::class.java))
                                            //verFragmentoPerfilAdmin()
                                        } else {
                                            // Manejar el caso donde el usuario no se encuentra en ninguna colección
                                        }
                                    } else {
                                        //
                                    }
                                }
                        }
                    } else {
                        //
                    }
                }
        }
    }


    private fun solicitarPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_DENIED){
                concederPermiso.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val concederPermiso =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ esConcedido->
            //El permiso se ha concedido
        }

    private fun agregarToken() {
        val miUid = "${firebaseAuth.uid}"
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener {fcmToken->
                val hashMap = HashMap<String,Any>()
                hashMap["fcmToken"] = "${fcmToken}"
                val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
                ref.child(miUid)
                    .updateChildren(hashMap)
                    .addOnSuccessListener {
                        /*El token se agregó con éxito*/
                    }
                    .addOnFailureListener {e->
                        Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {e->
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun verFragmentoPerfil(){
        binding.tvTitulo.text = "Perfil"

        val fragment = FragmentPerfil()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentoFL.id, fragment, "Fragment Perfil")
        fragmentTransaction.commit()
    }

    private fun verFragmentoTests(){
        binding.tvTitulo.text = "Tests"

        val fragment = FragmentTests()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentoFL.id, fragment, "Fragment Tests")
        fragmentTransaction.commit()
    }

    private fun verFragmentoFeedback(){
        binding.tvTitulo.text = "Feedback"

        val fragment = FragmentFeedback()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentoFL.id, fragment, "Fragment Feedback")
        fragmentTransaction.commit()
    }

    private fun verFragmentoPerfilAdmin(){
        binding.tvTitulo.text = "Perfil"

        val fragment = FragmentPerfilAdmin()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentoFL.id, fragment, "Fragment Perfil")
        fragmentTransaction.commit()
    }
}
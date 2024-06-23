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
import com.example.apporientacinvocacional.Fragmentos.FragmentAdd
import com.example.apporientacinvocacional.Fragmentos.FragmentPerfil
import com.example.apporientacinvocacional.Fragmentos.FragmentPerfilAdmin
import com.example.apporientacinvocacional.Fragmentos.FragmentTests
import com.example.apporientacinvocacional.databinding.ActivityMainAdminBinding
import com.example.apporientacinvocacional.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

class MainAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainAdminBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        verFragmentoPerfilAdmin()

        binding.bottomNV.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.item_perfil->{
                    //Visualizar el fragmento Perfil
                    verFragmentoPerfilAdmin()
                    true
                }
                R.id.item_tests->{
                    //Visualizar el fragmento Add
                    verFragmentoAdd()
                    true
                }
                else->{
                    false
                }
            }
        }
    }

    private fun comprobarSesion() {
        if (firebaseAuth.currentUser == null){
            startActivity(Intent(applicationContext, OpcionesLoginActivity::class.java))
            finishAffinity()
        }else{
            agregarToken()
            solicitarPermisoNotificaciones()
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

    private fun solicitarPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_DENIED){
                concederPermiso.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun verFragmentoPerfilAdmin(){
        binding.tvTitulo.text = "Perfil"

        val fragment = FragmentPerfilAdmin()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentoFL.id, fragment, "Fragment Perfil Admin")
        fragmentTransaction.commit()
    }

    private fun verFragmentoAdd(){
        binding.tvTitulo.text = "Subir Notas de Estudiante"

        val fragment = FragmentAdd()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentoFL.id, fragment, "Fragment Add")
        fragmentTransaction.commit()
    }
}
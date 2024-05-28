package com.example.apporientacinvocacional.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apporientacinvocacional.R
import com.example.apporientacinvocacional.databinding.FragmentPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.apporientacinvocacional.OpcionesLoginActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration


class FragmentPerfil : Fragment() {

    private lateinit var binding: FragmentPerfilBinding
    private lateinit var mContext: Context
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var userListener: ListenerRegistration? = null

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerfilBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth.currentUser?.email?.let { cargarInfo(it) }

        binding.btnCerrarsesion.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(mContext, OpcionesLoginActivity::class.java))
            activity?.finishAffinity()
        }
    }

    private fun cargarInfo(emailUsuario: String) {
        firestore.collection("usuario")
            .whereEqualTo("email", emailUsuario)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val nombres = document.getString("nombres")
                    val apPaterno = document.getString("apPaterno")
                    val apMaterno = document.getString("apMaterno")
                    val dni = document.getString("uid")
                    val genero = document.getString("genero")
                    val telefono = document.getString("telefono")
                    val colegio = document.getString("colegio")

                    binding.tvNombres.text = "$nombres $apPaterno $apMaterno"
                    binding.tvEmail.text = emailUsuario
                    binding.tvDni.text = dni
                    binding.tvTelefono.text = telefono
                    binding.tvGenero.text = genero
                    binding.tvColegio.text = colegio
                } else {
                    // El documento no existe o no se encontró coincidencia
                }
            }
            .addOnFailureListener { e->
                //pOR SI HAY ERRORES
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Limpiar listener
        userListener?.remove()
    }
}

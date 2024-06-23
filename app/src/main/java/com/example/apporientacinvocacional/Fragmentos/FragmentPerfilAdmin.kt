package com.example.apporientacinvocacional.Fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apporientacinvocacional.OpcionesLoginActivity
import com.example.apporientacinvocacional.R
import com.example.apporientacinvocacional.databinding.FragmentPerfilAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration


class FragmentPerfilAdmin : Fragment() {

    private lateinit var binding: FragmentPerfilAdminBinding
    private lateinit var mContext: Context
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var adminListener: ListenerRegistration? = null

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPerfilAdminBinding.inflate(layoutInflater, container, false)
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
        firestore.collection("admin")
            .whereEqualTo("email", emailUsuario)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val nombres = document.getString("nombres")
                    val apPaterno = document.getString("apPaterno")
                    val apMaterno = document.getString("apMaterno")
                    val dni = document.getString("uid")
                    val telefono = document.getString("telefono")

                    binding.tvNombres.text = "$nombres $apPaterno $apMaterno"
                    binding.tvEmail.text = emailUsuario
                    binding.tvDni.text = dni
                    binding.tvTelefono.text = telefono
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
        adminListener?.remove()
    }

}
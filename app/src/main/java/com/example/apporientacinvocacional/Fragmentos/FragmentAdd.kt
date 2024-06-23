package com.example.apporientacinvocacional.Fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apporientacinvocacional.AgregarAlumnoActivity
import com.example.apporientacinvocacional.OpcionesLoginActivity
import com.example.apporientacinvocacional.R
import com.example.apporientacinvocacional.databinding.FragmentAddBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration


class FragmentAdd : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private lateinit var mContext: Context
    private lateinit var firestore: FirebaseFirestore

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()

        binding.FABAgregarAlumno.setOnClickListener {
            startActivity(Intent(mContext, AgregarAlumnoActivity::class.java))
        }
    }

}
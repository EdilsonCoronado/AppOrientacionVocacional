package com.example.apporientacinvocacional.Fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apporientacinvocacional.R
import com.example.apporientacinvocacional.Test1Activity
import com.example.apporientacinvocacional.Test2Activity
import com.example.apporientacinvocacional.Test3Activity
import com.example.apporientacinvocacional.Test4Activity
import com.example.apporientacinvocacional.Test5Activity
import com.example.apporientacinvocacional.Test6Activity
import com.example.apporientacinvocacional.databinding.FragmentTestsBinding


class FragmentTests : Fragment() {

    private lateinit var binding: FragmentTestsBinding
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTestsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.containerTest1.setOnClickListener {
            startActivity(Intent(mContext, Test1Activity::class.java))
        }

        binding.containerTest2.setOnClickListener {
            startActivity(Intent(mContext, Test2Activity::class.java))
        }

        binding.containerTest3.setOnClickListener {
            startActivity(Intent(mContext, Test3Activity::class.java))
        }

        binding.containerTest4.setOnClickListener {
            startActivity(Intent(mContext, Test4Activity::class.java))
        }

        binding.containerTest5.setOnClickListener {
            startActivity(Intent(mContext, Test5Activity::class.java))
        }

        binding.containerTest6.setOnClickListener {
            startActivity(Intent(mContext, Test6Activity::class.java))
        }
    }


}
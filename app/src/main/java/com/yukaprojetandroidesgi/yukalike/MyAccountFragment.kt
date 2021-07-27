package com.yukaprojetandroidesgi.yukalike

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yukaprojetandroidesgi.yukalike.databinding.FragmentInfoProductBinding
import com.yukaprojetandroidesgi.yukalike.databinding.FragmentMyAccountBinding
import com.yukaprojetandroidesgi.yukalike.databinding.FragmentSignUpBinding
import kotlinx.android.synthetic.main.fragment_my_account.*

class MyAccountFragment : Fragment(R.layout.fragment_my_account) {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentMyAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val myEmail = view.findViewById<TextView>(R.id.myEmail)
        binding.myEmail.text = getUserEmail()
        binding.progressBar.progress = 1

        super.onViewCreated(view, savedInstanceState)
    }

    fun getUserEmail(): String {
        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            return email.toString()
        }
        return ""
    }
}
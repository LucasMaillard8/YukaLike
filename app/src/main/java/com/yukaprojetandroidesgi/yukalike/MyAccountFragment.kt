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
import com.yukaprojetandroidesgi.yukalike.databinding.FragmentSignUpBinding
import kotlinx.android.synthetic.main.fragment_my_account.*

class MyAccountFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val myEmail = view?.findViewById<TextView>(R.id.myEmail)
        myEmail?.text = getUserEmail()
        return inflater.inflate(R.layout.fragment_my_account, container, false)
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
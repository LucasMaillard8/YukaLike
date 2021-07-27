package com.yukaprojetandroidesgi.yukalike

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yukaprojetandroidesgi.yukalike.business.service.FirestoreProvider.getUserPoint
import com.yukaprojetandroidesgi.yukalike.business.service.NetworkListener
import com.yukaprojetandroidesgi.yukalike.databinding.FragmentMyAccountBinding

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
        getUserPoint(object: NetworkListener<Long> {
            override fun onSuccess(data: Long) {
                Log.d("LM", data.toString())
                binding.myPoints.text = "$data points"
                binding.progressBar.progress = data.toInt()
            }

            override fun onError(code: Int) {
                findNavController().navigate(R.id.action_infoProduct_to_errorBarcodeFragment)
            }
        })
        binding.myEmail.text = getUserEmail()

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
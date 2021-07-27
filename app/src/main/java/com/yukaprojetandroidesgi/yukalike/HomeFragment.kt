package com.yukaprojetandroidesgi.yukalike

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yukaprojetandroidesgi.yukalike.business.service.FirestoreProvider
import com.yukaprojetandroidesgi.yukalike.business.service.FirestoreProvider.addPointToUser
import com.yukaprojetandroidesgi.yukalike.business.service.NetworkListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scanButton = view.findViewById<Button>(R.id.scanButton)
        scanButton.setOnClickListener {
            if (!allPermissionsGranted()) {
                requestPermissions(
                    REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )
            }
            if (allPermissionsGranted()) {
                findNavController().navigate(R.id.action_homeFragment_to_productScannerFragment)
            }
        }
        val barCodeTextfield = view.findViewById<EditText>(R.id.barCodeTextfield)
        val validateScanButton = view.findViewById<Button>(R.id.validateScanButton)
        validateScanButton.setOnClickListener {
            if (barCodeTextfield.text.toString().matches(Regex("[0-9]+"))){
                //val bundle : Bundle = bundleOf("Barcode" to barCodeTextfield.text.toString())
                //findNavController().navigate(R.id.action_homeFragment_to_infoProduct, bundle)
                val navDirection = HomeFragmentDirections.actionHomeFragmentToInfoProduct(barCodeTextfield.text.toString())
                findNavController().navigate(navDirection)
            }


        }
        addPointToUser(1)
        myAccountButton.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_myAccountFragment) }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}
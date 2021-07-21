package com.yukaprojetandroidesgi.yukalike

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yukaprojetandroidesgi.yukalike.utils.BarcodeAnalyzer
import kotlinx.android.synthetic.main.fragment_product_scanner.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

class ProductScannerFragment : Fragment(R.layout.fragment_product_scanner) {
    private lateinit var cameraExecutor: ExecutorService
    private var processingBarcode = AtomicBoolean(false)
    private val scanBarcodeViewModel: ScanBarcodeViewModel by viewModels()

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(
                    fragment_scan_barcode_preview_view.surfaceProvider
                )
            }
            val imageAnalysis = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, BarcodeAnalyzer { barcode ->
                        if (processingBarcode.compareAndSet(false, true)) {
                            scanBarcodeViewModel.searchBarcode(barcode)
                        }
                    })
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
            } catch (e: Exception) {
                Log.e("PreviewUseCase", "Binding failed! :(", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        scanBarcodeViewModel.navigation.observe(viewLifecycleOwner, { navDirections ->
            navDirections?.let {
                //val bundle : Bundle = bundleOf("Codebar" to "010101")
                findNavController().navigate(navDirections)
                scanBarcodeViewModel.doneNavigating()
            }
        })
        startCamera()
    }
}
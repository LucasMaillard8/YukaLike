package com.yukaprojetandroidesgi.yukalike

import android.graphics.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.yukaprojetandroidesgi.yukalike.R
import com.yukaprojetandroidesgi.yukalike.services.BarcodeAnalyzer
import kotlinx.android.synthetic.main.activity_product_scanner.*
import java.util.concurrent.Executors

class ProductScannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_scanner)

        val frameLayout = findViewById<FrameLayout>(R.id.frameLayout)
        val cameraButton = findViewById<Button>(R.id.cameraButton)
    }

    private fun startCamera() {
        // Create an instance of the ProcessCameraProvider,
        // which will be used to bind the use cases to a lifecycle owner.
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        // Add a listener to the cameraProviderFuture.
        // The first argument is a Runnable, which will be where the magic actually happens.
        // The second argument (way down below) is an Executor that runs on the main thread.
        cameraProviderFuture.addListener({
            // Add a ProcessCameraProvider, which binds the lifecycle of your camera to
            // the LifecycleOwner within the application's life.
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            // Initialize the Preview object, get a surface provider from your PreviewView,
            // and set it on the preview instance.
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(
                    fragment_scan_barcode_preview_view.surfaceProvider
                )
            }

            // Select back camera
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                // Unbind any bound use cases before rebinding
                cameraProvider.unbindAll()
                // Bind use cases to lifecycleOwner
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
            } catch (e: Exception) {
                Log.e("PreviewUseCase", "Binding failed! :(", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }
}
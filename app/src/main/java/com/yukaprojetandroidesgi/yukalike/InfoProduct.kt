package com.yukaprojetandroidesgi.yukalike

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yukaprojetandroidesgi.yukalike.business.model.Product
import com.yukaprojetandroidesgi.yukalike.business.service.NetworkListener
import com.yukaprojetandroidesgi.yukalike.business.service.OpenFoodFactProvider
import com.yukaprojetandroidesgi.yukalike.databinding.FragmentInfoProductBinding

class InfoProduct : Fragment(R.layout.fragment_info_product) {
    private lateinit var binding: FragmentInfoProductBinding
    private var load: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoProductBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val safeArgs: InfoProductArgs by navArgs()
        val code = safeArgs.barCodeNumber

        if(load){
        //codebarNum.text = arguments?.getString("Barcode")
            OpenFoodFactProvider.getInfoProduit(code, object: NetworkListener<Product> {
                override fun onSuccess(data: Product) {
                    Log.d("LV", "success : ${data}")
                    binding.brend.text = data.marque
                    binding.calsApi.text = data.nutriments.calories.toString()
                    binding.calsUnit.text = data.nutriments.caloriesUnit
                    binding.sugarApi.text = data.nutriments.sugar.toString()
                    binding.sugarUnit.text = data.nutriments.sugarUnit
                }

                override fun onError(code: Int) {
                    Log.d("LV", "error code : $code")
                    load = false
                    findNavController().navigate(R.id.action_infoProduct_to_errorBarcodeFragment)
                }
            })
        }
    }
}
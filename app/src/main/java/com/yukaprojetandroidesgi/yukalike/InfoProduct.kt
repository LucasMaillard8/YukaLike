package com.yukaprojetandroidesgi.yukalike

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.yukaprojetandroidesgi.yukalike.business.model.Product
import com.yukaprojetandroidesgi.yukalike.business.service.NetworkListener
import com.yukaprojetandroidesgi.yukalike.business.service.OpenFoodFactProvider
import com.yukaprojetandroidesgi.yukalike.databinding.FragmentInfoProductBinding
import com.yukaprojetandroidesgi.yukalike.databinding.FragmentSignInBinding
import kotlinx.android.synthetic.main.fragment_home.*

class InfoProduct : Fragment(R.layout.fragment_info_product) {
    private lateinit var binding: FragmentInfoProductBinding

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

        //codebarNum.text = arguments?.getString("Barcode")
        val safeArgs: InfoProductArgs by navArgs()
        val code = safeArgs.barCodeNumber
        OpenFoodFactProvider.getInfoProduit(code, object: NetworkListener<Product> {
            override fun onSuccess(data: Product) {
                //Log.d("LV", "success : ${data.marque}")
                binding.brend.text = data.marque
                binding.calsApi.text = data.nutriments.calories.toString()
                binding.sugarApi.text = data.nutriments.sugar.toString()
            }

            override fun onError(code: Int) {
                Log.d("LV", "error code : $code")
            }

        })
    }
}
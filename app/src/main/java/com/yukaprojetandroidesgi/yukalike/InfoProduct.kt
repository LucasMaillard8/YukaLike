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
import kotlinx.android.synthetic.main.fragment_home.*

class InfoProduct : Fragment(R.layout.fragment_info_product) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //_binding = FragmentHistoryBinding.inflate(inflater, container, false)
        //val view = binding.root

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val codebarNum = view.findViewById<TextView>(R.id.CodebarNum)
        //codebarNum.text = arguments?.getString("Barcode")
        val safeArgs: InfoProductArgs by navArgs()
        val code = safeArgs.barCodeNumber
        OpenFoodFactProvider.getInfoProduit(code, object: NetworkListener<Product> {
            override fun onSuccess(data: Product) {
                Log.d("LV", "success : ${data.marque}")
                codebarNum.text = data.marque
            }

            override fun onError(code: Int) {
                Log.d("LV", "error code : $code")
            }

        })
    }
}
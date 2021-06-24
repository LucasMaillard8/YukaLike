package com.yukaprojetandroidesgi.yukalike.business.service

import android.util.Log
import com.yukaprojetandroidesgi.yukalike.business.model.Product
import com.yukaprojetandroidesgi.yukalike.business.service.dto.ProductMapper
import com.yukaprojetandroidesgi.yukalike.business.service.dto.ProductResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenFoodFactProvider {
    private val openFoodFactAPI: OpenFoodFactAPI

    // Init retrofit with base url
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://world.openfoodfacts.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        openFoodFactAPI = retrofit.create(OpenFoodFactAPI::class.java)
    }

    fun getInfoProduit(codeBarre: Long, listener: NetworkListener<Product>) {
        openFoodFactAPI.getInfoProduit(codeBarre).enqueue(object : Callback<ProductResponseDTO> {
            override fun onFailure(call: Call<ProductResponseDTO>, t: Throwable) {
                //Throw error
                Log.d("test failure getProduit", t.toString())
                listener.onError(500)
            }

            override fun onResponse(call: Call<ProductResponseDTO>, response: Response<ProductResponseDTO>) {
                val statusCode = response.code()
                // If statusCode say not authenticated throw error
                if (statusCode == 401 || statusCode == 402 || statusCode == 403) listener.onError(
                    statusCode
                )
                else {
                    //Get token from json response
                    val productDTO = response.body()
                    productDTO?.let {
                        listener.onSuccess(ProductMapper().mapProductFromResponse(it))
                    } ?: listener.onError(500)
                }


            }

        })
    }
}

interface NetworkListener<T> {
    fun onSuccess(data: T)
    fun onError(code: Int)
}
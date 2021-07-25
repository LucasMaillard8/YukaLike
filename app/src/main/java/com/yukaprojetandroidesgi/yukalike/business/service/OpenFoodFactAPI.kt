package com.yukaprojetandroidesgi.yukalike.business.service

import com.yukaprojetandroidesgi.yukalike.business.service.dto.ProductResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenFoodFactAPI {

    @GET("api/v0/product/{codeBarre}.json")
    fun getInfoProduit(@Path("codeBarre") codeBarre: String): Call<ProductResponseDTO>
}
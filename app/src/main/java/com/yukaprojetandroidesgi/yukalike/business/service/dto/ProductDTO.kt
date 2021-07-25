package com.yukaprojetandroidesgi.yukalike.business.service.dto

import com.google.gson.annotations.SerializedName
import com.yukaprojetandroidesgi.yukalike.business.model.Nutriments
import com.yukaprojetandroidesgi.yukalike.business.model.Product
import okhttp3.Challenge

data class ProductResponseDTO(
    @SerializedName("code")
    val code: String,
    @SerializedName("product")
    val product: ProductDTO
)

data class ProductDTO(
    @SerializedName("brands")
    val marque: String,
    @SerializedName("nutriments")
    val nutriment: NutrimentDTO,
)

data class NutrimentDTO(
    @SerializedName("energy-kcal")
    val calories: Float,
    @SerializedName("sugars")
    val sugar: Float
)

class ProductMapper {
    fun mapProductFromResponse(dto: ProductResponseDTO): Product {
        val nutriments = Nutriments(dto.product.nutriment.calories, dto.product.nutriment.sugar)
        return Product(dto.product.marque, nutriments)
    }
}
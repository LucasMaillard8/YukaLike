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
    @SerializedName("image_front_url")
    val image: String
)

data class NutrimentDTO(
    @SerializedName("energy-kcal_100g")
    val calories: Float,
    @SerializedName("energy-kcal_unit")
    val caloriesUnit: String,
    @SerializedName("sugars_100g")
    val sugar: Float,
    @SerializedName("sugars_unit")
    val sugarUnit: String
)

class ProductMapper {
    fun mapProductFromResponse(dto: ProductResponseDTO): Product {
        val nutriments = Nutriments(
            dto.product.nutriment.calories,
            dto.product.nutriment.caloriesUnit,
            dto.product.nutriment.sugar,
            dto.product.nutriment.sugarUnit)
        return Product(dto.product.marque, nutriments, dto.product.image)
    }
}
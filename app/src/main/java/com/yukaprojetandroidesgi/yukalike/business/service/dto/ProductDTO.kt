package com.yukaprojetandroidesgi.yukalike.business.service.dto

import com.google.gson.annotations.SerializedName
import com.yukaprojetandroidesgi.yukalike.business.model.Product
import okhttp3.Challenge

data class ProductResponseDTO(
    @SerializedName("code")
    val code: String,
    @SerializedName("product")
    val product: ProductDTO
)

data class ProductDTO(
    @SerializedName("brand_owner")
    val marque: String,
)


class ProductMapper {

    fun mapProductFromResponse(dto: ProductResponseDTO): Product {
        return Product(dto.product.marque)
    }
}
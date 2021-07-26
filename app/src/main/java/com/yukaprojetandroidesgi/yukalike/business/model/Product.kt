package com.yukaprojetandroidesgi.yukalike.business.model

data class Product (
    val marque: String,
    val nutriments: Nutriments
)

data class Nutriments(
    val calories: Float,
    val caloriesUnit: String,
    val sugar: Float,
    val sugarUnit: String
)
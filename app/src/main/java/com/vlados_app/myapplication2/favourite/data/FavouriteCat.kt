package com.vlados_app.myapplication2.favourite.data

import com.google.gson.annotations.SerializedName

data class FavouriteCat(
    val id: String,
    @SerializedName("image_id") val imageId: String,
    val image: Image
)

data class Image(
    val url: String
)
package com.vlados_app.myapplication2.favourite.data

import com.google.gson.annotations.SerializedName

data class FavouriteCatDto(
    @SerializedName("id") val id: String,
    @SerializedName("image_id") val imageId: String,
    @SerializedName("image") val imageDto: ImageDto
)

data class ImageDto(
    @SerializedName("url") val url: String
)
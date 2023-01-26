package com.vlados_app.myapplication2.cat_list.data.model

import com.google.gson.annotations.SerializedName

data class FavouriteCatRequest(
    @SerializedName("image_id") val imageId: String
)

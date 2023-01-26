package com.vlados_app.myapplication2.cat_list.data.model

import com.google.gson.annotations.SerializedName

data class CatDto(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
)
package com.vlados_app.myapplication2.favourite.data

import com.vlados_app.myapplication2.favourite.domain.FavouriteCatModel
import javax.inject.Inject

class FavouriteCatListMapper @Inject constructor() {
    fun map(catDto: FavouriteCatDto): FavouriteCatModel  = with(catDto) {
        FavouriteCatModel(
            id = id,
            imageId = imageId,
            url = imageDto.url
        )
    }
}
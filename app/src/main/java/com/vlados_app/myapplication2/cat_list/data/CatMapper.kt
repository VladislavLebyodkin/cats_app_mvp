package com.vlados_app.myapplication2.cat_list.data

import com.vlados_app.myapplication2.cat_list.data.model.CatDto
import com.vlados_app.myapplication2.cat_list.domain.CatModel
import javax.inject.Inject

class CatMapper @Inject constructor() {
    fun map(catDto: CatDto, isFavourite: Boolean): CatModel = with(catDto) {
        CatModel(
            id = id,
            url = url,
            isFavourite = isFavourite
        )
    }
}
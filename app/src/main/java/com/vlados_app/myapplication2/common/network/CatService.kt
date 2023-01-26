package com.vlados_app.myapplication2.common.network

import com.vlados_app.myapplication2.cat_list.data.model.CatDto
import com.vlados_app.myapplication2.cat_list.data.model.FavouriteCatRequest
import com.vlados_app.myapplication2.cat_list.data.model.FavouriteCatResponse
import com.vlados_app.myapplication2.favourite.data.FavouriteCatDto
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CatService {
    @GET("images/search")
    fun loadCatList(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10,
        @Query("order") order: String = "ASC"
    ): Single<List<CatDto>>

    @POST("favourites")
    fun addToFavourites(
        @Body favouriteCatRequest: FavouriteCatRequest
    ): Single<FavouriteCatResponse>

    @GET("favourites")
    fun loadFavouriteCatList(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10,
        @Query("attach_image") attachImage: Int = 1
    ): Single<List<FavouriteCatDto>>
}

fun createCatService(): CatService {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val origin = chain.request()
            val request = origin.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader(Consts.HEADER_API_KEY, Consts.API_KEY)
                .build()
            chain.proceed(request)
        }
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(Consts.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(CatService::class.java)
}
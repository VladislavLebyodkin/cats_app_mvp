package com.vlados_app.myapplication2.network

import com.vlados_app.myapplication2.cat_list.data.Cat
import com.vlados_app.myapplication2.favourite.data.FavouriteCat
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CatService {
    @GET("images/search")
    fun loadCatList(
        @Query("api_key") apiKey: String = "live_5WVW2pzGrLJCL7i8IC91Ed7WJ6OsgXQ5RaUZb4KBF1spZI1bhPc3ZAyz8ljABQcV",
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int,
        @Query("order") order: String = "ASC"
    ): Single<List<Cat>>

    @POST("favourites")
    fun addToFavourites(
        @Query("image_id") imageId: String
    ): Single<List<Cat>>

    @GET("favourites")
    fun loadFavouriteCatList(
        @Query("api_key") apiKey: String = "live_5WVW2pzGrLJCL7i8IC91Ed7WJ6OsgXQ5RaUZb4KBF1spZI1bhPc3ZAyz8ljABQcV",
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int,
    ): Single<List<FavouriteCat>>
}

private const val HEADER_API_KEY = "x-api-key"

fun CatService(apiKey: String, baseUrl: String): CatService {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val authInterceptor = Request.Builder()
//                .addHeader(HEADER_API_KEY, apiKey)
//                .build()
//
//            chain.proceed(authInterceptor)
//        }
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(CatService::class.java)
}
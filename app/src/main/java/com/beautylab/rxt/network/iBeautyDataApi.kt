package com.beautylab.rxt

import com.beautylab.rxt.models.ArticlesModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface iBeautyDataApi {

    @GET
    fun getData(@Url url: String): Single<List<ArticlesModel>>
}
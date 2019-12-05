package com.beautylab.rxt

import com.beautylab.rxt.models.MyModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface iCountryApi {

    @GET
    fun getCountryCode(@Url url: String): Single<MyModel>
}
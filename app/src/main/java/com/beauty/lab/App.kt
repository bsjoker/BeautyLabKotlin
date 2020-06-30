package com.beauty.lab

import android.app.Application
import com.beauty.lab.di.KoinModule.loadModule
import io.reactivex.plugins.RxJavaPlugins
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(loadModule())
        }

        RxJavaPlugins.setErrorHandler { throwable -> }
        instance = this
    }

    companion object {
        fun create(): iBeautyDataApi {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://80.211.207.96:14234/")
                .client(client)
                .build()

            return retrofit.create(iBeautyDataApi::class.java)
        }

        lateinit var instance: App
            private set
    }
}
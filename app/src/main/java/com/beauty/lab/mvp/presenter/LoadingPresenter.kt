package com.beauty.lab.mvp.presenter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.beauty.lab.App
import com.beauty.lab.di.LoadingContract
import com.beauty.lab.models.ArticlesModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoadingPresenter(val mView: LoadingContract.View): LoadingContract.Presenter {
    companion object {
        const val TAG = "LoadingPresenter"
    }

    var disposable: Disposable? = null
    var i: Long = 0

    private val mIBeautyDataApi by lazy {
        App.create()
    }

    override fun getRecipesFromServer() {
        if (hasConnection(App.instance.applicationContext)) {
            disposable = mIBeautyDataApi.getData("bsjoker/BeautyLabKotlin/master/receipts.json")
                .retry()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        var mArticles = ArrayList<ArticlesModel>()
                        mArticles.addAll(result)

                        result.forEach {
                            i++
                            it.id = i
                            mView.setArtictesViewModel(it)
                        }
                    },
                    { error -> Log.d(TAG, "Error to try catch data!") }
                )
        } else {
            Log.d(TAG, "No connect")
            mView.checkConnection()
        }
    }

    override fun hasConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var wifiInfo: NetworkInfo? = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.activeNetworkInfo
        return wifiInfo != null && wifiInfo.isConnected
    }

    override fun disposeDisposable() {
        disposable?.dispose()
    }
}
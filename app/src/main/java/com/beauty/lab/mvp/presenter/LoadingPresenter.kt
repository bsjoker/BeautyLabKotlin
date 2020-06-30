package com.beauty.lab.mvp.presenter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager
import android.util.Log
import com.beauty.lab.App
import com.beauty.lab.mvp.view.LoadingActivity
import com.beauty.lab.R
import com.beauty.lab.di.LoadingContract
import com.beauty.lab.models.ArticlesModel
import com.beauty.lab.utils.PreferencesHelper
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.InvalidClassException

class LoadingPresenter(val mView: LoadingContract.View): LoadingContract.Presenter {
    companion object {
        const val TAG = "LoadingPresenter"
    }

    var disposable: Disposable? = null
    var i: Long = 0
    private var mCountry: String = ""
    private var catchData = false
    private var isStart = false
    private var mState = false
    private var accessCountry = false
    private lateinit var myRef: DatabaseReference

    private val mIBeautyDataApi by lazy {
        App.create()
    }

    private val mICountryApi by lazy {
        App.createCountry()
    }

    override fun initFirebsae() {
        myRef = FirebaseDatabase.getInstance().getReference()
        checkAuditoryParameters()
    }

    override fun checkIconChange() {
        val iconsList = App.instance.resources.getStringArray(R.array.iconsList).asList()
        myRef.child("listIcon").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d(TAG, "Value is: $value")
                if (iconsList.contains(value)) {
                    if (value != null) {
                        mView.changeIcon(value)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(LoadingActivity.TAG, "Failed to read value.", error.toException())
            }
        })
    }

    override fun checkAuditoryParameters() {
        myRef.child("state").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(Boolean::class.java)
                Log.d(TAG, "Value is: $value")
                mState = value!!
                try {
                    PreferencesHelper().savePreference("state", value)
                } catch (e: InvalidClassException) {
                    e.printStackTrace()
                }

                if (mCountry != null && !isStart && catchData) {
                    mView.startNewActivity(value)
                    isStart = true
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        if (hasConnection(App.instance.applicationContext)) {
            disposable = mICountryApi.getCountryCode("json/")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        mCountry = result.countryCode
                        Log.d(LoadingActivity.TAG, "${mCountry} result found")
                        PreferencesHelper().savePreference("country", mCountry)
                        accessCountry = checkCountry(mCountry)
                        if (!isStart && catchData && mState) {
                            if (PreferencesHelper().getSharedPreferences().getBoolean("state", false) && accessCountry) {
                                mView.startNewActivity(true)
                            } else {
                                mView.startNewActivity(false)
                            }
                            isStart = true
                        }
                    },
                    { error -> Log.d(TAG, "Request error!") }
                )
        } else {
            Log.d(LoadingActivity.TAG, "No connect")
        }
    }

    override fun checkCountry(mCountry: String): Boolean {
        myRef.child("AcceptCountry").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                Log.d(LoadingActivity.TAG, "Value country is: $value")
                if (value != null) {
                    accessCountry = value.contains(mCountry)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(LoadingActivity.TAG, "Failed to read value.", error.toException())
            }
        })
        return accessCountry
    }

    override fun getRecipesFromServer() {
        if (hasConnection(App.instance.applicationContext)) {
            disposable = mIBeautyDataApi.getData("info")
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
                            //articlesViewModel.insert(it)
                            Log.d(TAG, "Data from result: " + it.title + " " + it.id)
                        }
                        catchData = true
                    },
                    { error -> Log.d(TAG, "Error to try catch data!") }
                )
        } else {
            Log.d(TAG, "No connect")
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

    override fun setSubscribeToTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("pharaon")
            .addOnSuccessListener { println("!!!") }

        FirebaseMessaging.getInstance().subscribeToTopic("country_" + getCountry())
            .addOnSuccessListener { println("!!!") }
    }

    fun getCountry(): String {
        val telephonyManager = App.instance.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        Log.d(LoadingActivity.TAG, "Country: " + telephonyManager.simCountryIso.toLowerCase())
        return telephonyManager.simCountryIso.toLowerCase()
    }

    override fun disposeDisposable() {
        disposable?.dispose()
    }
}
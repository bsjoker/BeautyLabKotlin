package com.beautylab.rxt.mvp.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import bolts.AppLinks
import com.beautylab.rxt.App
import com.beautylab.rxt.MyWebView
import com.beautylab.rxt.R
import com.facebook.applinks.AppLinkData
import com.beautylab.rxt.database.ArticlesViewModel
import com.beautylab.rxt.di.LoadingContract
import com.beautylab.rxt.models.ArticlesModel
import com.beautylab.rxt.utils.PreferencesHelper
import kotlinx.android.synthetic.main.activity_loading.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.io.InvalidClassException

class LoadingActivity : AppCompatActivity(), LoadingContract.View {
    companion object {
        const val TAG = "LoadingActivity"
    }

    val mPresenter: LoadingContract.Presenter by inject { parametersOf(this) }

    private lateinit var articlesViewModel: ArticlesViewModel

    private lateinit var pathSegments: List<String>
    private var isStart = false
    private var mState = false
    private var accessCountry = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        mPresenter.getRecipesFromServer()
        mPresenter.initFirebsae()
        mPresenter.checkIconChange()
        mPresenter.setSubscribeToTopic()

        showProgress()

        AppLinkData.fetchDeferredAppLinkData(this,
            object : AppLinkData.CompletionHandler {
                override fun onDeferredAppLinkDataFetched(appLinkData: AppLinkData?) {
                    if (appLinkData != null) {
                        try {
                            pathSegments = appLinkData.getTargetUri().getPathSegments()
                            if (pathSegments != null) {
                                PreferencesHelper().savePreference("sub1", pathSegments[0])
                                PreferencesHelper().savePreference("sub2", pathSegments[1])
                                PreferencesHelper().savePreference("sub3", pathSegments[2])
                                PreferencesHelper().savePreference("sub4", pathSegments[3])
                            }
                            Log.i(
                                "TAG",
                                "Deep link receive: " + appLinkData.getTargetUri().getLastPathSegment()
                            )
                        } catch (e: NullPointerException) {
                            e.printStackTrace()
                        } catch (e: InvalidClassException) {
                            e.printStackTrace()
                        }
                    } else {
                        try {
                            var targetUri =
                                AppLinks.getTargetUrlFromInboundIntent(applicationContext, intent)
                            if (targetUri != null) {
                                pathSegments = targetUri.pathSegments
                                if (pathSegments != null) {
                                    PreferencesHelper().savePreference("sub1", pathSegments[0])
                                    PreferencesHelper().savePreference("sub2", pathSegments[1])
                                    PreferencesHelper().savePreference("sub3", pathSegments[2])
                                    PreferencesHelper().savePreference("sub4", pathSegments[3])
                                }
                            }
                        } catch (e: NullPointerException) {
                            e.printStackTrace()
                        } catch (e: InvalidClassException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        )

        articlesViewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
    }

    override fun setArtictesViewModel(am: ArticlesModel) {
        articlesViewModel.insert(am)
    }

    private fun showProgress() {
        var speed = 105
        Thread(Runnable {
            var prs = 0

            while (prs < 100) {

                speed--
                Thread.sleep(speed.toLong())
                pb_horizontal.setProgress(prs)
                //Log.d(TAG, "Progress: " + progress)

                prs++
            }
            if (!isStart) {
//                if (mState && accessCountry) {
//                    Log.d(TAG, "Progress mState: " + mState + " . accessCountry: " + accessCountry)
//                    startActivity(Intent(this@LoadingActivity, MyWebView::class.java))
//                } else {
//                    startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
//                }
                startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
                isStart = true
            }
        }).start()
    }

    override fun startNewActivity(value: Boolean) {
        when (value) {
            true -> startActivity(Intent(this@LoadingActivity, MyWebView::class.java))
            false -> startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
        }
        isStart = true
    }

    override fun changeIcon(name: String) {
        val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        if (prefs.getString("alias-name", "") != name) {
            val pm = packageManager
            try {
                pm.setComponentEnabledSetting(
                    ComponentName(App.instance.applicationContext, "$packageName.$name"),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP
                )
                pm.setComponentEnabledSetting(
                    componentName,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP
                )
            } catch (e: Exception) {
                e.printStackTrace()

            }
            prefs.edit().putString("alias-name", name).apply()
        }
    }

    override fun onPause() {
        super.onPause()
        mPresenter.disposeDisposable()
    }
}
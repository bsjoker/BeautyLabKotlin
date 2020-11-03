package com.beauty.lab.mvp.view

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beauty.lab.R
import com.beauty.lab.database.ArticlesViewModel
import com.beauty.lab.di.LoadingContract
import com.beauty.lab.models.ArticlesModel
import kotlinx.android.synthetic.main.activity_loading.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class LoadingActivity : AppCompatActivity(), LoadingContract.View {
    companion object {
        const val TAG = "LoadingActivity"
    }

    val mPresenter: LoadingContract.Presenter by inject { parametersOf(this) }

    private lateinit var articlesViewModel: ArticlesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        startProgress()

        var oa = ObjectAnimator.ofFloat(tvLodanig, "alpha", 1.0f, 0.0f).apply {
            duration = 700
            interpolator = LinearInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        articlesViewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
        articlesViewModel.allArticles.observe(this, Observer { articles ->
            articles?.let {
                if (!it.isEmpty()) {
                    startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
                } else mPresenter.getRecipesFromServer()
            }
        })
    }

    override fun setArtictesViewModel(am: ArticlesModel) {
        articlesViewModel.insert(am)
        startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
    }

    override fun checkConnection() {
        tvLodanig.visibility = View.VISIBLE
        tvLodanig.text = resources.getString(R.string.check_internet_connection)
        tvLodanig.setOnClickListener {
            mPresenter.getRecipesFromServer()
        }
        pb_horizontal.alpha = 0.0f
    }

    private fun startProgress() {
        var speed = 105
        Thread(Runnable {
            var prs = 0
            while (prs < 100) {
                speed--
                Thread.sleep((speed / 3).toLong())
                pb_horizontal.setProgress(prs)
                prs++
            }

            runOnUiThread {
                tvLodanig.visibility = View.VISIBLE
            }
        }).start()
    }

    override fun onPause() {
        super.onPause()
        mPresenter.disposeDisposable()
    }
}
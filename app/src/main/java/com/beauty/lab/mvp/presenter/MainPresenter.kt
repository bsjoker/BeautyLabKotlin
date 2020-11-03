package com.beauty.lab.mvp.presenter

import androidx.core.content.ContextCompat
import com.beauty.lab.App
import com.beauty.lab.R
import com.beauty.lab.di.MainContract
import com.beauty.lab.models.RecipeModelForRV
import com.beauty.lab.utils.EnumOfRV

class MainPresenter(val mView: MainContract.View) : MainContract.Presenter {
    companion object {
        const val TAG = "MainPresenter"
    }

    lateinit var number: IntArray
    lateinit var numberOfRecipe: IntArray
    private val drawArrayPopular = intArrayOf(
        R.drawable.popular_recp_01,
        R.drawable.popular_recp_02,
        R.drawable.popular_recp_03,
        R.drawable.popular_recp_04,
        R.drawable.popular_recp_05,
        R.drawable.popular_recp_06
    )
    private val drawArrayNew = intArrayOf(
        R.drawable.new_recp_01,
        R.drawable.new_recp_02,
        R.drawable.new_recp_03,
        R.drawable.new_recp_04,
        R.drawable.new_recp_05,
        R.drawable.new_recp_06
    )
    private val popularRecipeForRVS: ArrayList<RecipeModelForRV> = ArrayList()
    private val newRecipeForRVS: ArrayList<RecipeModelForRV> = ArrayList()

    override fun fillListOfData() {
        for (i in 0..5) {

            popularRecipeForRVS.add(
                RecipeModelForRV(
                    ContextCompat.getDrawable(App.instance.applicationContext, drawArrayPopular[i])!!,
                    App.instance.resources.getStringArray(R.array.popular_recp)[i]
                )
            )

            newRecipeForRVS.add(
                RecipeModelForRV(
                    ContextCompat.getDrawable(App.instance.applicationContext, drawArrayNew[i])!!,
                    App.instance.resources.getStringArray(R.array.new_recp)[i]
                )
            )
        }

        mView.setDataInRV(popularRecipeForRVS, newRecipeForRVS)
    }

    override fun clickPos(pos: Int, type: EnumOfRV) {
        when (type) {
            EnumOfRV.VIEWPAGER -> {
                number = intArrayOf(4, 3, 0, 3, 4)
                numberOfRecipe = intArrayOf(29, 0, 2, 16, 0)
            }

            EnumOfRV.RECYCLERVIEW_POPULAR ->
            {
                number = intArrayOf(3, 3, 4, 0, 4, 3)
                numberOfRecipe = intArrayOf(6, 7, 30, 9, 11, 4)
            }

            EnumOfRV.RECYCLERVIEW_NEW ->
            {
                number = intArrayOf(3, 4, 0, 4, 3, 3)
                numberOfRecipe = intArrayOf(10, 37, 4, 19, 15, 17)
            }
        }
        mView.startNextActivity(number[pos], numberOfRecipe[pos])
    }
}
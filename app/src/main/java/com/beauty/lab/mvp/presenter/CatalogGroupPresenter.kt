package com.beauty.lab.mvp.presenter

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import com.beauty.lab.App
import com.beauty.lab.R
import com.beauty.lab.di.CatalogGroupContract
import com.beauty.lab.models.ArticlesModel
import com.beauty.lab.models.RecipeModelForRVGroup
import com.beauty.lab.models.RecipesModel

class CatalogGroupPresenter(val mView: CatalogGroupContract.View): CatalogGroupContract.Presenter {
    companion object {
        const val TAG = "CatalogGroupPresenter"
    }

    lateinit var image: Drawable
    private var text: String = ""
    private var numImage: Int = 0
    private var mArticles = ArrayList<ArticlesModel>()
    private var mRecipes = ArrayList<RecipesModel>()
    private lateinit var currentDraweblesList: IntArray
    private val recipesForRV: ArrayList<RecipeModelForRVGroup> = ArrayList()

    private val draweblesHair = intArrayOf(
        R.drawable.hair_01,
        R.drawable.hair_02,
        R.drawable.hair_03,
        R.drawable.hair_04,
        R.drawable.hair_05
    )

    private val draweblesBody = intArrayOf(
        R.drawable.body_01,
        R.drawable.body_02,
        R.drawable.body_03,
        R.drawable.body_04,
        R.drawable.body_05,
        R.drawable.body_06,
        R.drawable.body_07,
        R.drawable.body_08
    )

    private val draweblesFace = intArrayOf(
        R.drawable.face_01,
        R.drawable.face_02,
        R.drawable.face_03,
        R.drawable.face_04,
        R.drawable.face_05
    )

    override fun fillDataFromDB(
        listArticles: List<ArticlesModel>,
        num: Int
    ) {
        fillDrawList(num)
        mArticles.addAll(listArticles)
        mRecipes.addAll(mArticles[num].articles)
        var numImageOld = 0
        mRecipes.forEach {
            numImage = (0..4).random()
            while (numImage == numImageOld) {
                numImage = (0..4).random()
                Log.d(TAG, "NumImage: " + numImage + ". NumImageOld: " + numImageOld)
            }
            numImageOld = numImage
            var recipeModelForRVGroup = RecipeModelForRVGroup(
                ContextCompat.getDrawable(
                    App.instance.applicationContext,
                    currentDraweblesList[numImage]
                ), it.name, it.rate, it.description
            )
            recipesForRV.add(recipeModelForRVGroup)
        }
        mView.updateRV(recipesForRV)
    }

    private fun fillDrawList(num: Int) {
        when (num) {
            0 -> {
                currentDraweblesList = draweblesHair
                image = ContextCompat.getDrawable(App.instance.applicationContext,
                    R.drawable.group_hair
                )!!
                text = App.instance.resources.getStringArray(R.array.group_main_catalog)[0]
                mView.setData(image, text)
            }

            1 -> {
                currentDraweblesList = draweblesBody
                image = ContextCompat.getDrawable(App.instance.applicationContext,
                    R.drawable.group_body
                )!!
                text = App.instance.resources.getStringArray(R.array.group_main_catalog)[1]
                mView.setData(image, text)
            }

            3 -> {
                currentDraweblesList = draweblesBody
                image = ContextCompat.getDrawable(App.instance.applicationContext,
                    R.drawable.group_body
                )!!
                text = App.instance.resources.getStringArray(R.array.group_main_catalog)[2]
                mView.setData(image, text)
            }

            4 -> {
                currentDraweblesList = draweblesFace
                image = ContextCompat.getDrawable(App.instance.applicationContext,
                    R.drawable.group_face
                )!!
                text = App.instance.resources.getStringArray(R.array.group_main_catalog)[3]
                mView.setData(image, text)
            }
        }

    }
}
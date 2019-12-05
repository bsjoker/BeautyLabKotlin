package com.beautylab.rxt.mvp.presenter

import androidx.core.content.ContextCompat
import com.beautylab.rxt.App
import com.beautylab.rxt.R
import com.beautylab.rxt.di.CatalogContract
import com.beautylab.rxt.models.RecipeModelForRV

class CatalogPresenter(val mView: CatalogContract.View): CatalogContract.Presenter {
    companion object {
        const val TAG = "CatalogPresenter"
    }

    private val drawArray = intArrayOf(
        R.drawable.group_hair,
        R.drawable.group_home,
        R.drawable.group_body,
        R.drawable.group_face
    )
    private val groupsForRV: ArrayList<RecipeModelForRV> = ArrayList()

    override fun fillDataForRV() {
        for (i in 0..3){
            groupsForRV.add(
                RecipeModelForRV(
                    ContextCompat.getDrawable(App.instance.applicationContext, drawArray[i])!!,
                    App.instance.resources.getStringArray(R.array.group_main_catalog)[i]
                )
            )
        }

        mView.setDataToRV(groupsForRV)
    }

    override fun clickToItem(pos: Int) {
        mView.startNewActivity(pos)
    }
}
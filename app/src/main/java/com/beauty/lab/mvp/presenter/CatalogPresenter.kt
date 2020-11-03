package com.beauty.lab.mvp.presenter

import androidx.core.content.ContextCompat
import com.beauty.lab.App
import com.beauty.lab.R
import com.beauty.lab.di.CatalogContract
import com.beauty.lab.models.RecipeModelForRV

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

    override fun fillDataForReciclerView() {
        for (i in 0..3){
            groupsForRV.add(
                RecipeModelForRV(
                    ContextCompat.getDrawable(App.instance.applicationContext, drawArray[i])!!,
                    App.instance.resources.getStringArray(R.array.group_main_catalog)[i]
                )
            )
        }

        mView.setDataInRV(groupsForRV)
    }

    override fun clickToItem(pos: Int) {
        mView.startActivity(pos)
    }
}
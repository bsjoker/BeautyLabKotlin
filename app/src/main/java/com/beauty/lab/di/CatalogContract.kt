package com.beauty.lab.di

import com.beauty.lab.models.RecipeModelForRV

interface CatalogContract {
    interface View {
        fun setDataToRV(groupsForRV: ArrayList<RecipeModelForRV>)
        fun startNewActivity(pos: Int)

    }

    interface Presenter {
        fun fillDataForRV()
        fun clickToItem(pos: Int)

    }
}
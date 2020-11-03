package com.beauty.lab.di

import com.beauty.lab.models.RecipeModelForRV

interface CatalogContract {
    interface View {
        fun setDataInRV(groupsForRV: ArrayList<RecipeModelForRV>)
        fun startActivity(pos: Int)

    }

    interface Presenter {
        fun fillDataForReciclerView()
        fun clickToItem(pos: Int)

    }
}
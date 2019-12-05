package com.beautylab.rxt.di

import com.beautylab.rxt.models.RecipeModelForRV
import com.beautylab.rxt.utils.EnumOfRV

interface MainContract {
    interface View {
        fun startNextActivity(num: Int, numRecipe: Int)
        fun setDataToRV(popularRecipeForRVS: ArrayList<RecipeModelForRV>, newRecipeForRVS: ArrayList<RecipeModelForRV>)
    }

    interface Presenter {
        fun fillListData()
        fun clickPosition(pos: Int, type: EnumOfRV)
    }
}
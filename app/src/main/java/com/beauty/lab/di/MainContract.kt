package com.beauty.lab.di

import com.beauty.lab.models.RecipeModelForRV
import com.beauty.lab.utils.EnumOfRV

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
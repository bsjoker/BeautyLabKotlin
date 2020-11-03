package com.beauty.lab.di

import com.beauty.lab.models.RecipeModelForRV
import com.beauty.lab.utils.EnumOfRV

interface MainContract {
    interface View {
        fun startNextActivity(num: Int, numRecipe: Int)
        fun setDataInRV(popularRecipeForRVS: ArrayList<RecipeModelForRV>, newRecipeForRVS: ArrayList<RecipeModelForRV>)
    }

    interface Presenter {
        fun fillListOfData()
        fun clickPos(pos: Int, type: EnumOfRV)
    }
}
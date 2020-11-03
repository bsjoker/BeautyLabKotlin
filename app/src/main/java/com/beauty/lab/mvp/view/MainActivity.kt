package com.beauty.lab.mvp.view

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.beauty.lab.R
import com.beauty.lab.RecipeActivity
import com.beauty.lab.adapter.RecipesAdapter
import com.beauty.lab.adapter.ViewPagerAdapter
import com.beauty.lab.di.MainContract
import com.beauty.lab.models.RecipeModelForRV
import com.beauty.lab.utils.EnumOfRV
import com.beauty.lab.utils.StartSnapHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), MainContract.View {
    companion object {
        const val TAG = "MainActivity"
    }

    lateinit var recipeAdapterTop: RecipesAdapter
    lateinit var recipeAdapterBottom: RecipesAdapter

    val mPresenter: MainContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter.fillListOfData()

        viewPager2.adapter = ViewPagerAdapter {
            mPresenter.clickPos(it, EnumOfRV.VIEWPAGER)
        }

        val typeface = Typeface.createFromAsset(assets, "playfair.ttf")
        popular_recp.typeface = typeface
        new_recp.typeface = typeface

        recyclerViewPopular.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewNew.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        navigation.setOnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.navigation_catalog) {
                startActivity(Intent(this@MainActivity, CatalogActivity::class.java))
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun setDataInRV(
        popularRecipeForRV: ArrayList<RecipeModelForRV>,
        newRecipeForRV: ArrayList<RecipeModelForRV>
    ) {
        recipeAdapterTop = RecipesAdapter(popularRecipeForRV) {
            Log.d(TAG, "clicked at : $it")
            mPresenter.clickPos(it, EnumOfRV.RECYCLERVIEW_POPULAR)
        }
        recyclerViewPopular.adapter = recipeAdapterTop

        recipeAdapterBottom = RecipesAdapter(newRecipeForRV) {
            Log.d(TAG, "clicked at : $it")
            mPresenter.clickPos(it, EnumOfRV.RECYCLERVIEW_NEW)
        }
        recyclerViewNew.adapter = recipeAdapterBottom

        val snapHelperPopular = StartSnapHelper()
        snapHelperPopular.attachToRecyclerView(recyclerViewPopular)

        val snapHelperNew = StartSnapHelper()
        snapHelperNew.attachToRecyclerView(recyclerViewNew)
    }

    override fun startNextActivity(num: Int, numRecipe: Int) {
        startActivity(
            Intent(
                this@MainActivity,
                RecipeActivity::class.java
            ).putExtra("groupNum", num).putExtra("recipeNum", numRecipe)
        )
    }
}

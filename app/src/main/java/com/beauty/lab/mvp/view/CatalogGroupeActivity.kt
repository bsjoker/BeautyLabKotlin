package com.beauty.lab.mvp.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.beauty.lab.R
import com.beauty.lab.RecipeActivity
import com.beauty.lab.adapter.GroupAdapter
import com.beauty.lab.database.ArticlesViewModel
import com.beauty.lab.di.CatalogGroupContract
import com.beauty.lab.models.RecipeModelForRVGroup
import kotlinx.android.synthetic.main.activity_catalog_grope.*
import kotlinx.android.synthetic.main.activity_catalog_grope.navigation
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import kotlin.collections.ArrayList

class CatalogGroupeActivity : AppCompatActivity(), CatalogGroupContract.View {
    companion object {
        const val TAG = "CatalogGroupeActivity"
    }

    val mPresenter : CatalogGroupContract.Presenter by inject { parametersOf(this) }

    private var num: Int = 0
    lateinit var adapter: GroupAdapter
    private var recipesForRV: ArrayList<RecipeModelForRVGroup> = ArrayList()
    private lateinit var articlesViewModel: ArticlesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog_grope)

        num = intent.getIntExtra("pos", 0)

        articlesViewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
        articlesViewModel.allArticles.observe(this, Observer { articles ->
            articles?.let {
                mPresenter.fillDataFromDB(it, num)
            }
        })

        navigation.selectedItemId = R.id.navigation_catalog


        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> startActivity(Intent(this@CatalogGroupeActivity, MainActivity::class.java))
                R.id.navigation_catalog -> startActivity(Intent(this@CatalogGroupeActivity, CatalogActivity::class.java))
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun setData(imageGroup: Drawable, text: String) {
        image.setImageDrawable(imageGroup)
        tvGroup.text = text
    }

    override fun updateRV(recipesForRV: ArrayList<RecipeModelForRVGroup>) {
        this.recipesForRV = recipesForRV
        recyclerViewCatalogSecond.layoutManager = LinearLayoutManager(this)

        adapter = GroupAdapter(recipesForRV) {
            Log.d(TAG, "clicked at : $it")
            startActivity(
                Intent(
                    this@CatalogGroupeActivity,
                    RecipeActivity::class.java
                ).putExtra("groupNum", num).putExtra("recipeNum", it)
            )
        }

        recyclerViewCatalogSecond.adapter = adapter
    }
}
package com.beautylab.rxt.mvp.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.beautylab.rxt.R
import com.beautylab.rxt.RecipeActivity
import com.beautylab.rxt.adapter.GroupAdapter
import com.beautylab.rxt.database.ArticlesViewModel
import com.beautylab.rxt.di.CatalogGroupContract
import com.beautylab.rxt.models.RecipeModelForRVGroup
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
//    private var numImage: Int = 0
    lateinit var adapter: GroupAdapter
//    private var mArticles = ArrayList<ArticlesModel>()
//    private var mRecipes = ArrayList<RecipesModel>()
    private var recipesForRV: ArrayList<RecipeModelForRVGroup> = ArrayList()
//    private lateinit var currentDraweblesList: IntArray
//    private val draweblesHair = intArrayOf(
//        R.drawable.hair_01,
//        R.drawable.hair_02,
//        R.drawable.hair_03,
//        R.drawable.hair_04,
//        R.drawable.hair_05
//    )
//
//    private val draweblesBody = intArrayOf(
//        R.drawable.body_01,
//        R.drawable.body_02,
//        R.drawable.body_03,
//        R.drawable.body_04,
//        R.drawable.body_05,
//        R.drawable.body_06,
//        R.drawable.body_07,
//        R.drawable.body_08
//    )
//
//    private val draweblesFace = intArrayOf(
//        R.drawable.face_01,
//        R.drawable.face_02,
//        R.drawable.face_03,
//        R.drawable.face_04,
//        R.drawable.face_05
//    )

    private lateinit var articlesViewModel: ArticlesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog_grope)

        articlesViewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
        articlesViewModel.allArticles.observe(this, Observer { articles ->
            articles?.let {
                mPresenter.fillDataFromDB(it, num)
//                mArticles.addAll(it)
//                mRecipes.addAll(mArticles[num].articles)
//                var numImageOld = 0
//                mRecipes.forEach {
//                    numImage = (0..4).random()
//                    while (numImage == numImageOld) {
//                        numImage = (0..4).random()
//                        Log.d(TAG, "NumImage: " + numImage + ". NumImageOld: " + numImageOld)
//                    }
//                    numImageOld = numImage
//                    var recipeModelForRVGroup = RecipeModelForRVGroup(
//                        ContextCompat.getDrawable(
//                            this,
//                            currentDraweblesList[numImage]
//                        ), it.name, it.rate, it.description
//                    )
//                    recipesForRV.add(recipeModelForRVGroup)
//                }
//                updateRV()
            }
        })

        navigation.selectedItemId = R.id.navigation_catalog
        num = intent.getIntExtra("pos", 0)

//        when (num) {
//            0 -> {
//                currentDraweblesList = draweblesHair
//                image.setImageDrawable(ContextCompat.getDrawable(this,
//                    R.drawable.group_hair
//                ))
//                tvGroup.text = resources.getStringArray(R.array.group_main_catalog)[0]
//            }
//
//            1 -> {
//                currentDraweblesList = draweblesBody
//                image.setImageDrawable(ContextCompat.getDrawable(this,
//                    R.drawable.group_body
//                ))
//                tvGroup.text = resources.getStringArray(R.array.group_main_catalog)[1]
//            }
//
//            3 -> {
//                currentDraweblesList = draweblesBody
//                image.setImageDrawable(ContextCompat.getDrawable(this,
//                    R.drawable.group_body
//                ))
//                tvGroup.text = resources.getStringArray(R.array.group_main_catalog)[2]
//            }
//
//            4 -> {
//                currentDraweblesList = draweblesFace
//                image.setImageDrawable(ContextCompat.getDrawable(this,
//                    R.drawable.group_face
//                ))
//                tvGroup.text = resources.getStringArray(R.array.group_main_catalog)[3]
//            }
//        }



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
        //adapter.notifyDataSetChanged()
    }
}
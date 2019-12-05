package com.beautylab.rxt

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.beautylab.rxt.adapter.ComponentsAdapter
import com.beautylab.rxt.models.ComponentsModelForRV
import com.beautylab.rxt.models.RecipesModel
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.activity_recipe.image
import kotlinx.android.synthetic.main.activity_recipe.navigation
import java.lang.StringBuilder
import java.util.*
import androidx.lifecycle.ViewModelProvider
import com.beautylab.rxt.database.ArticlesViewModel
import com.beautylab.rxt.mvp.view.CatalogActivity
import com.beautylab.rxt.mvp.view.MainActivity


class RecipeActivity : AppCompatActivity() {
    companion object {
        const val TAG = "RecipeActivity"
    }

    private var num: Int = 0
    private var numRecipe: Int = 0
    private var numImage: Int = 0
    lateinit var adapter: ComponentsAdapter
    private var mRecipes = RecipesModel()
    private lateinit var currentDrawList: IntArray
    val componentsForRV: ArrayList<ComponentsModelForRV> = ArrayList()

    private val draweblesHair = intArrayOf(
        R.drawable.recipe_hair_01,
        R.drawable.recipe_hair_02,
        R.drawable.recipe_hair_03
    )

    private val draweblesBody = intArrayOf(
        R.drawable.recipe_body_01,
        R.drawable.recipe_body_02,
        R.drawable.recipe_body_03,
        R.drawable.recipe_body_04,
        R.drawable.recipe_body_05,
        R.drawable.recipe_body_06,
        R.drawable.recipe_body_07,
        R.drawable.recipe_body_08
    )

    private val draweblesFace = intArrayOf(
        R.drawable.recipe_face_01,
        R.drawable.recipe_face_02,
        R.drawable.recipe_face_03,
        R.drawable.recipe_face_04,
        R.drawable.recipe_face_05,
        R.drawable.recipe_face_06
    )

    private lateinit var articlesViewModel: ArticlesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        num = intent.getIntExtra("groupNum", 0)
        numRecipe = intent.getIntExtra("recipeNum", 0)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            finish()
        }

        when (num) {
            0 -> currentDrawList = draweblesHair
            1, 3 -> currentDrawList = draweblesBody
            4 -> currentDrawList = draweblesFace
        }

        articlesViewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
        articlesViewModel.allArticles.observe(this, Observer { articles ->
                        articles?.let { mRecipes = it[num].articles[numRecipe]
                            numImage = (0..currentDrawList.size-1).random()
                            image.setImageDrawable(
                                ContextCompat.getDrawable(
                                    this,
                                    currentDrawList[numImage]
                                )
                            )

                            ratingBar.visibility = View.VISIBLE

                            image.alpha = 1.0f

                            tvNameCard.text = mRecipes.name
                            ratingBar.rating = mRecipes.rate.toFloat()
                            tvDescription.text = mRecipes.description
                            tvUsage.text = mRecipes.instructions

                            val components = mRecipes.components
                            components?.forEach {
                                componentsForRV.add(ComponentsModelForRV(it.key, it.value))
                            }

                            val stringBuilder = StringBuilder()
                            var stepNum = 0
                            mRecipes.steps.forEach {
                                if (stepNum < mRecipes.steps.size - 1) {
                                    Log.d(TAG, "StepNum + n" + stepNum)
                                    ++stepNum
                                    stringBuilder.append(stepNum.toString()).append(". ").append(it)
                                        .append("\n")
                                } else {
                                    Log.d(TAG, "StepNum" + stepNum)
                                    ++stepNum
                                    stringBuilder.append(stepNum.toString()).append(". ").append(it)

                                }
                            }

                            tvPrepare.text = stringBuilder
                            updateRV() }})

        val layoutManager = LinearLayoutManager(this)
        rvRecipe.layoutManager = layoutManager

        adapter = ComponentsAdapter(componentsForRV)
        rvRecipe.adapter = adapter

        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> startActivity(
                    Intent(
                        this@RecipeActivity,
                        MainActivity::class.java
                    )
                )
                R.id.navigation_catalog -> startActivity(
                    Intent(
                        this@RecipeActivity,
                        CatalogActivity::class.java
                    )
                )
            }
            return@setOnNavigationItemSelectedListener true
        }

        startAnimationAlpha(image, 0)
        startAnimationAlpha(card, 0)
        startAnimationTranslation(fab, 0)
        startAnimationTranslation(llComponents, 0)
        startAnimationAlpha(rvRecipe, 300)
        startAnimationTranslation(llPrepare, 300)
        startAnimationAlpha(tvPrepare, 600)
        startAnimationTranslation(llUsage, 600)
        startAnimationAlpha(tvUsage, 900)

//        nested_content.setOnScrollChangeListener(
//            NestedScrollView.OnScrollChangeListener {
//                    _, scrollX, scrollY, _, oldScrollY ->
//
//                if(scrollY > oldScrollY){
//                    Log.d(TAG, "Scroll down")
//                    startAnimationTranslation(navigation, 0)
//                }
//            })
    }

    private fun startAnimationTranslation(view: View?, delay: Long) {
        val objectAnimator = ObjectAnimator.ofFloat(view, "translationX", -500f, 0f)
        objectAnimator.interpolator = AccelerateInterpolator(1.5f)
        objectAnimator.duration = 500
        objectAnimator.startDelay = delay
        objectAnimator.start()
        objectAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationStart(animation: Animator) {
                when (view!!.id) {
                    R.id.llPrepare -> llPrepare.visibility = View.VISIBLE
                    R.id.llUsage -> llUsage.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun startAnimationAlpha(view: View?, delay: Long) {
        val objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f)
        objectAnimator.duration = 500
        objectAnimator.startDelay = delay
        objectAnimator.interpolator = AccelerateInterpolator()
        objectAnimator.start()
        objectAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationStart(animation: Animator) {
                when (view!!.id){
                    R.id.rvRecipe -> rvRecipe.visibility = View.VISIBLE
                    R.id.tvPrepare -> tvPrepare.visibility = View.VISIBLE
                    R.id.tvUsage -> tvUsage.visibility = View.VISIBLE
                }
            }
        })
    }


    private fun updateRV() {
        adapter.notifyDataSetChanged()
    }
}
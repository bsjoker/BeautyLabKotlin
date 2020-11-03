package com.beauty.lab.mvp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.beauty.lab.R
import com.beauty.lab.adapter.CatalogAdapter
import com.beauty.lab.di.CatalogContract
import com.beauty.lab.models.RecipeModelForRV
import kotlinx.android.synthetic.main.activity_catalog.*
import kotlinx.android.synthetic.main.activity_catalog.navigation
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CatalogActivity : AppCompatActivity(), CatalogContract.View {
    companion object {
        const val TAG = "CatalogActivity"
    }

    val mPresenter: CatalogContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        mPresenter.fillDataForReciclerView()

        navigation.selectedItemId = R.id.navigation_catalog
        navigation.setOnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.navigation_home) {
                startActivity(Intent(this@CatalogActivity, MainActivity::class.java))
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun setDataInRV(groupsForRV: ArrayList<RecipeModelForRV>) {
        recyclerViewCatalogMain.layoutManager = LinearLayoutManager(this)
        recyclerViewCatalogMain.adapter = CatalogAdapter(groupsForRV) {
            Log.d(TAG, "clicked at : $it")
            mPresenter.clickToItem(it)
        }
    }

    override fun startActivity(pos: Int) {
        if(pos>1){
            startActivity(Intent(this@CatalogActivity, CatalogGroupeActivity::class.java).putExtra("pos", pos+1))
        } else {
            startActivity(Intent(this@CatalogActivity, CatalogGroupeActivity::class.java).putExtra("pos", pos))
        }
    }
}
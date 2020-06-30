package com.beauty.lab.di

import com.beauty.lab.mvp.presenter.CatalogGroupPresenter
import com.beauty.lab.mvp.presenter.CatalogPresenter
import com.beauty.lab.mvp.presenter.LoadingPresenter
import com.beauty.lab.mvp.presenter.MainPresenter
import org.koin.core.module.Module
import org.koin.dsl.module

var loadingModule: Module = module {
    factory<LoadingContract.Presenter> { (view: LoadingContract.View) ->
        LoadingPresenter(view)
    }
}

var mainModule: Module = module {
    factory<MainContract.Presenter> { (view: MainContract.View) ->
        MainPresenter(view)
    }
}

var catalogModule: Module = module {
    factory<CatalogContract.Presenter> { (view: CatalogContract.View) ->
        CatalogPresenter(view)
    }
}

var catalogGroupModule: Module = module {
    factory<CatalogGroupContract.Presenter> { (view: CatalogGroupContract.View) ->
        CatalogGroupPresenter(view)
    }
}

object KoinModule {
    fun loadModule() = listOf(loadingModule, mainModule, catalogModule, catalogGroupModule)
}

package com.gemini.myapplication.presentation.presenter

import com.gemini.myapplication.domain.interactor.HomeInteractor
import com.gemini.myapplication.domain.viewstate.HomeViewState
import com.gemini.myapplication.presentation.view.HomeView
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomePresenter @Inject constructor(val interactor: HomeInteractor) :MviBasePresenter<HomeView,HomeViewState>() {

    override fun bindIntents() {
        val homeViewState: Observable<HomeViewState> = intent (HomeView::buttonClickIntent)
            .subscribeOn(Schedulers.io())
            .debounce (50,TimeUnit.MILLISECONDS)
            .switchMap { interactor.getText() }
            .observeOn(AndroidSchedulers.mainThread())
        subscribeViewState(homeViewState,HomeView::render)
    }
}
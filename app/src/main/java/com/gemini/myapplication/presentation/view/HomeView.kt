package com.gemini.myapplication.presentation.view

import com.gemini.myapplication.domain.viewstate.HomeViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface HomeView:MvpView {
    fun buttonClickIntent(): Observable<Unit>
    fun render(state: HomeViewState)
}
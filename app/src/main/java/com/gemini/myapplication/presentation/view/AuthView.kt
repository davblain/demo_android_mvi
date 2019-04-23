package com.gemini.myapplication.presentation.view

import com.gemini.myapplication.domain.viewstate.auth.AuthViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface AuthView: MvpView {
    fun render (state: AuthViewState)
    fun emailTextChangeIntent():Observable<CharSequence>
    fun passwordTextChangeIntent():Observable<CharSequence>
    fun errorShownIntent():Observable<Unit>
    fun clickIntent():Observable<Unit>
}
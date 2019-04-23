package com.gemini.myapplication.presentation.presenter

import com.gemini.myapplication.domain.interactor.AuthInteractor
import com.gemini.myapplication.domain.viewstate.auth.AuthViewState
import com.gemini.myapplication.presentation.view.AuthView
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class AuthPresenter @Inject constructor(private val interactor: AuthInteractor) :
    MviBasePresenter<AuthView, AuthViewState>() {


    override fun bindIntents() {
        val emailChangeIntent = intent(AuthView::emailTextChangeIntent)
            .flatMap { interactor.emailTyped(it.toString()) }
            .observeOn(AndroidSchedulers.mainThread())
        val passwordChangeIntent = intent(AuthView::passwordTextChangeIntent)
            .flatMap { interactor.passwordTyped(it.toString()) }
            .observeOn(AndroidSchedulers.mainThread())
        val buttonIntent = intent(AuthView::clickIntent)
            .concatMap { interactor.login() }
            .observeOn(AndroidSchedulers.mainThread())
        val errorShownIntent = intent(AuthView::errorShownIntent)
            .flatMap { interactor.errorShow() }
            .observeOn(AndroidSchedulers.mainThread())
        val allIntents: Observable<AuthViewState> = Observable.merge(
            emailChangeIntent,
            passwordChangeIntent,
            errorShownIntent,
            buttonIntent
        )
            .distinctUntilChanged()
            .concatMap { interactor.state }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { Timber.d(it.toString()) }
        subscribeViewState(allIntents, AuthView::render)
    }


}
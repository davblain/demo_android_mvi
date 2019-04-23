package com.gemini.myapplication.presentation.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gemini.myapplication.R
import com.gemini.myapplication.di.DI
import com.gemini.myapplication.domain.viewstate.auth.AuthViewState
import com.gemini.myapplication.presentation.presenter.AuthPresenter
import com.gemini.myapplication.presentation.view.AuthView
import com.hannesdorfmann.mosby3.MviController
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.auth_view.view.*
import toothpick.Toothpick

class AuthController: MviController<AuthView,AuthPresenter>(),AuthView {

    private val errorShown = BehaviorSubject.create<Unit>()
    override fun createPresenter(): AuthPresenter {
        return Toothpick.openScope(DI.APP_SCOPE).getInstance(AuthPresenter::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View { return inflater.inflate(R.layout.auth_view,container,false)
    }

    override fun render(state: AuthViewState) {
        view?.apply {
            authButton.isEnabled = state.isCorrectEmail && state.isCorrectPassword
            if (state.isLoading) {
                progress_bar.visibility = View.VISIBLE
            } else {
                progress_bar.visibility = View.GONE
            }
            if (state.error != null) {
                    Toast.makeText(context, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show()
                    errorShown.onNext(Unit)
            }
        }
    }

    override fun emailTextChangeIntent(): Observable<CharSequence> {
       return view!!.run {
           emailEditText.textChanges()
       }
    }

    override fun passwordTextChangeIntent(): Observable<CharSequence> {
        return view!!.run {
            passwordEditText.textChanges()
        }
    }

    override fun clickIntent(): Observable<Unit> {
        return  view!!.run {
            authButton.clicks()
        }
    }

    override fun errorShownIntent(): Observable<Unit> {
        return  errorShown.hide()
    }
}
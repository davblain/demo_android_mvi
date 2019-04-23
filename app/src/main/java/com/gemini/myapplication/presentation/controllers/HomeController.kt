package com.gemini.myapplication.presentation.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gemini.myapplication.R
import com.gemini.myapplication.di.DI
import com.gemini.myapplication.domain.viewstate.HomeViewState
import com.gemini.myapplication.presentation.presenter.HomePresenter
import com.gemini.myapplication.presentation.view.HomeView
import com.hannesdorfmann.mosby3.MviController
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.home_view.view.*
import toothpick.Toothpick

class HomeController : MviController<HomeView, HomePresenter>(), HomeView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.home_view, container, false)
    }

    override fun createPresenter(): HomePresenter {
        return Toothpick.openScope(DI.APP_SCOPE).getInstance(HomePresenter::class.java)
    }

    override fun buttonClickIntent(): Observable<Unit> {
        return view!!.run {
            button.clicks()
        }
    }

    override fun render(state: HomeViewState) {
        when (state) {
            is HomeViewState.DataState -> {
                renderText(state.text)
            }
            is HomeViewState.LoadingState -> {
                renderLoading()
            }
            is HomeViewState.ErrorViewState -> {
                showErrorState(state)
            }
        }
    }

    private fun renderLoading() {
        view?.apply {
            progress_bar.visibility = View.VISIBLE
        }
    }
    private fun renderText(text: String) {
        view?.apply {
            progress_bar.visibility = View.GONE
            textView.text = text
        }
    }
    private fun showErrorState(state: HomeViewState.ErrorViewState) {
        view?.apply {
            progress_bar.visibility = View.GONE
        }
    }
}
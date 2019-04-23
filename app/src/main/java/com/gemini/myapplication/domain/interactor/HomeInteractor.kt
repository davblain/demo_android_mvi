package com.gemini.myapplication.domain.interactor

import com.gemini.myapplication.domain.viewstate.HomeViewState
import io.reactivex.Observable


class HomeInteractor {

    fun getText(): Observable<HomeViewState> =
        Observable.just("Test")
            .map<HomeViewState>(HomeViewState::DataState)
            .startWith(HomeViewState.LoadingState)
            .onErrorReturn(HomeViewState::ErrorViewState)
}
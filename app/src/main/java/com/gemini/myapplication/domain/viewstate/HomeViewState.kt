package com.gemini.myapplication.domain.viewstate

sealed class HomeViewState {
    object LoadingState:HomeViewState()
    data class DataState (val text:String): HomeViewState()
    data class ErrorViewState(val t:Throwable): HomeViewState()
}
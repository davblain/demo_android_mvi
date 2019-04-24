package com.gemini.myapplication.domain.viewstate.auth

sealed class PartialAuthState {
    data class LoadingState(val isLoading: Boolean) : PartialAuthState()
    data class EmailCorrectState(val valid:Boolean,val email: String) : PartialAuthState()
    object TryLoggedInState : PartialAuthState()
    data class ErrorState(val t:Throwable): PartialAuthState()
    object ErrorStateHandled: PartialAuthState()
    object LoggedInState : PartialAuthState()
    data class PasswordCorrectState(val valid:Boolean,val password: String) : PartialAuthState()
}
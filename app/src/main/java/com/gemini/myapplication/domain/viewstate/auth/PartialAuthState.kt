package com.gemini.myapplication.domain.viewstate.auth

sealed class PartialAuthState {
    data class LoadingState(val isLoading: Boolean) : PartialAuthState()
    data class EmailCorrectState(val email: String) : PartialAuthState()
    object TryLoggedInState : PartialAuthState()
    data class ErrorState(val t:Throwable): PartialAuthState()
    object ErrorStateHandled: PartialAuthState()
    object LoggedInState : PartialAuthState()
    data class EmailIncorrectState(val email: String) : PartialAuthState()
    data class PasswordCorrectState(val password: String) : PartialAuthState()
    data class PasswordIncorrectState(val password: String) : PartialAuthState()
}
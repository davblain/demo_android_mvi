package com.gemini.myapplication.domain.viewstate.auth

data class AuthViewState (val isLoading: Boolean = false,
                          val isCorrectEmail: Boolean = false,
                          val email: String? = null,
                          val password: String? = null,
                          val isLoggedIn:Boolean = false,
                          val isCorrectPassword: Boolean = false,
                          val error:Throwable? = null)

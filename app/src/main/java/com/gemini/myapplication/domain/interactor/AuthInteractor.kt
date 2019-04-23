package com.gemini.myapplication.domain.interactor

import com.gemini.myapplication.data.AuthRepository
import com.gemini.myapplication.domain.validator.EmailValidator
import com.gemini.myapplication.domain.validator.PasswordValidator
import com.gemini.myapplication.domain.viewstate.auth.AuthViewState
import com.gemini.myapplication.domain.viewstate.auth.PartialAuthState
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

class AuthInteractor @Inject constructor(private val repository: AuthRepository) {

    private val partialStates: BehaviorSubject<PartialAuthState> = BehaviorSubject.create()

    private val viewStateReducer = BiFunction { oldState: AuthViewState, partialState: PartialAuthState ->
        when (partialState) {
            is PartialAuthState.EmailCorrectState -> oldState.copy(isCorrectEmail = true, email = partialState.email)
            is PartialAuthState.EmailIncorrectState -> oldState.copy(
                isCorrectPassword = false,
                email = partialState.email
            )
            is PartialAuthState.PasswordCorrectState -> oldState.copy(
                isCorrectPassword = true,
                password = partialState.password
            )
            is PartialAuthState.PasswordIncorrectState -> oldState.copy(
                isCorrectPassword = false,
                password = partialState.password
            )
            is PartialAuthState.LoadingState -> oldState.copy(isLoading = partialState.isLoading)
            is PartialAuthState.TryLoggedInState -> oldState.copy(isLoading = true)
            is PartialAuthState.LoggedInState -> oldState.copy(isLoggedIn = true, isLoading = false)
            is PartialAuthState.ErrorState -> oldState.copy(error = partialState.t, isLoading = false)
            is PartialAuthState.ErrorStateHandled -> oldState.copy(error = null)
        }

    }

    val state: Observable<AuthViewState> = partialStates.distinctUntilChanged().doOnNext {
        Timber.d(it.toString())
    }.scan(AuthViewState(false), viewStateReducer)


    fun passwordTyped(password: String): Observable<out PartialAuthState> {
        return Observable.just(password).map {
            if (PasswordValidator.isValid(it)) {
                return@map PartialAuthState.PasswordCorrectState(it)
            } else {
                return@map PartialAuthState.PasswordIncorrectState(it)
            }
        }.toState()
    }

    fun emailTyped(email: String): Observable<out PartialAuthState> {
        return Observable.just(email).map {
            if (EmailValidator.isValid(it)) {
                return@map PartialAuthState.EmailCorrectState(it)
            } else {
                return@map PartialAuthState.EmailIncorrectState(it)
            }
        }.toState()
    }

    fun login(): Observable<out PartialAuthState> {
        return state.flatMap { repository.login(it.email, it.password).toObservable() }
            .map<PartialAuthState> { PartialAuthState.LoggedInState }
            .startWith(PartialAuthState.TryLoggedInState)
            .onErrorReturn { PartialAuthState.ErrorState(it) }
            .toState()

    }

    fun errorShow(): Observable<out PartialAuthState> {
        return Observable.just(PartialAuthState.ErrorStateHandled).toState()
    }

    private fun Observable<out PartialAuthState>.toState(): Observable<out PartialAuthState> {
        return this.doOnNext {
            partialStates.onNext(it)
        }
    }


}
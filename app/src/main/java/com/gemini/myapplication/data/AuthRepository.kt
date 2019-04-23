package com.gemini.myapplication.data

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepository @Inject constructor(){

    fun login(username:String?,password:String?): Single<String> {
        return Single.just("Success").delay(9000, TimeUnit.MILLISECONDS)
    }
}
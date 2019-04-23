package com.gemini.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.gemini.myapplication.presentation.controllers.AuthController
import com.gemini.myapplication.presentation.controllers.HomeController
import kotlinx.android.synthetic.main.flow_container.*

class MainActivity: AppCompatActivity() {

    private lateinit var router:Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flow_container)
        router = Conductor.attachRouter(this,flow_container,savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(AuthController()))
        }
    }
}
package com.test.whatsappstatusdowloader

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {

    private var DURATION:Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        goToMainActivity()
    }

    private fun goToMainActivity() {

        Timer().schedule(timerTask {

            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, DURATION)
    }
}
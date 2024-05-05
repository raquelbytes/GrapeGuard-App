package com.raquelbytes.grapeguard.Controller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.raquelbytes.grapeguard.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loggin_view)

        val loginButton = findViewById<Button>(R.id.loginButton)

    }
}

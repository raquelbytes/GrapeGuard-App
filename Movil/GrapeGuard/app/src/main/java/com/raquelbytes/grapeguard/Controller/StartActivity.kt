package com.raquelbytes.grapeguard.Controller

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.raquelbytes.grapeguard.R

class StartActivity : AppCompatActivity() {

        private lateinit var sharedPreferences: SharedPreferences

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.loggin_view)

                sharedPreferences = applicationContext.getSharedPreferences("preferencias", Context.MODE_PRIVATE)

                val usuario = sharedPreferences.getString("Usuario", "")

                if (!usuario.isNullOrEmpty()) {
                        val intentMainActivity = Intent(this, MainActivity::class.java)
                        startActivity(intentMainActivity)
                        finish()
                }

                val btnLogin = findViewById<Button>(R.id.loginButton)
                val btnRegister = findViewById<Button>(R.id.registerButton)

                btnLogin.setOnClickListener {
                        startActivity(Intent(this, LoginActivity::class.java))
                }

                btnRegister.setOnClickListener {
                        startActivity(Intent(this, RegisterActivity::class.java))
                }
        }
}

package com.raquelbytes.grapeguard.Controller

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.raquelbytes.grapeguard.R
import com.raquelbytes.grapeguard.Util.LocaleUtil

// Clase para la actividad de inicio
class StartActivity : AppCompatActivity() {

        // Variable para almacenar las preferencias compartidas
        private lateinit var sharedPreferences: SharedPreferences

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                LocaleUtil.loadLocale(this);
                setContentView(R.layout.loggin_view) // Establece el diseño de la actividad de inicio

                // Inicializa las preferencias compartidas
                sharedPreferences = applicationContext.getSharedPreferences("preferencias", Context.MODE_PRIVATE)

                // Obtiene el usuario almacenado en las preferencias compartidas
                val usuario = sharedPreferences.getString("Usuario", "")

                // Verifica si hay un usuario almacenado y lo redirige a la actividad principal
                if (!usuario.isNullOrEmpty()) {
                        val intentMainActivity = Intent(this, MainActivity::class.java)
                        startActivity(intentMainActivity)
                        finish()
                }

                // Obtiene las referencias a los botones de login y registro
                val btnLogin = findViewById<Button>(R.id.loginButton)
                val btnRegister = findViewById<Button>(R.id.registerButton)

                // Configura el listener para el botón de login
                btnLogin.setOnClickListener {
                        startActivity(Intent(this, LoginActivity::class.java)) // Inicia la actividad de login
                }

                // Configura el listener para el botón de registro
                btnRegister.setOnClickListener {
                        startActivity(Intent(this, RegisterActivity::class.java)) // Inicia la actividad de registro
                }
        }
}

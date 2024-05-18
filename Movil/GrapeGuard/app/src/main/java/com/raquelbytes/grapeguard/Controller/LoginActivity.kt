package com.raquelbytes.grapeguard.Controller

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.raquelbytes.grapeguard.API.Interface.UserLoginCallback
import com.raquelbytes.grapeguard.API.Model.Usuario
import com.raquelbytes.grapeguard.API.Repository.UsuarioRepository
import com.raquelbytes.grapeguard.Controller.MainActivity
import com.raquelbytes.grapeguard.R
import com.raquelbytes.grapeguard.Util.EncryptionUtil

class LoginActivity : AppCompatActivity(), UserLoginCallback {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var checkBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logginwithemail_view)

        sharedPreferences = applicationContext.getSharedPreferences("preferencias", MODE_PRIVATE)

        val email = findViewById<EditText>(R.id.emailEditText)
        val contrasena = findViewById<EditText>(R.id.passwordEditText)
        val btnLogin = findViewById<Button>(R.id.loginButton)
        checkBox = findViewById<CheckBox>(R.id.recordarmeCheckBox)

        btnLogin.setOnClickListener {
            UsuarioRepository.loginUsuario(
                applicationContext,
                email.text.toString().trim(),
                contrasena.text.toString().trim(),
                this
            )
        }
    }

    private val someActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Aquí puedes manejar el resultado de la actividad si es necesario
    }

    override fun onLoginSuccess(usuario: Usuario) {
        try {
            if (checkBox.isChecked) {
                // GUARDAR EL USUARIO EN LAS SHARED PREFERENCES
                val editor = sharedPreferences.edit()
                val usuarioEncriptado = EncryptionUtil.encriptar(
                    EncryptionUtil.transformarUsuarioToJson(usuario),
                    "admintest123"
                )
                editor.putString("Usuario", usuarioEncriptado)
                editor.apply()
            }
        } catch (ex: Exception) {
            Log.d("ERROR", ex.message ?: "")
        }

        // NOTIFICAR AL USUARIO DE LA BIENVENIDA
        Toast.makeText(this, "¡Bienvenido a GrapeGuard!", Toast.LENGTH_LONG).show()

        // PASAR A LA PANTALLA DE MESAS
        val intentMainActivity = Intent(applicationContext, MainActivity::class.java)
        intentMainActivity.putExtra("id_usuario", usuario.id_usuario)
        someActivityResultLauncher.launch(intentMainActivity)
    }

    override fun onLoginError(message: String) {
        if (message.contains("Usuario o contraseña incorrectos")) {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Error Inesperado", Toast.LENGTH_LONG).show()
        }
    }
}

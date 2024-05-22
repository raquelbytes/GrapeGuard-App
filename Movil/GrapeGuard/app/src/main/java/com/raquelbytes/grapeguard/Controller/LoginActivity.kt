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
import com.raquelbytes.grapeguard.R

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
        //val btnLogout = findViewById<Button>(R.id.logoutButton) // Button to redirect to LogOutActivity

        btnLogin.setOnClickListener {
            UsuarioRepository.loginUsuario(
                applicationContext,
                email.text.toString().trim(),
                contrasena.text.toString().trim(),
                this
            )
        }

    /*    btnLogout.setOnClickListener {
            val intentLogOutActivity = Intent(this, LogOutActivity::class.java)
            startActivity(intentLogOutActivity)
        } */
    }

    private val someActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

    }

    override fun onLoginSuccess(usuario: Usuario) {
        try {
            if (checkBox.isChecked) {
                // Guardar el usuario en las SharedPreferences
                val editor = sharedPreferences.edit()
                val usuarioEncriptado = EncryptionUtil.encriptar(
                    EncryptionUtil.transformarUsuarioToJson(usuario),
                    "ejemploadmin123"
                )
                editor.putString("Usuario", usuarioEncriptado)
                editor.putInt("id_usuario", usuario.id_usuario ?: -1)
                editor.putString("nombre", usuario.nombre)
                editor.putString("apellido", usuario.apellido)
                editor.putString("email", usuario.email)
                editor.putString("contrasena", usuario.contrasena)
                editor.putString("foto", usuario.foto)
                editor.apply()
            }
        } catch (ex: Exception) {
            Log.d("ERROR", ex.message ?: "")
        }

        Toast.makeText(this, "¡Bienvenido a GrapeGuard!", Toast.LENGTH_LONG).show()

        // Pasar a la pantalla Main
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

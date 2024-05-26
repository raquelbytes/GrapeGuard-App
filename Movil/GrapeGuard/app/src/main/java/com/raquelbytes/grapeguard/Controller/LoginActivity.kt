package com.raquelbytes.grapeguard.Controller

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.raquelbytes.grapeguard.API.Interface.UserLoginCallback
import com.raquelbytes.grapeguard.API.Model.Usuario
import com.raquelbytes.grapeguard.API.Repository.UsuarioRepository
import com.raquelbytes.grapeguard.R
import com.raquelbytes.grapeguard.SQLite.UsuarioDAO
import com.raquelbytes.grapeguard.SQLite.UsuarioDbHelper

// Actividad para iniciar sesión
class LoginActivity : AppCompatActivity(), UserLoginCallback {

    // Variables de clase
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var checkBox: CheckBox
    private lateinit var dbHelper: UsuarioDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logginwithemail_view) // Establece el diseño de la actividad

        // Inicialización de variables
        sharedPreferences = applicationContext.getSharedPreferences("preferencias", MODE_PRIVATE)
        dbHelper = UsuarioDbHelper(this)

        // Vistas de la interfaz de usuario
        val email = findViewById<EditText>(R.id.emailEditText)
        val contrasena = findViewById<EditText>(R.id.passwordEditText)
        val btnLogin = findViewById<Button>(R.id.loginButton)
        checkBox = findViewById<CheckBox>(R.id.recordarmeCheckBox)

        // Listener para el botón de inicio de sesión
        btnLogin.setOnClickListener {
            // Realiza la llamada al método de inicio de sesión del repositorio de usuario
            UsuarioRepository.loginUsuario(
                applicationContext,
                email.text.toString().trim(),
                contrasena.text.toString().trim(),
                this // Proporciona esta actividad como callback para manejar el resultado
            )
        }

    }


    // Callback que se ejecuta cuando el inicio de sesión es exitoso
    override fun onLoginSuccess(usuario: Usuario) {
        try {
            // Si el usuario ha marcado la casilla "Recordarme"
            if (checkBox.isChecked) {
                // Guarda la información del usuario en SharedPreferences
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
                usuario.foto?.let { Log.d("foto", it) }
                editor.apply()
                Log.d("usuario", usuarioEncriptado)
            }
        } catch (ex: Exception) {
            Log.d("ERROR", ex.message ?: "")
        }

        // Guarda la información del usuario en la base de datos SQLite
        val db = dbHelper.writableDatabase
        val usuarioDAO = UsuarioDAO(this)

        usuario.id_usuario?.let {
            usuario.nombre?.let { it1 ->
                usuario.apellido?.let { it2 ->
                    usuario.foto?.let { it3 ->
                        usuarioDAO.insertarUsuario(db,
                            it, it1, it2, it3
                        )
                    }
                }
            }
        }

        // Muestra un mensaje de bienvenida al usuario
        Toast.makeText(this, getString(R.string.toast_bienvenido_grapeguard), Toast.LENGTH_LONG).show()

        // Inicia la actividad principal
        val intentMainActivity = Intent(applicationContext, MainActivity::class.java)
        intentMainActivity.putExtra("id_usuario",usuario.id_usuario)
        someActivityResultLauncher.launch(intentMainActivity)
        finish()
    }
    private val someActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

    }

    // Callback que se ejecuta cuando ocurre un error durante el inicio de sesión
    override fun onLoginError(message: String) {
        // Muestra un mensaje de error según el tipo de error
        if (message.contains("Usuario o contraseña incorrectos")) {
            Toast.makeText(this, getString(R.string.toast_bienvenido_grapeguard), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, getString(R.string.toast_bienvenido_grapeguard), Toast.LENGTH_LONG).show()
        }
    }
}

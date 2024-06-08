package com.raquelbytes.grapeguard.Controller

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.raquelbytes.grapeguard.API.Interface.UserRegisterCallback
import com.raquelbytes.grapeguard.API.Model.Usuario
import com.raquelbytes.grapeguard.API.Repository.UsuarioRepository
import com.raquelbytes.grapeguard.R
import com.raquelbytes.grapeguard.Util.ImageHelper;

// Clase para la actividad de registro de usuario
class RegisterActivity : AppCompatActivity(), UserRegisterCallback {

    // Declaración de variables de vistas
    private lateinit var email: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_view) // Establece el diseño de la actividad de registro

        // Obtiene las referencias a los elementos de la interfaz de usuario
        val nombre = findViewById<EditText>(R.id.nombreEditText)
        val apellidos = findViewById<EditText>(R.id.apellidosEditText)
        email = findViewById<EditText>(R.id.emailEditText)
        val contrasena = findViewById<EditText>(R.id.passwordEditText)
        val fotoPerfil = findViewById<ImageView>(R.id.fotoPerfil)
        val btnFoto = findViewById<Button>(R.id.btnSacarFoto)
        val btnRegistro = findViewById<Button>(R.id.loginButton)
        val editTexts = listOf(nombre, apellidos, email, contrasena)

        // Configura el listener para el botón de tomar foto
        btnFoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
            }
        }

        // Configura el listener para el botón de registro
        btnRegistro.setOnClickListener {
            val errorMessage = StringBuilder()

            // Verifica si algún campo está vacío o no cumple con las validaciones
            if (nombre.text.toString().isEmpty()) {
                errorMessage.append("Nombre no puede estar vacío\n")
            }
            if (apellidos.text.toString().isEmpty()) {
                errorMessage.append("Apellidos no pueden estar vacíos\n")
            }
            if (!isValidEmail(email.text.toString())) {
                errorMessage.append("Correo electrónico no válido\n")
            }
            if (!containsOnlyLetters(nombre.text.toString())) {
                errorMessage.append("Nombre solo puede contener letras\n")
            }
            if (!containsOnlyLetters(apellidos.text.toString())) {
                errorMessage.append("Apellidos solo pueden contener letras\n")
            }
            if (!isValidPassword(contrasena.text.toString())) {
                errorMessage.append("Contraseña debe tener máximo 16 caracteres\n")
            }

            // Si hay errores, muestra un Toast con los mensajes de error
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this, errorMessage.toString().trim(), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Si no hay errores, procede con el registro
            val usuario = Usuario(
                nombre.text.toString().trim(),
                apellidos.text.toString().trim(),
                email.text.toString().trim(),
                contrasena.text.toString().trim(),
                ImageHelper.encodeImageViewToBase64(fotoPerfil)
            )
            UsuarioRepository.registrarUsuario(this, usuario, this)
        }
    }

    // Método para abrir la cámara
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        someActivityResultLauncher.launch(intent)
    }

    // Método para verificar si algún campo de texto está vacío
    private fun comprobarVacio(editTexts: List<EditText>): Boolean {
        for (editText in editTexts) {
            if (editText.text.toString().isEmpty()) {
                editText.setHintTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
                editText.requestFocus()
                return false
            }
        }
        return true
    }

    // Callback para manejar el éxito en el registro de usuario
    override fun onUserRegisterSuccess(response: String) {
        Toast.makeText(this, response, Toast.LENGTH_LONG).show()
        val intentLogin = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)
        finish()
    }

    // Callback para manejar el fracaso en el registro de usuario
    override fun onUserRegisterFailed(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        if (error.contains("El usuario ya existe")) {
            email.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
        }
    }

    // Callback para manejar el resultado de la actividad de la cámara
    private val someActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val extras = result.data!!.extras
            val imageBitmap = extras!!["data"] as Bitmap
            val imageView = findViewById<ImageView>(R.id.fotoPerfil)
            imageView.setImageBitmap(imageBitmap)
        } else {
            Toast.makeText(this, getString(R.string.toast_foto_no_capturada), Toast.LENGTH_SHORT).show()
        }
    }

    // Método para manejar el resultado de la solicitud de permisos
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, getString(R.string.toast_permiso_camara_denegado), Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val MY_CAMERA_PERMISSION_CODE = 100
        private const val REQUEST_TAKE_PHOTO = 101
    }
    // Función para validar el formato de correo electrónico
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }

    // Función para validar que el texto no contenga números
    private fun containsOnlyLetters(text: String): Boolean {
        val letterRegex = "^[a-zA-Z]+\$"
        return text.matches(letterRegex.toRegex())
    }

    // Función para validar la longitud de la contraseña
    private fun isValidPassword(password: String): Boolean {
        return password.length <= 16
    }
}

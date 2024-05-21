package com.raquelbytes.grapeguard.Controller

/*
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
import com.raquelbytes.grapeguard.Util.ImageHelper

class LogOutActivity : AppCompatActivity(), UserRegisterCallback {

    private lateinit var nombre: EditText
    private lateinit var apellidos: EditText
    private lateinit var email: EditText
    private lateinit var contrasena: EditText
    private lateinit var fotoPerfil: ImageView
    private lateinit var currentUser: Usuario
    private var userPhoto: Bitmap? = null

    private val MY_CAMERA_PERMISSION_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logout_view)

        // Initialize views
        nombre = findViewById(R.id.nombreEditText)
        apellidos = findViewById(R.id.apellidosEditText)
        email = findViewById(R.id.emailEditText)
        contrasena = findViewById(R.id.passwordEditText)
        fotoPerfil = findViewById(R.id.fotoPerfil)
        val btnFoto = findViewById<Button>(R.id.btnSacarFoto)
        val btnGuardar = findViewById<Button>(R.id.guardarButton)

        // Simulate fetching current user data from the server
        currentUser = getCurrentUser()

        // Initialize the views with current user data
        nombre.setText(currentUser.nombre)
        apellidos.setText(currentUser.apellidos)
        email.setText(currentUser.email)
        contrasena.setText(currentUser.password)
        fotoPerfil.setImageBitmap(ImageHelper.decodeBase64ToBitmap(currentUser.photoUrl))

        // Set up the button listeners
        btnFoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
            }
        }

        btnGuardar.setOnClickListener {
            updateUser()
        }
    }

    private fun getCurrentUser(): Usuario {

    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        someActivityResultLauncher.launch(intent)
    }

    private fun updateUser() {
        val updatedNombre = nombre.text.toString().ifEmpty { currentUser.nombre }
        val updatedApellidos = apellidos.text.toString().ifEmpty { currentUser.apellidos }
        val updatedEmail = email.text.toString().ifEmpty { currentUser.email }
        val updatedPassword = contrasena.text.toString().ifEmpty { currentUser.password }
        val updatedPhotoUrl = userPhoto?.let { ImageHelper.encodeImageViewToBase64(fotoPerfil) } ?: currentUser.photoUrl

        currentUser = currentUser.copy(
            nombre = updatedNombre,
            apellidos = updatedApellidos,
            email = updatedEmail,
            password = updatedPassword,
            photoUrl = updatedPhotoUrl
        )


        UsuarioRepository.updateUsuario(this, currentUser, this)
    }

    override fun onUserRegisterSuccess(response: String) {
        Toast.makeText(this, "Usuario actualizado con éxito", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onUserRegisterFailed(error: String) {
        Toast.makeText(this, "Error al actualizar usuario: $error", Toast.LENGTH_LONG).show()
        if (error.contains("El usuario ya existe")) {
            email.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
        }
    }

    private val someActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val extras = result.data!!.extras
            val imageBitmap = extras!!["data"] as Bitmap
            userPhoto = imageBitmap
            fotoPerfil.setImageBitmap(imageBitmap)
        } else {
            Toast.makeText(this, "Foto no capturada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_LONG).show()
            }
        }
    }
}
*/
package com.raquelbytes.grapeguard.Controller

import android.Manifest
import android.annotation.SuppressLint
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
import com.raquelbytes.grapeguard.API.Interface.UserUpdateCallback
import com.raquelbytes.grapeguard.API.Model.Usuario
import com.raquelbytes.grapeguard.API.Repository.UsuarioRepository
import com.raquelbytes.grapeguard.R
import com.raquelbytes.grapeguard.Util.ImageHelper

class LogOutActivity : AppCompatActivity(), UserUpdateCallback {

    private lateinit var nombre: EditText
    private lateinit var apellido: EditText
    private lateinit var email: EditText
    private lateinit var contrasena: EditText
    private lateinit var fotoPerfil: ImageView
    private lateinit var currentUser: Usuario
    private var userPhoto: Bitmap? = null

    private val MY_CAMERA_PERMISSION_CODE = 100

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logout_view)

        // Initialize views
        nombre = findViewById(R.id.nombreEditText)
        apellido = findViewById(R.id.apellidosEditText)
        email = findViewById(R.id.emailEditText)
        contrasena = findViewById(R.id.passwordEditText)
        fotoPerfil = findViewById(R.id.fotoPerfil)
        val btnFoto = findViewById<Button>(R.id.btnSacarFoto)
        val btnGuardar = findViewById<Button>(R.id.saveChanges)

        // Fetch current user data
        currentUser = getCurrentUser()

        // Initialize the views with current user data
        nombre.setText(currentUser.nombre)
        apellido.setText(currentUser.apellido)
        email.setText(currentUser.email)
        contrasena.setText(currentUser.contrasena)
        fotoPerfil.setImageBitmap(currentUser.foto?.let { ImageHelper.decodeBase64ToBitmap(it) })

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
        // Try to get the user data from SharedPreferences
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val idUsuario = sharedPreferences.getInt("id_usuario", -1)
        val nombre = sharedPreferences.getString("nombre", null)
        val apellido = sharedPreferences.getString("apellido", null)
        val email = sharedPreferences.getString("email", null)
        val contrasena = sharedPreferences.getString("contrasena", null)
        val foto = sharedPreferences.getString("foto", null)

        return if (idUsuario != -1 && nombre != null && apellido != null && email != null && contrasena != null && foto != null) {
            Usuario(
                id_usuario = idUsuario,
                nombre = nombre,
                apellido = apellido,
                email = email,
                contrasena = contrasena,
                foto = foto
            )
        } else {
            intent.getSerializableExtra("usuario") as Usuario
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        someActivityResultLauncher.launch(intent)
    }

    private fun updateUser() {
        val updatedNombre = if (nombre.text.toString().isNotEmpty()) nombre.text.toString() else currentUser.nombre
        val updatedApellido = if (apellido.text.toString().isNotEmpty()) apellido.text.toString() else currentUser.apellido
        val updatedEmail = if (email.text.toString().isNotEmpty()) email.text.toString() else currentUser.email
        val updatedContrasena = if (contrasena.text.toString().isNotEmpty()) contrasena.text.toString() else currentUser.contrasena
        val updatedFoto = userPhoto?.let { ImageHelper.encodeImageViewToBase64(fotoPerfil) } ?: currentUser.foto

        currentUser.nombre = updatedNombre
        currentUser.apellido = updatedApellido
        currentUser.email = updatedEmail
        currentUser.contrasena = updatedContrasena
        currentUser.foto = updatedFoto

        // Sending updated user data to the server
        UsuarioRepository.modificarUsuario(this, currentUser, currentUser.id_usuario!!, this)
    }

    override fun onUserUpdateSuccess(response: String) {
        Toast.makeText(this, "Usuario actualizado con éxito", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onUserUpdateFailed(error: String) {
        Toast.makeText(this, "Error al actualizar usuario: $error", Toast.LENGTH_LONG).show()
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

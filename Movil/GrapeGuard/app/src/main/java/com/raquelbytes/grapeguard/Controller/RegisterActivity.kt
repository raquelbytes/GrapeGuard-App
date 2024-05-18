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

class RegisterActivity : AppCompatActivity(), UserRegisterCallback {


    private lateinit var email: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_view)

        val nombre = findViewById<EditText>(R.id.nombreEditText)
        val apellidos = findViewById<EditText>(R.id.apellidosEditText)
        email = findViewById<EditText>(R.id.emailEditText)
        val contrasena = findViewById<EditText>(R.id.passwordEditText)
        val fotoPerfil = findViewById<ImageView>(R.id.fotoPerfil)
        val btnFoto = findViewById<Button>(R.id.btnSacarFoto)
        val btnRegistro = findViewById<Button>(R.id.loginButton)
        val editTexts = listOf(nombre, apellidos, email, contrasena)

        btnFoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
            }
        }

        btnRegistro.setOnClickListener {
            if (!comprobarVacio(editTexts)) return@setOnClickListener

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

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        someActivityResultLauncher.launch(intent)
    }

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

    override fun onUserRegisterSuccess(response: String) {
        Toast.makeText(this, response, Toast.LENGTH_LONG).show()
        val intentLogin = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)
        finish()
    }

    override fun onUserRegisterFailed(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
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
            val imageView = findViewById<ImageView>(R.id.fotoPerfil)
            imageView.setImageBitmap(imageBitmap)
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

    companion object {
        private const val MY_CAMERA_PERMISSION_CODE = 100
        private const val REQUEST_TAKE_PHOTO = 101
    }
}

package com.raquelbytes.grapeguard.Controller

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.raquelbytes.grapeguard.API.Interface.UserFindCallback
import com.raquelbytes.grapeguard.API.Interface.UserUpdateCallback
import com.raquelbytes.grapeguard.API.Model.Usuario
import com.raquelbytes.grapeguard.API.Repository.UsuarioRepository
import com.raquelbytes.grapeguard.R
import com.raquelbytes.grapeguard.SQLite.UsuarioDAO
import com.raquelbytes.grapeguard.Util.ImageHelper
import com.raquelbytes.grapeguard.Util.LocaleUtil

/**
 * Actividad para cerrar sesión y actualizar información de usuario.
 */
class LogOutActivity : AppCompatActivity(), UserUpdateCallback {

    // Declaración de variables de vistas y de datos
    private lateinit var nombre: EditText
    private lateinit var apellido: EditText
    private lateinit var email: EditText
    private lateinit var contrasena: EditText
    private lateinit var fotoPerfil: ImageView
    private lateinit var usuarioActual: Usuario
    private var fotoUsuario: Bitmap? = null

    private val MY_CAMERA_PERMISSION_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logout_view) // Establece el diseño de la actividad

        // Inicialización de vistas
        nombre = findViewById(R.id.nombreEditText)
        apellido = findViewById(R.id.apellidosEditText)
        email = findViewById(R.id.emailEditText)
        contrasena = findViewById(R.id.passwordEditText)
        fotoPerfil = findViewById(R.id.logoImageView)
        val btnFoto = findViewById<Button>(R.id.btnSacarFoto)
        val btnGuardar = findViewById<Button>(R.id.saveChanges)

        // Obtiene el ID de usuario pasado desde la actividad anterior
        val idUsuario = intent.getIntExtra("id_usuario", -1)
        if (idUsuario == -1) {
            // Si no se proporciona un ID de usuario válido, muestra un mensaje de error y sale de la actividad
            Toast.makeText(this, getString(R.string.toast_error_obtener_usuario), Toast.LENGTH_LONG)
                .show()
            return
        }

        // Llama al método del repositorio para buscar al usuario por su ID
        UsuarioRepository.obtenerUsuarioPorId(this, idUsuario, object : UserFindCallback {
            override fun onUserUpdateSuccess(usuario: Usuario) {
                // Cuando se obtiene el usuario correctamente, actualiza el objeto usuarioActual y muestra los datos del usuario
                usuarioActual = usuario
                mostrarDatosUsuario()
            }

            override fun onUserUpdateFailed(error: String) {
                // Si falla la obtención del usuario, muestra un mensaje de error
                Toast.makeText(
                    this@LogOutActivity,
                    getString(R.string.toast_error_obtener_usuario) + error,
                    Toast.LENGTH_LONG
                ).show()
            }

        })

        // Configuración de listeners para los botones
        btnFoto.setOnClickListener {
            // Verifica si se tienen permisos para usar la cámara y, si es así, abre la cámara
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                abrirCamara()
            } else {
                // Si no se tienen permisos, solicita permisos de cámara
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    MY_CAMERA_PERMISSION_CODE
                )
            }
        }

        btnGuardar.setOnClickListener {
            // Llama al método para actualizar los datos del usuario
            actualizarUsuario()
        }

        val botonCerrarSesion = findViewById<Button>(R.id.cerrarSesion)
        botonCerrarSesion.setOnClickListener {
            // Llama al método para cerrar sesión
            cerrarSesion()
        }

        val botonCambiarAGallego = findViewById<ImageButton>(R.id.buttonChangeToGalician)
        botonCambiarAGallego.setOnClickListener {
            // Cambia el idioma a gallego y recrea la actividad para aplicar los cambios
            cambiarIdioma("gl")
        }

        val botonCambiarAEspañol = findViewById<ImageButton>(R.id.buttonChangeToSpanish)
        botonCambiarAEspañol.setOnClickListener {
            // Cambia el idioma a español y recrea la actividad para aplicar los cambios
            cambiarIdioma("es")
        }
    }

    // Función para mostrar los datos del usuario en las vistas
    private fun mostrarDatosUsuario() {
        nombre.setText(usuarioActual.nombre)
        apellido.setText(usuarioActual.apellido)
        email.setText(usuarioActual.email)
        contrasena.setText(usuarioActual.contrasena)
        fotoPerfil.setImageBitmap(usuarioActual.foto?.let { ImageHelper.decodeBase64ToBitmap(it) })
    }

    // Función para actualizar los datos del usuario
    private fun actualizarUsuario() {

        // Obtiene los datos actualizados de las vistas
        val nombreActualizado = if (nombre.text.toString()
                .isNotEmpty()
        ) nombre.text.toString() else usuarioActual.nombre
        val apellidoActualizado = if (apellido.text.toString()
                .isNotEmpty()
        ) apellido.text.toString() else usuarioActual.apellido
        val emailActualizado =
            if (email.text.toString().isNotEmpty()) email.text.toString() else usuarioActual.email
        val contrasenaActualizada = if (contrasena.text.toString()
                .isNotEmpty()
        ) contrasena.text.toString() else usuarioActual.contrasena
        val fotoActualizada = fotoUsuario?.let { ImageHelper.encodeImageViewToBase64(fotoPerfil) }
            ?: usuarioActual.foto

        if (!emailActualizado?.let { isValidEmail(it) }!!) {
            // Muestra un mensaje de error si el formato del correo electrónico es inválido
            Toast.makeText(
                this,
                getString(R.string.error_invalid_email_format),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (!nombreActualizado?.let { containsOnlyLetters(it) }!! || !apellidoActualizado?.let {
                containsOnlyLetters(
                    it
                )
            }!!) {
            // Muestra un mensaje de error si el nombre o apellido contienen caracteres no permitidos
            Toast.makeText(
                this,
                getString(R.string.error_only_letters),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (!contrasenaActualizada?.let { isValidPassword(it) }!!) {
            // Muestra un mensaje de error si la contraseña excede la longitud máxima permitida
            Toast.makeText(
                this,
                getString(R.string.error_password_length),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Actualiza los datos del usuario en el objeto usuarioActual
        usuarioActual.nombre = nombreActualizado
        usuarioActual.apellido = apellidoActualizado
        usuarioActual.email = emailActualizado
        usuarioActual.contrasena = contrasenaActualizada
        usuarioActual.foto = fotoActualizada




        // Envia los datos actualizados al servidor
        UsuarioRepository.modificarUsuario(this, usuarioActual, usuarioActual.id_usuario!!, this)
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

    // Función para abrir la cámara y capturar una foto
    private fun abrirCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        someActivityResultLauncher.launch(intent)
    }

    private val someActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Maneja el resultado de la actividad para capturar una foto
        if (result.resultCode == RESULT_OK && result.data != null) {
            // Si la captura de la foto fue exitosa, obtiene la imagen capturada como un bitmap
            val extras = result.data!!.extras
            val imageBitmap = extras!!["data"] as Bitmap
            fotoUsuario = imageBitmap
            fotoPerfil.setImageBitmap(imageBitmap)
        } else {
            // Si la captura de la foto falló, muestra un mensaje de error
            Toast.makeText(this, getString(R.string.toast_foto_no_capturada), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Maneja la respuesta del usuario a la solicitud de permisos de la cámara
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Si se otorga el permiso, abre la cámara
                abrirCamara()
            } else {
                // Si se deniega el permiso, muestra un mensaje de error
                Toast.makeText(
                    this,
                    getString(R.string.toast_permiso_camara_denegado),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onUserUpdateFailed(error: String) {
        // Maneja el caso en el que la actualización del usuario falla
        Toast.makeText(this, getString(R.string.toast_error_actualizar_usuario), Toast.LENGTH_LONG)
            .show()
    }

    override fun onUserUpdateSuccess(response: String) {
        // Maneja el caso en el que la actualización del usuario tiene éxito
        Toast.makeText(
            this,
            getString(R.string.toast_usuario_actualizado_exitoso),
            Toast.LENGTH_LONG
        ).show()
        finish()
    }

    private fun cambiarIdioma(codigoIdioma: String) {
        // Cambia el idioma de la aplicación y recrea la actividad para aplicar los cambios
        LocaleUtil.setLocale(this, codigoIdioma)
        recreate()
    }

    private fun cerrarSesion() {
        // Cierra la sesión del usuario borrando las preferencias compartidas y redirige a la actividad de inicio
        val sharedPreferences = getSharedPreferences("preferencias", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
    }

}
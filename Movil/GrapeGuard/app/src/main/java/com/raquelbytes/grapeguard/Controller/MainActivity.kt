package com.raquelbytes.grapeguard.Controller

import EncryptionUtil
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.raquelbytes.grapeguard.API.Interface.VinedoCallback
import com.raquelbytes.grapeguard.API.Interface.VinedosCallback
import com.raquelbytes.grapeguard.API.Model.Usuario
import com.raquelbytes.grapeguard.API.Model.Vinedo
import com.raquelbytes.grapeguard.API.Repository.VinedoRepository
import com.raquelbytes.grapeguard.R
import com.raquelbytes.grapeguard.SQLite.UsuarioDAO
import com.raquelbytes.grapeguard.SQLite.UsuarioDbHelper
import com.raquelbytes.grapeguard.Util.ImageHelper

class MainActivity : AppCompatActivity(), AddVinedoDialogFragment.AddVinedoDialogListener {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dbHelper: UsuarioDbHelper
    private var idUsuario: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vinedos_view)
        createNotificationChannel()

        sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        dbHelper = UsuarioDbHelper(this)

        idUsuario = getUsuarioId()
        Log.e("idUsuario", idUsuario.toString())

        if (idUsuario == null || idUsuario == -1) {
            Log.e("MainActivity", "ID de usuario no válido")
            return
        }

        val profileImageButton: ImageView = findViewById(R.id.profileImageView)
        val fotoUsuario = getFotoUsuario(idUsuario!!)
        Log.e("fotoUsuario", fotoUsuario ?: "null") // Agregamos un log para ver el valor de fotoUsuario
        cargarFotoUsuarioEnBoton(profileImageButton, fotoUsuario)

        profileImageButton.setOnClickListener {
            val intent = Intent(this, LogOutActivity::class.java)
            intent.putExtra("id_usuario", idUsuario)
            startActivity(intent)
        }

        val vineyardContainer: LinearLayout = findViewById(R.id.vineyardContainer)
        val addVineyardButton: Button = findViewById(R.id.add_vinedo_button)

        addVineyardButton.setOnClickListener {
            val dialog = AddVinedoDialogFragment()
            dialog.setAddVinedoDialogListener(this)
            dialog.show(supportFragmentManager, "AddVineyardDialogFragment")
        }

        loadVineyards(idUsuario!!, vineyardContainer)
    }

    private fun getUsuarioId(): Int? {
        val usuarioEncriptado = sharedPreferences.getString("Usuario", null)
        Log.e("usuarioEncriptado", usuarioEncriptado ?: "null")

        if (usuarioEncriptado != null) {
            return try {
                val usuarioJson = EncryptionUtil.desencriptar(usuarioEncriptado, "ejemploadmin123")
                val usuario = Gson().fromJson(usuarioJson, Usuario::class.java)
                Log.e("usuarioDesencriptado", usuario.nombre + usuario.id_usuario + usuario.contrasena)
                usuario.id_usuario
            } catch (ex: JsonSyntaxException) {
                Log.e("MainActivity", "Error de sintaxis JSON al desencriptar usuario: ${ex.message}")
                null
            } catch (ex: Exception) {
                Log.e("MainActivity", "Error al desencriptar usuario: ${ex.message}")
                null
            }
        } else {
            val idUsuarioIntent = intent.getIntExtra("id_usuario", -1)
            return if (idUsuarioIntent != -1) {
                idUsuarioIntent
            } else {
                null
            }
        }
    }

    private fun getFotoUsuario(id: Int): String? {
        val usuarioDAO = UsuarioDAO(this)
        val usuario = usuarioDAO.obtenerUsuario(id)
        Log.e("usuario", usuario.toString())

        return usuario?.foto
    }

    private fun cargarFotoUsuarioEnBoton(imageView: ImageView, fotoUsuario: String?) {
        if (fotoUsuario != null) {
            try {
                val decodedImage = ImageHelper.decodeBase64ToBitmap(fotoUsuario)
                imageView.setImageBitmap(decodedImage)
            } catch (ex: IllegalArgumentException) {
                Log.e("MainActivity", "Error al decodificar la imagen: ${ex.message}")
                imageView.setImageResource(R.drawable.defaultuserimage)
            }
        } else {
            // Si fotoUsuario es nulo, carga una imagen por defecto
            imageView.setImageResource(R.drawable.defaultuserimage)
        }
    }

    private fun loadVineyards(idUsuario: Int, vineyardContainer: LinearLayout) {
        // Limpiar el contenedor antes de cargar los viñedos
        vineyardContainer.removeAllViews()

        VinedoRepository.obtenerVinedosPorUsuario(this, idUsuario, object : VinedosCallback {
            override fun onVinedosObtenidos(vinedos: List<Vinedo>) {
                for (vinedo in vinedos) {
                    // Verificar si el viñedo ya está presente en el contenedor
                    val vineyardExists = vineyardContainer.findViewWithTag<View>(vinedo.id)
                    if (vineyardExists == null) {
                        val cardView = layoutInflater.inflate(R.layout.vineyard_card, null) as LinearLayout
                        val vineyardNameTextView: TextView = cardView.findViewById(R.id.vineyardNameTextView)
                        val locationTextView: TextView = cardView.findViewById(R.id.locationTextView)
                        vineyardNameTextView.text = vinedo.nombre
                        locationTextView.text = vinedo.ubicacion
                        vineyardContainer.addView(cardView)

                        cardView.tag = vinedo.id // Agregar una etiqueta con el ID del viñedo

                        val clickableLayout: LinearLayout = cardView.findViewById(R.id.linearlayout)
                        clickableLayout.setOnClickListener {
                            val intent = Intent(this@MainActivity, VinedoActivity::class.java)
                            intent.putExtra("vinedo", vinedo)
                            Log.e("Vinedo", vinedo.usuario.toString())
                            startActivity(intent)
                        }
                    }
                }
            }

            override fun onVinedoError(errorMessage: String) {
                Log.e("Vinedos Error", errorMessage)
            }
        })
    }


    override fun onVinedoAdded(vinedo: Vinedo) {
        val idUsuario = this.idUsuario ?: return

        VinedoRepository.agregarNuevoVinedo(this, idUsuario, vinedo, object : VinedoCallback {
            override fun onVinedoAgregado(response: String) {
                Log.d("Vinedo agregado", response)
                val vineyardContainer: LinearLayout = findViewById(R.id.vineyardContainer)
                loadVineyards(idUsuario, vineyardContainer)
            }

            override fun onVinedoError(errorMessage: String) {
                if (errorMessage.contains("Ya existe un viñedo en esta ubicación")) {
                    // Muestra un Toast indicando que ya existe un viñedo en la ubicación especificada
                    Toast.makeText(this@MainActivity, getString(R.string.toast_invalid_vinedo), Toast.LENGTH_SHORT).show()
                } else {
                    // Muestra un Toast genérico para otros errores desconocidos
                    Toast.makeText(this@MainActivity, "Error desconocido", Toast.LENGTH_SHORT).show()
                }
                Log.e("Error al agregar viñedo", errorMessage)
            }
        })
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Task Reminders"
            val descriptionText = "Channel for task reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(AddTaskDialogFragment.MY_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    override fun onResume() {
        super.onResume()
        // Actualizar los datos al volver a la actividad
        idUsuario = getUsuarioId()
        val profileImageButton: ImageView = findViewById(R.id.profileImageView)
        val fotoUsuario = getFotoUsuario(idUsuario!!)
        cargarFotoUsuarioEnBoton(profileImageButton, fotoUsuario)
        val vineyardContainer: LinearLayout = findViewById(R.id.vineyardContainer)
        loadVineyards(idUsuario!!, vineyardContainer)
    }


}

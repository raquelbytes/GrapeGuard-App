package com.raquelbytes.grapeguard.Controller

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.raquelbytes.grapeguard.API.Interface.VinedosCallback
import com.raquelbytes.grapeguard.API.Model.Vinedo
import com.raquelbytes.grapeguard.API.Model.Usuario
import com.raquelbytes.grapeguard.API.Repository.VinedoRepository
import com.raquelbytes.grapeguard.R
import com.raquelbytes.grapeguard.Util.EncryptionUtil

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vinedos_view)

        sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE)

        // Obtener el ID del usuario del intent o de las preferencias
        val idUsuario = getUserIdFromIntentOrPreferences()

        if (idUsuario == null || idUsuario == -1) {
            Log.e("MainActivity", "ID de usuario no válido")
            return
        }

        // Obtener referencia al LinearLayout que contiene las tarjetas de viñedos
        val vineyardContainer: LinearLayout = findViewById(R.id.vineyardContainer)

        // Llamar a la función para obtener los viñedos del usuario loggeado
        VinedoRepository.obtenerVinedosPorUsuario(this, idUsuario, object : VinedosCallback {
            override fun onVinedosObtenidos(vinedos: List<Vinedo>) {
                // Recorrer la lista de viñedos y mostrar cada uno en una tarjeta
                for (vinedo in vinedos) {
                    val cardView = layoutInflater.inflate(R.layout.vineyard_card, null) as LinearLayout
                    val vineyardNameTextView: TextView = cardView.findViewById(R.id.vineyardNameTextView)
                    val locationTextView: TextView = cardView.findViewById(R.id.locationTextView)
                    vineyardNameTextView.text = vinedo.nombre
                    locationTextView.text = vinedo.ubicacion
                    vineyardContainer.addView(cardView)

                    // Configurar OnClickListener para la tarjeta
                    cardView.setOnClickListener {
                        // Crear un Intent para abrir la actividad VinedoActivity
                        val intent = Intent(this@MainActivity, VinedoActivity::class.java)
                        // Pasar el objeto Vinedo completo como extra del Intent
                        intent.putExtra("vinedo", vinedo)
                        // Iniciar la actividad VinedoActivity
                        startActivity(intent)
                    }
                }
            }

            override fun onVinedoError(errorMessage: String) {
                Log.e("Vinedos Error", errorMessage)
            }
        })
    }

    private fun getUserIdFromIntentOrPreferences(): Int? {
        val idUsuario = intent.getIntExtra("id_usuario", -1)

        if (idUsuario != -1) {
            return idUsuario
        }

        val usuarioEncriptado = sharedPreferences.getString("Usuario", null)

        return if (usuarioEncriptado != null) {
            try {
                val usuarioJson = EncryptionUtil.desencriptar(usuarioEncriptado, "admintest123")
                val usuario = Gson().fromJson(usuarioJson, Usuario::class.java)
                usuario.id_usuario
            } catch (ex: JsonSyntaxException) {
                Log.e("MainActivity", "Error de sintaxis JSON al desencriptar usuario: ${ex.message}")
                null
            } catch (ex: Exception) {
                Log.e("MainActivity", "Error al desencriptar usuario: ${ex.message}")
                null
            }
        } else {
            Log.e("MainActivity", "No se encontró usuario en las preferencias")
            null
        }
    }
}

package com.raquelbytes.grapeguard.Controller

import EncryptionUtil
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.raquelbytes.grapeguard.API.Interface.VinedoCallback
import com.raquelbytes.grapeguard.API.Interface.VinedosCallback
import com.raquelbytes.grapeguard.API.Model.Usuario
import com.raquelbytes.grapeguard.API.Model.Vinedo
import com.raquelbytes.grapeguard.API.Repository.VinedoRepository
import com.raquelbytes.grapeguard.R


class MainActivity : AppCompatActivity(), AddVinedoDialogFragment.AddVinedoDialogListener {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vinedos_view)

        sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE)

        val idUsuario = getUserIdFromIntentOrPreferences()

        if (idUsuario == null || idUsuario == -1) {
            Log.e("com.raquelbytes.grapeguard.Controller.MainActivity", "ID de usuario no válido")
            return
        }

        val vineyardContainer: LinearLayout = findViewById(R.id.vineyardContainer)
        val addVineyardButton: Button = findViewById(R.id.add_vinedo_button)

        addVineyardButton.setOnClickListener {
            val dialog = AddVinedoDialogFragment()
            dialog.setAddVinedoDialogListener(this)
            dialog.show(supportFragmentManager, "AddVineyardDialogFragment")
        }

        loadVineyards(idUsuario, vineyardContainer)
    }

    private fun getUserIdFromIntentOrPreferences(): Int? {
        val idUsuario = intent.getIntExtra("id_usuario", -1)

        if (idUsuario != -1) {
            return idUsuario
        }

        val usuarioEncriptado = sharedPreferences.getString("Usuario", null)

        return if (usuarioEncriptado != null) {
            try {
                val usuarioJson = EncryptionUtil.desencriptar(usuarioEncriptado, "ejemploadmin123")
                val usuario = Gson().fromJson(usuarioJson, Usuario::class.java)
                usuario.id_usuario
            } catch (ex: JsonSyntaxException) {
                Log.e("com.raquelbytes.grapeguard.Controller.MainActivity", "Error de sintaxis JSON al desencriptar usuario: ${ex.message}")
                null
            } catch (ex: Exception) {
                Log.e("com.raquelbytes.grapeguard.Controller.MainActivity", "Error al desencriptar usuario: ${ex.message}")
                null
            }
        } else {
            Log.e("com.raquelbytes.grapeguard.Controller.MainActivity", "No se encontró usuario en las preferencias")
            null
        }
    }

    private fun loadVineyards(idUsuario: Int, vineyardContainer: LinearLayout) {
        VinedoRepository.obtenerVinedosPorUsuario(this, idUsuario, object : VinedosCallback {
            override fun onVinedosObtenidos(vinedos: List<Vinedo>) {
                for (vinedo in vinedos) {
                    val cardView = layoutInflater.inflate(R.layout.vineyard_card, null) as LinearLayout
                    val vineyardNameTextView: TextView = cardView.findViewById(R.id.vineyardNameTextView)
                    val locationTextView: TextView = cardView.findViewById(R.id.locationTextView)
                    vineyardNameTextView.text = vinedo.nombre
                    locationTextView.text = vinedo.ubicacion
                    vineyardContainer.addView(cardView)

                    cardView.setOnClickListener {
                        val intent = Intent(this@MainActivity, VinedoActivity::class.java)
                        intent.putExtra("vinedo", vinedo)
                        startActivity(intent)
                    }
                }
            }

            override fun onVinedoError(errorMessage: String) {
                Log.e("Vinedos Error", errorMessage)
            }
        })
    }

    override fun onVinedoAdded(vinedo: Vinedo) {
        val idUsuario = getUserIdFromIntentOrPreferences() ?: return
        Log.e("prefs Error", idUsuario.toString());


        VinedoRepository.agregarNuevoVinedo(this, idUsuario, vinedo, object : VinedoCallback {
                override fun onVinedoAgregado(response: String) {
                    Log.d("Vinedo agregado", response)
                    val vineyardContainer: LinearLayout = findViewById(R.id.vineyardContainer)
                    loadVineyards(idUsuario, vineyardContainer)
                }

                override fun onVinedoError(errorMessage: String) {
                    Log.e("Error al agregar viñedo", errorMessage)
                }
            }
        )
    }

}

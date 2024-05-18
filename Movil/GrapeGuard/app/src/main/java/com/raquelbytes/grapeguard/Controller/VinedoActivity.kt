package com.raquelbytes.grapeguard.Controller

import TareaAdapter
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.raquelbytes.grapeguard.API.Interface.BorrarTareaCallback
import com.raquelbytes.grapeguard.API.Interface.CosechaCallback
import com.raquelbytes.grapeguard.API.Interface.TareaCallback
import com.raquelbytes.grapeguard.API.Model.Cosecha
import com.raquelbytes.grapeguard.API.Model.Tarea
import com.raquelbytes.grapeguard.API.Model.Vinedo
import com.raquelbytes.grapeguard.API.Repository.CosechaRepository
import com.raquelbytes.grapeguard.API.Repository.TareaRepository
import com.raquelbytes.grapeguard.R


class VinedoActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.vinedo_view)

                // Recuperar el objeto Vinedo del Intent
                val vinedo: Vinedo? = intent.getSerializableExtra("vinedo") as? Vinedo

                // Verificar si el objeto Vinedo no es nulo antes de acceder a sus atributos
                if (vinedo != null) {
                        // Acceder a los atributos del viñedo y mostrarlos en los componentes de la actividad
                        val nombreTextView: TextView = findViewById(R.id.vineyardNameTextView)
                        val ubicacionTextView: TextView = findViewById(R.id.locationTextView)

                        nombreTextView.text = vinedo.nombre
                        ubicacionTextView.text = vinedo.ubicacion

                        // Continuar mostrando otros atributos del viñedo según sea necesario
                        val tableLayout: TableLayout = findViewById(R.id.tableLayout)
                        val vinedoId = vinedo.id ?: -1
                        CosechaRepository.obtenerCosechasPorVinedo(this, vinedoId, object :
                                CosechaCallback {
                                override fun onCosechasObtenidas(cosechas: List<Cosecha>) {
                                        runOnUiThread {
                                                val rowCount = tableLayout.childCount
                                                for (i in 1 until rowCount) {
                                                        tableLayout.removeViewAt(1)
                                                }

                                                // Agregar nuevas filas para mostrar los datos de las cosechas
                                                cosechas.forEach { cosecha ->
                                                        val row = TableRow(this@VinedoActivity)
                                                        val varietyTextView =
                                                                TextView(this@VinedoActivity).apply {
                                                                        text =
                                                                                cosecha.nombreVariedad
                                                                        layoutParams =
                                                                                TableRow.LayoutParams(
                                                                                        0,
                                                                                        TableRow.LayoutParams.WRAP_CONTENT,
                                                                                        1f
                                                                                )
                                                                }
                                                        val quantityTextView =
                                                                TextView(this@VinedoActivity).apply {
                                                                        text =
                                                                                cosecha.cantidadUvas.toString()
                                                                        layoutParams =
                                                                                TableRow.LayoutParams(
                                                                                        0,
                                                                                        TableRow.LayoutParams.WRAP_CONTENT,
                                                                                        1f
                                                                                )
                                                                }
                                                        val priceTextView =
                                                                TextView(this@VinedoActivity).apply {
                                                                        text =
                                                                                cosecha.precioVentaKg.toString()
                                                                        layoutParams =
                                                                                TableRow.LayoutParams(
                                                                                        0,
                                                                                        TableRow.LayoutParams.WRAP_CONTENT,
                                                                                        1f
                                                                                )
                                                                }
                                                        val dateTextView =
                                                                TextView(this@VinedoActivity).apply {
                                                                        text =
                                                                                cosecha.fechaCosecha.toString()
                                                                        layoutParams =
                                                                                TableRow.LayoutParams(
                                                                                        0,
                                                                                        TableRow.LayoutParams.WRAP_CONTENT,
                                                                                        1f
                                                                                )
                                                                }

                                                        row.addView(varietyTextView)
                                                        row.addView(quantityTextView)
                                                        row.addView(priceTextView)
                                                        row.addView(dateTextView)

                                                        tableLayout.addView(row)
                                                }
                                        }
                                }

                                override fun onError(errorMessage: String) {
                                        Log.e("Cosechas Error", errorMessage)
                                }
                        })
                        val tareaListView: ListView = findViewById(R.id.list_view_tasks)
                        TareaRepository.obtenerTareasPorVinedo(this, vinedoId, object :
                                TareaCallback {
                                override fun onTareasObtenidas(tareas: List<Tarea>) {
                                        // Creamos el adaptador y lo asignamos al ListView
                                        val tareaAdapter = TareaAdapter(this@VinedoActivity, R.layout.item_tarea, tareas)
                                        tareaListView.adapter = tareaAdapter

                                        tareaListView.setOnItemClickListener { _, _, position, _ ->
                                                val tareaSeleccionada = tareas[position]
                                                val tareaSeleccionadaid = tareaSeleccionada.id ?: -1
                                                borrarTarea(tareaSeleccionadaid)
                                        }
                                }

                                override fun onError(errorMessage: String) {
                                        Log.e("Tareas Error", errorMessage)
                                        // Manejar el error, por ejemplo, mostrando un mensaje al usuario
                                }
                        })

                }

        }

        fun borrarTarea(idTarea: Int) {
                // Mostrar un diálogo de confirmación antes de borrar la tarea
                mostrarDialogoConfirmacion(idTarea)
        }

        private fun mostrarDialogoConfirmacion(idTarea: Int) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Confirmación")
                builder.setMessage("¿Está seguro de que desea borrar la tarea?")

                // Si el usuario hace clic en "Sí", borramos la tarea
                builder.setPositiveButton("Sí") { _: DialogInterface, _: Int ->
                        // Llama al método en TareaRepository para borrar la tarea
                        TareaRepository.borrarTareaDelVinedo(this, idTarea, object : BorrarTareaCallback {
                                override fun onTareaBorrada(mensaje: String) {
                                        // Tarea borrada con éxito
                                        Log.d("Tarea borrada", mensaje)
                                        // Actualiza la lista de tareas después de borrar
                                        actualizarListaTareas()
                                }

                                override fun onError(errorMessage: String) {
                                        // Manejar errores
                                        Log.e("Error al borrar tarea", errorMessage)
                                }
                        })
                }

                // Si el usuario hace clic en "No" o cierra el diálogo, no hacemos nada
                builder.setNegativeButton("No") { dialog: DialogInterface, _: Int ->
                        dialog.dismiss() // Cierra el diálogo
                }

                val dialog = builder.create()
                dialog.show()
        }

        private fun actualizarListaTareas() {
                val vinedo: Vinedo? = intent.getSerializableExtra("vinedo") as? Vinedo
                val tareaListView: ListView = findViewById(R.id.list_view_tasks)
                if (vinedo != null) {
                        val vinedoId = vinedo.id ?: -1

                        // Llama nuevamente al método para obtener las tareas del viñedo actual y actualiza la lista
                        TareaRepository.obtenerTareasPorVinedo(
                                this,
                                vinedoId,
                                object : TareaCallback {
                                        override fun onTareasObtenidas(tareas: List<Tarea>) {
                                                // Creamos el adaptador y lo asignamos al ListView
                                                val tareaAdapter = TareaAdapter(
                                                        this@VinedoActivity,
                                                        R.layout.item_tarea,
                                                        tareas
                                                )
                                                tareaListView.adapter = tareaAdapter
                                        }

                                        override fun onError(errorMessage: String) {
                                                Log.e("Tareas Error", errorMessage)
                                                // Manejar el error, por ejemplo, mostrando un mensaje al usuario
                                        }
                                })
                }
        }
}

package com.raquelbytes.grapeguard.Controller

import AddHarvestDialogFragment
import TareaAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.raquelbytes.grapeguard.API.Interface.AgregarCosechaCallback
import com.raquelbytes.grapeguard.API.Interface.AgregarTareaCallback
import com.raquelbytes.grapeguard.API.Interface.BorrarTareaCallback
import com.raquelbytes.grapeguard.API.Interface.CosechaCallback
import com.raquelbytes.grapeguard.API.Interface.TareaCallback
import com.raquelbytes.grapeguard.API.Model.Cosecha
import com.raquelbytes.grapeguard.API.Model.Tarea
import com.raquelbytes.grapeguard.API.Model.Vinedo
import com.raquelbytes.grapeguard.API.Repository.CosechaRepository
import com.raquelbytes.grapeguard.API.Repository.TareaRepository
import com.raquelbytes.grapeguard.R
import com.raquelbytes.grapeguard.SQLite.UsuarioDAO
import com.raquelbytes.grapeguard.Util.ImageHelper

class VinedoActivity : AppCompatActivity(), AddTaskDialogFragment.AddTaskDialogListener,  AddHarvestDialogFragment.AddHarvestDialogListener {

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.vinedo_view)

                val vinedo: Vinedo? = intent.getSerializableExtra("vinedo") as? Vinedo
                val profileImageButton: ImageView = findViewById(R.id.profileImageView)



                if (vinedo != null) {
                        val nombreTextView: TextView = findViewById(R.id.vineyardNameTextView)
                        val ubicacionTextView: TextView = findViewById(R.id.locationTextView)

                        nombreTextView.text = vinedo.nombre
                        ubicacionTextView.text = vinedo.ubicacion

                        val tableLayout: TableLayout = findViewById(R.id.tableLayout)
                        val vinedoId = vinedo.id ?: -1



                        val foto = vinedo.usuario?.let { getFotoUsuario(it) };
                        cargarFotoUsuarioEnBoton(profileImageButton,foto);
                        profileImageButton.setOnClickListener {
                                val intent = Intent(this, LogOutActivity::class.java)
                                intent.putExtra("id_usuario", vinedo.usuario)
                                startActivity(intent)
                        }

                        CosechaRepository.obtenerCosechasPorVinedo(this, vinedoId, object : CosechaCallback {
                                override fun onCosechasObtenidas(cosechas: List<Cosecha>) {
                                        runOnUiThread {
                                                val rowCount = tableLayout.childCount
                                                for (i in 1 until rowCount) {
                                                        tableLayout.removeViewAt(1)
                                                }

                                                cosechas.forEach { cosecha ->
                                                        val row = TableRow(this@VinedoActivity)
                                                        val varietyTextView = TextView(this@VinedoActivity).apply {
                                                                text = cosecha.nombreVariedad
                                                                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                                                        }
                                                        val quantityTextView = TextView(this@VinedoActivity).apply {
                                                                text = cosecha.cantidadUvas.toString()
                                                                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                                                        }
                                                        val priceTextView = TextView(this@VinedoActivity).apply {
                                                                text = cosecha.precioVentaKg.toString()
                                                                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                                                        }
                                                        val dateTextView = TextView(this@VinedoActivity).apply {
                                                                text = cosecha.fechaCosecha.toString()
                                                                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
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
                        TareaRepository.obtenerTareasPorVinedo(this, vinedoId, object : TareaCallback {
                                override fun onTareasObtenidas(tareas: List<Tarea>) {
                                        val tareaAdapter = TareaAdapter(this@VinedoActivity, R.layout.item_tarea, tareas)
                                        tareaListView.adapter = tareaAdapter

                                        tareaListView.setOnItemClickListener { _, _, position, _ ->
                                                val tareaSeleccionada = tareas[position]
                                                val tareaSeleccionadaId = tareaSeleccionada.id ?: -1
                                                borrarTarea(tareaSeleccionadaId)
                                        }
                                }

                                override fun onError(errorMessage: String) {
                                        Log.e("Tareas Error", errorMessage)
                                }
                        })
                }

                val addTaskButton: Button = findViewById(R.id.add_task_button)
                addTaskButton.setOnClickListener {
                        val dialog = AddTaskDialogFragment()
                        dialog.setAddTaskDialogListener(this)
                        dialog.show(supportFragmentManager, "AddTaskDialogFragment")
                }
                val addHarvestButton: Button = findViewById(R.id.add_harvest_button)
                addHarvestButton.setOnClickListener {
                        val dialog = AddHarvestDialogFragment()
                        dialog.setAddHarvestDialogListener(this)
                        dialog.show(supportFragmentManager, "AddHarvestDialogFragment")
                }

        }


        fun agregarCosechaAlVinedo(idVinedo: Int, nuevaCosecha: Cosecha) {
                CosechaRepository.agregarCosecha(this, idVinedo, nuevaCosecha, object :
                        AgregarCosechaCallback {
                        override fun onCosechaAgregada(response: String) {
                                // Manejar la respuesta exitosa
                                println("Cosecha agregada exitosamente: $response")
                                actualizarListaCosechas()
                        }

                        override fun onError(errorMessage: String) {
                                // Manejar el error
                                println("Error al agregar cosecha: $errorMessage")
                        }
                })
        }


        override fun onTaskAdded(tarea: Tarea) {
                val vinedo: Vinedo? = intent.getSerializableExtra("vinedo") as? Vinedo
                val vinedoId = vinedo?.id ?: -1
                agregarTareaAlVinedo(vinedoId, tarea)
        }
        override fun onHarvestAdded(cosecha: Cosecha) {
                val vinedo: Vinedo? = intent.getSerializableExtra("vinedo") as? Vinedo
                val vinedoId = vinedo?.id ?: -1
                agregarCosechaAlVinedo(vinedoId, cosecha)
        }

         fun agregarTareaAlVinedo(idVinedo: Int, nuevaTarea: Tarea) {
                TareaRepository.agregarTareaAlVinedo(this, idVinedo, nuevaTarea, object : AgregarTareaCallback {
                        override fun onTareaAgregada(response: String) {
                                // Manejar la respuesta exitosa
                                println("Tarea agregada exitosamente: $response")
                                actualizarListaTareas()
                        }

                        override fun onError(errorMessage: String) {
                                // Manejar el error
                                println("Error al agregar tarea: $errorMessage")
                        }
                })
        }

         fun borrarTarea(idTarea: Int) {
                mostrarDialogoConfirmacion(idTarea)
        }

         fun mostrarDialogoConfirmacion(idTarea: Int) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Confirmación")
                builder.setMessage("¿Está seguro de que desea borrar la tarea?")

                builder.setPositiveButton("Sí") { _, _ ->
                        TareaRepository.borrarTareaDelVinedo(this, idTarea, object : BorrarTareaCallback {
                                override fun onTareaBorrada(mensaje: String) {
                                        Log.d("Tarea borrada", mensaje)
                                        actualizarListaTareas()
                                }

                                override fun onError(errorMessage: String) {
                                        Log.e("Error al borrar tarea", errorMessage)
                                }
                        })
                }

                builder.setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
        }

        fun actualizarListaTareas() {
                val vinedo: Vinedo? = intent.getSerializableExtra("vinedo") as? Vinedo
                val tareaListView: ListView = findViewById(R.id.list_view_tasks)
                if (vinedo != null) {
                        val vinedoId = vinedo.id ?: -1
                        TareaRepository.obtenerTareasPorVinedo(this, vinedoId, object : TareaCallback {
                                override fun onTareasObtenidas(tareas: List<Tarea>) {
                                        val tareaAdapter = TareaAdapter(this@VinedoActivity, R.layout.item_tarea, tareas)
                                        tareaListView.adapter = tareaAdapter

                                        // Si la lista de tareas está vacía, limpiar la lista
                                        if (tareas.isEmpty()) {
                                                tareaAdapter.clear()
                                                tareaAdapter.notifyDataSetChanged()
                                        }
                                }

                                override fun onError(errorMessage: String) {
                                        // Si se produce un error al obtener las tareas, mostrar un mensaje de error pero limpiar la lista
                                        Log.e("Tareas Error", errorMessage)
                                        val tareaAdapter = TareaAdapter(this@VinedoActivity, R.layout.item_tarea, emptyList())
                                        tareaListView.adapter = tareaAdapter
                                }
                        })
                }
        }

        fun actualizarListaCosechas() {
                val vinedo: Vinedo? = intent.getSerializableExtra("vinedo") as? Vinedo
                val tableLayout: TableLayout = findViewById(R.id.tableLayout)
                if (vinedo != null) {
                        val vinedoId = vinedo.id ?: -1
                        CosechaRepository.obtenerCosechasPorVinedo(this, vinedoId, object : CosechaCallback {
                                override fun onCosechasObtenidas(cosechas: List<Cosecha>) {
                                        runOnUiThread {
                                                val rowCount = tableLayout.childCount
                                                for (i in 1 until rowCount) {
                                                        tableLayout.removeViewAt(1)
                                                }

                                                cosechas.forEach { cosecha ->
                                                        val row = TableRow(this@VinedoActivity)
                                                        val varietyTextView = TextView(this@VinedoActivity).apply {
                                                                text = cosecha.nombreVariedad
                                                                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                                                        }
                                                        val quantityTextView = TextView(this@VinedoActivity).apply {
                                                                text = cosecha.cantidadUvas.toString()
                                                                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                                                        }
                                                        val priceTextView = TextView(this@VinedoActivity).apply {
                                                                text = cosecha.precioVentaKg.toString()
                                                                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                                                        }
                                                        val dateTextView = TextView(this@VinedoActivity).apply {
                                                                text = cosecha.fechaCosecha.toString()
                                                                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
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

}

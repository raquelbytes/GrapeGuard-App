package com.raquelbytes.grapeguard.Controller

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.raquelbytes.grapeguard.API.Model.EstadoTarea
import com.raquelbytes.grapeguard.API.Model.Tarea
import com.raquelbytes.grapeguard.R
import com.raquelbytes.grapeguard.Util.AlarmNotification
import java.text.SimpleDateFormat
import java.util.*

// Fragmento para agregar una nueva tarea
class AddTaskDialogFragment : DialogFragment() {

    // Interfaz para comunicarse con la actividad que llama al fragmento
    interface AddTaskDialogListener {
        fun onTaskAdded(tarea: Tarea)
    }
    companion object {
        const val MY_CHANNEL_ID = "myChannel"
        const val NOTIFICATION_ID = 1
    }

    private lateinit var listener: AddTaskDialogListener

    // Variables para almacenar la fecha y hora seleccionadas
    private var selectedRecordatorio: String? = null
    private var selectedFechaRealizacion: String? = null

    // Método para configurar el listener del diálogo
    fun setAddTaskDialogListener(listener: AddTaskDialogListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task_dialog, container, false)

        // Inicialización de vistas
        val tareaEditText = view.findViewById<EditText>(R.id.tareaEditText)
        val estadoSpinner = view.findViewById<Spinner>(R.id.estadoSpinner)
        val recordatorioEditText = view.findViewById<EditText>(R.id.recordatorioEditText)
        val fechaRealizacionEditText =
            view.findViewById<EditText>(R.id.fechaRealizacionEditText)

        // Configuración de botones
        val cancelButton = view.findViewById<Button>(R.id.cancelButton)
        val addButton = view.findViewById<Button>(R.id.addButton)

        // Listener para el botón de cancelar
        cancelButton.setOnClickListener {
            dismiss() // Cierra el diálogo
        }

        // Listener para el campo de recordatorio
        recordatorioEditText.setOnClickListener {
            showDateTimePicker { dateTime ->
                selectedRecordatorio = dateTime
                recordatorioEditText.setText(dateTime) // Establece la fecha y hora seleccionadas en el campo de texto
            }
        }

        // Listener para el campo de fecha de realización
        fechaRealizacionEditText.setOnClickListener {
            showDatePicker { date ->
                selectedFechaRealizacion = date
                fechaRealizacionEditText.setText(date) // Establece la fecha seleccionada en el campo de texto
            }
        }

        // Listener para el botón de agregar
        addButton.setOnClickListener {
            // Crea un objeto de tipo Tarea con los datos ingresados por el usuario
            val tarea = Tarea().apply {
                this.tarea = tareaEditText.text.toString()
                this.estado = EstadoTarea.valueOf(estadoSpinner.selectedItem.toString())
                this.recordatorio = selectedRecordatorio
                this.fechaRealizacion = selectedFechaRealizacion
            }
            listener.onTaskAdded(tarea) // Llama al método onTaskAdded del listener para pasar la nueva tarea a la actividad

            if (selectedRecordatorio != null) {
                scheduleNotification(selectedRecordatorio!!, tarea.tarea!!)
            }
            dismiss() // Cierra el diálogo
        }

        return view
    }

    // Método para mostrar un diálogo de selección de fecha y hora
    private fun showDateTimePicker(callback: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)
                        val dateTimeFormat =
                            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                        callback(dateTimeFormat.format(calendar.time)) // Ejecuta el callback con la fecha y hora seleccionadas
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Método para mostrar un diálogo de selección de fecha
    private fun showDatePicker(callback: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                callback(dateFormat.format(calendar.time)) // Ejecuta el callback con la fecha seleccionada
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNotification(dateTime: String, tarea: String) {
        val alarmManager =
            requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmNotification::class.java)
        intent.putExtra("TAREA", tarea) // Pasar la tarea a AlarmNotification
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Convertir la fecha y hora del recordatorio a milisegundos
        val dateTimeMillis =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(dateTime)?.time
                ?: return

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, dateTimeMillis, pendingIntent)
        Log.d("AddTaskDialogFragment", "Alarma programada para $dateTime con tarea $tarea")
    }

}

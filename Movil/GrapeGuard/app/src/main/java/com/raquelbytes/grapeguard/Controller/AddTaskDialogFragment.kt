package com.raquelbytes.grapeguard.Controller

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.raquelbytes.grapeguard.API.Model.EstadoTarea
import com.raquelbytes.grapeguard.API.Model.Tarea
import com.raquelbytes.grapeguard.R
import java.text.SimpleDateFormat
import java.util.*

class AddTaskDialogFragment : DialogFragment() {

    interface AddTaskDialogListener {
        fun onTaskAdded(tarea: Tarea)
    }

    private lateinit var listener: AddTaskDialogListener


    private var selectedRecordatorio: String? = null
    private var selectedFechaRealizacion: String? = null

    fun setAddTaskDialogListener(listener: AddTaskDialogListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task_dialog, container, false)

        val tareaEditText = view.findViewById<EditText>(R.id.tareaEditText)
        val estadoSpinner = view.findViewById<Spinner>(R.id.estadoSpinner)
        val recordatorioEditText = view.findViewById<EditText>(R.id.recordatorioEditText)
        val fechaRealizacionEditText = view.findViewById<EditText>(R.id.fechaRealizacionEditText)

        val cancelButton = view.findViewById<Button>(R.id.cancelButton)
        val addButton = view.findViewById<Button>(R.id.addButton)

        cancelButton.setOnClickListener {
            dismiss()
        }

        recordatorioEditText.setOnClickListener {
            showDateTimePicker { dateTime ->
                selectedRecordatorio = dateTime
                recordatorioEditText.setText(dateTime)
            }
        }

        fechaRealizacionEditText.setOnClickListener {
            showDatePicker { date ->
                selectedFechaRealizacion = date
                fechaRealizacionEditText.setText(date)
            }
        }


        addButton.setOnClickListener {
            val tarea = Tarea().apply {
                this.tarea = tareaEditText.text.toString()
                this.estado = EstadoTarea.valueOf(estadoSpinner.selectedItem.toString())
                this.recordatorio = selectedRecordatorio
                this.fechaRealizacion = selectedFechaRealizacion
            }
            listener.onTaskAdded(tarea)
            dismiss()
        }

        return view
    }

    private fun showDateTimePicker(callback: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                callback(dateTimeFormat.format(calendar.time))
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showDatePicker(callback: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            callback(dateFormat.format(calendar.time))
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }
}

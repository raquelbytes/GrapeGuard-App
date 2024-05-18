package com.raquelbytes.grapeguard.Controller

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.raquelbytes.grapeguard.API.Model.EstadoTarea
import com.raquelbytes.grapeguard.API.Model.Tarea
import com.raquelbytes.grapeguard.R

class AddTaskDialogFragment : DialogFragment() {

  /*  interface AddTaskDialogListener {
        fun onTaskAdded(tarea: Tarea)
    }

    private lateinit var listener: AddTaskDialogListener

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

        addButton.setOnClickListener {
            val tarea = Tarea().apply {
                this.tarea = tareaEditText.text.toString()
                this.estado = EstadoTarea.valueOf(estadoSpinner.selectedItem.toString())
                this.recordatorio = recordatorioEditText.text.toString()
                this.fechaRealizacion = fechaRealizacionEditText.text.toString()
            }
           
        }

        return view
    }

*/
}

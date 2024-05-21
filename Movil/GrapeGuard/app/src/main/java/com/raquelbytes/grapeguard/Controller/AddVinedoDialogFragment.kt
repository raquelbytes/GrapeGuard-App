package com.raquelbytes.grapeguard.Controller

import java.math.BigDecimal

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.raquelbytes.grapeguard.API.Model.Vinedo
import com.raquelbytes.grapeguard.R
import java.text.SimpleDateFormat
import java.util.*

class AddVinedoDialogFragment : DialogFragment() {

    interface AddVinedoDialogListener {
        fun onVinedoAdded(vinedo: Vinedo)
    }

    private lateinit var listener: AddVinedoDialogListener

    private var selectedFechaPlantacion: String? = null

    fun setAddVinedoDialogListener(listener: AddVinedoDialogListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_vinedo_dialog_fragment, container, false)

        val nombreEditText = view.findViewById<EditText>(R.id.nombreEditText)
        val ubicacionEditText = view.findViewById<EditText>(R.id.ubicacionEditText)
        val fechaPlantacionEditText = view.findViewById<EditText>(R.id.fechaPlantacionEditText)
        val hectareasEditText = view.findViewById<EditText>(R.id.hectareasEditText)

        val cancelButton = view.findViewById<Button>(R.id.cancelButton)
        val addButton = view.findViewById<Button>(R.id.addButton)

        cancelButton.setOnClickListener {
            dismiss()
        }

        fechaPlantacionEditText.setOnClickListener {
            showDatePicker { date ->
                selectedFechaPlantacion = date
                fechaPlantacionEditText.setText(date)
            }
        }

        addButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val ubicacion = ubicacionEditText.text.toString()
            val hectareasText = hectareasEditText.text.toString()

            if (nombre.isEmpty() || ubicacion.isEmpty() || selectedFechaPlantacion == null || hectareasText.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {


                val vinedo = Vinedo().apply {
                    this.nombre = nombre
                    this.ubicacion = ubicacion
                    this.fechaPlantacion = selectedFechaPlantacion
                    this.hectareas = BigDecimal(hectareasEditText.text.toString())
                }

                listener.onVinedoAdded(vinedo)
                dismiss()
            } catch (e: NumberFormatException) {
                Toast.makeText(requireContext(), "Formato de número no válido.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
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

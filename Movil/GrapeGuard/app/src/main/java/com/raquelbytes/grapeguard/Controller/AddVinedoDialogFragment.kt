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

// Fragmento para agregar un nuevo viñedo
class AddVinedoDialogFragment : DialogFragment() {

    // Interfaz para comunicarse con la actividad que llama al fragmento
    interface AddVinedoDialogListener {
        fun onVinedoAdded(vinedo: Vinedo)
    }

    private lateinit var listener: AddVinedoDialogListener
    private var selectedFechaPlantacion: String? = null

    // Método para configurar el listener del diálogo
    fun setAddVinedoDialogListener(listener: AddVinedoDialogListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_vinedo_dialog_fragment, container, false)

        // Inicialización de vistas
        val nombreEditText = view.findViewById<EditText>(R.id.nombreEditText)
        val ubicacionEditText = view.findViewById<EditText>(R.id.ubicacionEditText)
        val fechaPlantacionEditText = view.findViewById<EditText>(R.id.fechaPlantacionEditText)
        val hectareasEditText = view.findViewById<EditText>(R.id.hectareasEditText)
        val cancelButton = view.findViewById<Button>(R.id.cancelButton)
        val addButton = view.findViewById<Button>(R.id.addButton)

        // Listener para el botón de cancelar
        cancelButton.setOnClickListener {
            dismiss() // Cierra el diálogo
        }

        // Listener para el campo de fecha de plantación
        fechaPlantacionEditText.setOnClickListener {
            showDatePicker { date ->
                selectedFechaPlantacion = date
                fechaPlantacionEditText.setText(date) // Establece la fecha seleccionada en el campo de texto
            }
        }

        // Listener para el botón de agregar
        addButton.setOnClickListener {
            // Obtiene los valores ingresados por el usuario
            val nombre = nombreEditText.text.toString()
            val ubicacion = ubicacionEditText.text.toString()
            val hectareasText = hectareasEditText.text.toString()

            // Valida que los campos no estén vacíos
            if (nombre.isEmpty() || ubicacion.isEmpty() || selectedFechaPlantacion == null || hectareasText.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.toast_complete_all_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                // Crea un objeto de tipo Vinedo con los datos ingresados por el usuario
                val vinedo = Vinedo().apply {
                    this.nombre = nombre
                    this.ubicacion = ubicacion
                    this.fechaPlantacion = selectedFechaPlantacion
                    this.hectareas = BigDecimal(hectareasText)
                }

                listener.onVinedoAdded(vinedo) // Llama al método onVinedoAdded del listener para pasar el nuevo viñedo a la actividad
                dismiss() // Cierra el diálogo
            } catch (e: NumberFormatException) {
                Toast.makeText(requireContext(), getString(R.string.toast_invalid_number_format), Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    // Método para mostrar un diálogo de selección de fecha
    private fun showDatePicker(callback: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            callback(dateFormat.format(calendar.time)) // Ejecuta el callback con la fecha seleccionada
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }
}

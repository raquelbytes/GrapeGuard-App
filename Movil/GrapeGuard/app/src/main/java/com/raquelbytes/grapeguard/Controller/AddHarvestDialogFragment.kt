package com.raquelbytes.grapeguard.Controller

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.raquelbytes.grapeguard.API.Model.Cosecha
import com.raquelbytes.grapeguard.R
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

// Fragmento para agregar una nueva cosecha
class AddHarvestDialogFragment : DialogFragment() {

    // Interfaz para comunicarse con la actividad que llama al fragmento
    interface AddHarvestDialogListener {
        fun onHarvestAdded(cosecha: Cosecha)
    }

    private lateinit var listener: AddHarvestDialogListener
    private lateinit var fechaCosechaEditText: EditText
    private lateinit var cantidadUvasEditText: EditText
    private lateinit var precioVentaKgEditText: EditText
    private lateinit var nombreVariedadEditText: EditText

    // Método para configurar el listener del diálogo
    fun setAddHarvestDialogListener(listener: AddHarvestDialogListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_harvest_dialog_fragment, container, false)

        // Inicialización de vistas
        fechaCosechaEditText = view.findViewById(R.id.fechaCosechaEditText)
        cantidadUvasEditText = view.findViewById(R.id.cantidadUvasEditText)
        precioVentaKgEditText = view.findViewById(R.id.precioVentaKgEditText)
        nombreVariedadEditText = view.findViewById(R.id.nombreVariedad)

        // Configuración de botones
        val cancelButton = view.findViewById<Button>(R.id.cancelButton)
        val addButton = view.findViewById<Button>(R.id.addButton)

        // Listener para el botón de cancelar
        cancelButton.setOnClickListener {
            dismiss() // Cierra el diálogo
        }

        // Listener para el campo de fecha de cosecha
        fechaCosechaEditText.setOnClickListener {
            showDatePicker { date ->
                fechaCosechaEditText.setText(date) // Establece la fecha seleccionada en el campo de texto
            }
        }

        // Listener para el botón de agregar
        // Listener para el botón de agregar
        addButton.setOnClickListener {
            // Verifica si los campos están vacíos
            if (nombreVariedadEditText.text.isEmpty() || fechaCosechaEditText.text.isEmpty() ||
                cantidadUvasEditText.text.isEmpty() || precioVentaKgEditText.text.isEmpty()
            ) {
                // Muestra un mensaje de error si algún campo está vacío
                Toast.makeText(requireContext(), getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show()
            } else {
                // Verifica si el nombre de la variedad contiene solo caracteres
                var nombreVariedad = nombreVariedadEditText.text.toString()
                if (!nombreVariedad.matches("[a-zA-Z]+".toRegex())) {
                    // Muestra un mensaje de error si el nombre de la variedad contiene números
                    Toast.makeText(requireContext(), getString(R.string.error_invalid_variety_name), Toast.LENGTH_SHORT).show()
                } else {
                    // Crea un objeto de tipo Cosecha con los datos ingresados por el usuario
                    val cosecha = Cosecha().apply {
                        nombreVariedad = nombreVariedadEditText.text.toString()
                        fechaCosecha = fechaCosechaEditText.text.toString()
                        cantidadUvas = BigDecimal(cantidadUvasEditText.text.toString())
                        precioVentaKg = BigDecimal(precioVentaKgEditText.text.toString())
                    }

                    // Llama al método onHarvestAdded del listener para pasar la nueva cosecha a la actividad
                    listener.onHarvestAdded(cosecha)
                    dismiss() // Cierra el diálogo
                }
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

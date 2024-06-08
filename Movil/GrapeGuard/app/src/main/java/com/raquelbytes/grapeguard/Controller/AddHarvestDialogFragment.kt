import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.raquelbytes.grapeguard.API.Model.Cosecha
import com.raquelbytes.grapeguard.R
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

    class AddHarvestDialogFragment : DialogFragment() {

        interface AddHarvestDialogListener {
            fun onHarvestAdded(cosecha: Cosecha)
        }

        private lateinit var listener: AddHarvestDialogListener
        private lateinit var fechaCosechaEditText: EditText
        private lateinit var cantidadUvasEditText: EditText
        private lateinit var precioVentaKgEditText: EditText
        private lateinit var nombreVariedadEditText: EditText

        fun setAddHarvestDialogListener(listener: AddHarvestDialogListener) {
            this.listener = listener
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.add_harvest_dialog_fragment, container, false)

            fechaCosechaEditText = view.findViewById(R.id.fechaCosechaEditText)
            cantidadUvasEditText = view.findViewById(R.id.cantidadUvasEditText)
            precioVentaKgEditText = view.findViewById(R.id.precioVentaKgEditText)
            nombreVariedadEditText = view.findViewById(R.id.nombreVariedad)

            val cancelButton = view.findViewById<Button>(R.id.cancelButton)
            val addButton = view.findViewById<Button>(R.id.addButton)

            cancelButton.setOnClickListener {
                dismiss()
            }

            fechaCosechaEditText.setOnClickListener {
                showDatePicker { date ->
                    fechaCosechaEditText.setText(date)
                }
            }

            addButton.setOnClickListener {
                if (nombreVariedadEditText.text.isEmpty() || fechaCosechaEditText.text.isEmpty() ||
                    cantidadUvasEditText.text.isEmpty() || precioVentaKgEditText.text.isEmpty()
                ) {
                    Toast.makeText(requireContext(), getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show()
                } else {
                    val nombreVariedad = nombreVariedadEditText.text.toString()
                    Log.e("nombre variedad",nombreVariedad);
                    if (!nombreVariedad.matches("[a-zA-Z ]+".toRegex())) {
                        Log.e("no es valido",nombreVariedad);
                        Toast.makeText(requireContext(), getString(R.string.error_invalid_variety_name), Toast.LENGTH_SHORT).show()
                    } else {
                        val cosecha = Cosecha().apply {
                            this.nombreVariedad = nombreVariedadEditText.text.toString()
                            Log.e("nombre variedad", nombreVariedad);
                            this.fechaCosecha = fechaCosechaEditText.text.toString()
                            this.cantidadUvas = BigDecimal(cantidadUvasEditText.text.toString())
                            this.precioVentaKg = BigDecimal(precioVentaKgEditText.text.toString())
                        }

                        listener.onHarvestAdded(cosecha)
                        dismiss()
                    }
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


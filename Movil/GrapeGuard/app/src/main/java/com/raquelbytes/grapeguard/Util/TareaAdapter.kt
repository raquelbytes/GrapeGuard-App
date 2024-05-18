import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.raquelbytes.grapeguard.API.Model.Tarea
import com.raquelbytes.grapeguard.R
import com.raquelbytes.grapeguard.Controller.VinedoActivity

class TareaAdapter(
    private val context: Context,
    private val resource: Int,
    private val tareas: List<Tarea>
) : ArrayAdapter<Tarea>(context, resource, tareas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val tarea = tareas[position]
        val textViewDescripcion = view.findViewById<TextView>(R.id.textViewDescripcionTarea)
        val textViewEstado = view.findViewById<TextView>(R.id.textViewEstadoTarea)
        val textViewRecordatorio = view.findViewById<TextView>(R.id.textViewRecordatorioTarea)
        val textViewFechaRealizacion = view.findViewById<TextView>(R.id.textViewFechaRealizacionTarea)
        val buttonDelete = view.findViewById<ImageButton>(R.id.buttonDelete)

        textViewDescripcion.text = tarea.tarea
        textViewEstado.text = tarea.estado.toString()
        textViewRecordatorio.text = tarea.recordatorio
        textViewFechaRealizacion.text = tarea.fechaRealizacion
        val tareaid = tarea.id ?: -1

        buttonDelete.setOnClickListener {

            (context as VinedoActivity).borrarTarea(tareaid)
        }

        return view
    }
}

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
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
        val textViewFechaRealizacion = view.findViewById<TextView>(R.id.textViewFechaRealizacionTarea)
        val buttonDelete = view.findViewById<ImageButton>(R.id.buttonDelete)
        val imageViewRecordatorio = view.findViewById<ImageView>(R.id.imageViewRecordatorioTarea)

        textViewDescripcion.text = tarea.tarea
        if( tarea.estado.toString().contains("EnProgreso")){
            textViewEstado.text = "En Progreso"
        }
        else{
            textViewEstado.text = tarea.estado.toString()
        }

        textViewFechaRealizacion.text = tarea.fechaRealizacion

        // Verificar si el ImageView es nulo
        if (imageViewRecordatorio != null) {
            // Si no es nulo, establecer la visibilidad según la presencia del recordatorio
            if (tarea.recordatorio != null) {
                imageViewRecordatorio.visibility = View.VISIBLE
            } else {
                imageViewRecordatorio.visibility = View.GONE
            }
        }

        val tareaid = tarea.id ?: -1

        buttonDelete.setOnClickListener {
            (context as VinedoActivity).borrarTarea(tareaid)
        }

        return view
    }


}


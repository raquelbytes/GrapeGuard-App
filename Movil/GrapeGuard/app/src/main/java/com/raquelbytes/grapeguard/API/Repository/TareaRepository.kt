package com.raquelbytes.grapeguard.API.Repository


import android.content.Context
import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.raquelbytes.grapeguard.API.Interface.AgregarTareaCallback
import com.raquelbytes.grapeguard.API.Interface.BorrarTareaCallback
import com.raquelbytes.grapeguard.API.Interface.TareaCallback
import com.raquelbytes.grapeguard.API.Model.Tarea
import com.raquelbytes.grapeguard.Util.ApiMap
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.Result.Companion.success

class TareaRepository {
    companion object {

        fun obtenerTareasPorVinedo(context: Context, idVinedo: Int, callback: TareaCallback) {
            val url = "${ApiMap.BASE_URL}${ApiMap.TAREA_POR_VINEDO_ID.replace("{idVinedo}", idVinedo.toString())}"
            val stringRequest = object : StringRequest(
                Request.Method.GET, url,
                { response ->
                    try {
                        val gson = Gson()
                        val tareas: List<Tarea> = gson.fromJson(response, object : TypeToken<List<Tarea>>() {}.type)

                        for (tarea in tareas) {
                            // Formatear el recordatorio si no es nulo
                            tarea.recordatorio?.let { recordatorio ->
                                val formatoRecordatorio = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                                val recordatorioDate = try {
                                    formatoRecordatorio.parse(recordatorio)
                                } catch (ex: ParseException) {
                                    // Si el recordatorio no está en el formato esperado, intenta analizarlo en otro formato
                                    val otroFormato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                    otroFormato.parse(recordatorio)
                                }

                                val formattedRecordatorio = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(recordatorioDate)
                                tarea.recordatorio = formattedRecordatorio
                            }

// Formatear la fecha de realización si no es nula
                            tarea.fechaRealizacion?.let { fechaRealizacion ->
                                val formatoFechaRealizacion = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                                val fechaRealizacionDate = try {
                                    formatoFechaRealizacion.parse(fechaRealizacion)
                                } catch (ex: ParseException) {
                                    // Si la fecha de realización no está en el formato esperado, intenta analizarla en otro formato
                                    val otroFormato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                    otroFormato.parse(fechaRealizacion)
                                }

                                val formattedFechaRealizacion = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(fechaRealizacionDate)
                                tarea.fechaRealizacion = formattedFechaRealizacion
                            }
                        }

                        callback.onTareasObtenidas(tareas)
                    } catch (e: JsonSyntaxException) {
                        Log.e("Error de JSON", "Error al analizar la respuesta JSON: ${e.message}")
                        callback.onError("Error al analizar la respuesta JSON")
                    }
                },
                { error ->
                    // Maneja errores de la solicitud
                    val errorMessage = if (error.networkResponse != null && error.networkResponse.data != null) {
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    } else {
                        "Error en la solicitud: ${error.message}"
                    }
                    Log.e("Error de solicitud", errorMessage)
                    callback.onError(errorMessage)
                }
            ) {
                override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                    val parsed: String
                    val charset = HttpHeaderParser.parseCharset(response.headers, "utf-8")
                    parsed = String(response.data, Charset.forName(charset))
                    return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response))
                }
            }

            // Agrega la solicitud a la cola de solicitudes Volley
            val queue: RequestQueue = Volley.newRequestQueue(context)
            queue.add(stringRequest)
        }
        fun consultarTareasPendientesPorVinedo(context: Context, idVinedo: Int, callback: TareaCallback) {
            val url = "${ApiMap.BASE_URL}${ApiMap.TAREA_PENDIENTES_POR_VINEDO_ID.replace("{idVinedo}", idVinedo.toString())}"
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    try {
                        val gson = Gson()
                        // Convierte la respuesta JSON en una lista de Tarea
                        val tareas: List<Tarea> = gson.fromJson(response, object : TypeToken<List<Tarea>>() {}.type)
                        // Llama al método de callback con la lista de tareas obtenida
                        callback.onTareasObtenidas(tareas)
                    } catch (e: JsonSyntaxException) {
                        Log.e("Error de JSON", "Error al analizar la respuesta JSON: ${e.message}")
                        callback.onError("Error al analizar la respuesta JSON")
                    }
                },
                { error ->
                    // Maneja errores de la solicitud
                    val errorMessage = if (error.networkResponse != null && error.networkResponse.data != null) {
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    } else {
                        "Error en la solicitud: ${error.message}"
                    }
                    Log.e("Error de solicitud", errorMessage)
                    callback.onError(errorMessage)
                }
            )

            // Agrega la solicitud a la cola de solicitudes Volley
            val queue: RequestQueue = Volley.newRequestQueue(context)
            queue.add(stringRequest)
        }
        fun consultarTareasTerminadasPorVinedo(context: Context, idVinedo: Int, callback: TareaCallback) {
            val url = "${ApiMap.BASE_URL}${ApiMap.TAREA_TERMINADAS_POR_VINEDO_ID.replace("{idVinedo}", idVinedo.toString())}"
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    try {
                        val gson = Gson()
                        // Convierte la respuesta JSON en una lista de Tarea
                        val tareas: List<Tarea> = gson.fromJson(response, object : TypeToken<List<Tarea>>() {}.type)
                        // Llama al método de callback con la lista de tareas obtenida
                        callback.onTareasObtenidas(tareas)
                    } catch (e: JsonSyntaxException) {
                        Log.e("Error de JSON", "Error al analizar la respuesta JSON: ${e.message}")
                        callback.onError("Error al analizar la respuesta JSON")
                    }
                },
                { error ->
                    // Maneja errores de la solicitud
                    val errorMessage = if (error.networkResponse != null && error.networkResponse.data != null) {
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    } else {
                        "Error en la solicitud: ${error.message}"
                    }
                    Log.e("Error de solicitud", errorMessage)
                    callback.onError(errorMessage)
                }
            )

            // Agrega la solicitud a la cola de solicitudes Volley
            val queue: RequestQueue = Volley.newRequestQueue(context)
            queue.add(stringRequest)
        }
        fun borrarTareaDelVinedo(context: Context, idTarea: Int, callback: BorrarTareaCallback) {
            val url = "${ApiMap.BASE_URL}${ApiMap.TAREA_ELIMINAR_POR_ID.replace("{idTarea}", idTarea.toString())}"
            val stringRequest = StringRequest(
                Request.Method.DELETE, url,
                { response ->
                    // Si la solicitud se completa con éxito, llama al método de callback con el mensaje de éxito
                    callback.onTareaBorrada(response)
                },
                { error ->
                    // Maneja errores de la solicitud
                    val errorMessage = if (error.networkResponse != null && error.networkResponse.data != null) {
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    } else {
                        "Error en la solicitud: ${error.message}"
                    }
                    Log.e("Error de solicitud", errorMessage)
                    callback.onError(errorMessage)
                }
            )

            // Agrega la solicitud a la cola de solicitudes Volley
            val queue: RequestQueue = Volley.newRequestQueue(context)
            queue.add(stringRequest)
        }
        fun agregarTareaAlVinedo(context: Context, idVinedo: Int, nuevaTarea: Tarea, callback: AgregarTareaCallback) {
            val url = "${ApiMap.BASE_URL}${ApiMap.TAREA_AGREGAR_POR_VINEDO_ID.replace("{idVinedo}", idVinedo.toString())}"
            val requestBody = Gson().toJson(nuevaTarea)

            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener { response ->
                    // Si la solicitud se completa con éxito, llama al método de callback con el mensaje de éxito
                    callback.onTareaAgregada(response)
                },
                Response.ErrorListener { error ->
                    // Maneja errores de la solicitud
                    val errorMessage = if (error.networkResponse != null && error.networkResponse.data != null) {
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    } else {
                        "Error en la solicitud: ${error.message}"
                    }
                    Log.e("Error de solicitud", errorMessage)
                    callback.onError(errorMessage)
                }
            ) {
                override fun getBody(): ByteArray {
                    // Define el cuerpo de la solicitud POST como el objeto de tarea serializado en formato JSON
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getHeaders(): MutableMap<String, String> {
                    // Especifica los encabezados de la solicitud
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
                    return headers
                }
            }

            // Agrega la solicitud a la cola de solicitudes Volley
            val queue = Volley.newRequestQueue(context)
            queue.add(stringRequest)
        }
    }
}


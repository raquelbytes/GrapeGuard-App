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
import com.raquelbytes.grapeguard.API.Interface.AgregarCosechaCallback
import com.raquelbytes.grapeguard.API.Interface.BorrarCosechaCallback
import com.raquelbytes.grapeguard.API.Interface.CosechaCallback
import com.raquelbytes.grapeguard.API.Model.Cosecha
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Locale


class CosechaRepository {
    companion object {

        fun obtenerCosechasPorVinedo(context: Context, idVinedo: Int, callback: CosechaCallback) {
            val url = "http://192.168.1.132:8080/cosecha/vinedo/$idVinedo"

            val utf8StringRequest = object : StringRequest(Method.GET, url,
                { response ->
                    try {
                        val gson = Gson()

                        // Define el formato de fecha deseado para la deserialización
                        val sdfInput = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val cosechas: List<Cosecha> = gson.fromJson(response, object : TypeToken<List<Cosecha>>() {}.type)

                        cosechas.forEach {
                            // Convertir la fecha al formato deseado
                            it.fechaCosecha = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(sdfInput.parse(it.fechaCosecha))
                        }

                        callback.onCosechasObtenidas(cosechas)
                    } catch (e: JsonSyntaxException) {
                        Log.e("Error de JSON", "Error al analizar la respuesta JSON: ${e.message}")
                        callback.onError("Error al analizar la respuesta JSON")
                    }
                },
                { error ->
                    val errorMessage = if (error.networkResponse != null && error.networkResponse.data != null) {
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    } else {
                        "Error en la solicitud: ${error.message}"
                    }
                    Log.e("Error de solicitud", errorMessage)
                    callback.onError(errorMessage)
                }) {
                override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                    val parsed: String
                    val charset = HttpHeaderParser.parseCharset(response.headers, "utf-8")
                    parsed = String(response.data, Charset.forName(charset))
                    return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response))
                }
            }

            val queue: RequestQueue = Volley.newRequestQueue(context)
            queue.add(utf8StringRequest)
        }


        fun agregarCosecha(context: Context, idVinedo: Int, nuevaCosecha: Cosecha, callback: AgregarCosechaCallback) {
            val url = "http://192.168.1.132:8080/cosecha/vinedo/$idVinedo/cosecha/nuevo"
            val requestBody = Gson().toJson(nuevaCosecha)

            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener { response ->
                    // Si la solicitud se completa con éxito, llama al método de callback con la cosecha agregada
                    val gson = Gson()
                    val cosecha: Cosecha = gson.fromJson(response, Cosecha::class.java)
                    callback.onCosechaAgregada(cosecha)
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
                    // Define el cuerpo de la solicitud POST como el objeto de cosecha serializado en formato JSON
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
        fun borrarCosecha(context: Context, idCosecha: Int, callback: BorrarCosechaCallback) {
            val url = "http://192.168.1.132:8080/cosecha/$idCosecha"
            val stringRequest = StringRequest(
                Request.Method.DELETE, url,
                { response ->
                    // Si la solicitud se completa con éxito, llama al método de callback con el mensaje de éxito
                    callback.onCosechaBorrada(response)
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

    }
}
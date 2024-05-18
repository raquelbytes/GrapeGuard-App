package com.raquelbytes.grapeguard.API.Repository

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.raquelbytes.grapeguard.API.Interface.VinedoCallback
import com.raquelbytes.grapeguard.API.Interface.VinedosCallback
import com.raquelbytes.grapeguard.API.Model.Vinedo
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class VinedoRepository {
    companion object {
        fun obtenerVinedosPorUsuario(context: Context, usuarioId: Int, callBack: VinedosCallback) {
            val url = "http://192.168.1.132:8080/vinedo/usuario/$usuarioId"

            val utf8StringRequest = object : StringRequest(Method.GET, url,
                Response.Listener { response ->
                    Log.d("Vinedos Response", response)
                    val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()
                    val vinedos = gson.fromJson(response, Array<Vinedo>::class.java).toList()
                    callBack.onVinedosObtenidos(vinedos)
                },
                Response.ErrorListener { error ->
                    Log.e("Vinedos Error", "Error en la solicitud: ${error.toString()}")
                    callBack.onVinedoError(error.toString())
                }) {
                override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                    val parsed: String
                    val charset = HttpHeaderParser.parseCharset(response.headers, "utf-8")
                    parsed = String(response.data, Charset.forName(charset))
                    return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response))
                }
            }

            Volley.newRequestQueue(context).add(utf8StringRequest)
        }

        fun obtenerVinedoPorId(context: Context, vinedoId: Int, callBack: VinedoCallback) {
            val url = "http://192.168.1.132:8080/vinedo/$vinedoId"
            val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    Log.d("Vinedo Response", response)
                    val gson = Gson()
                    val vinedo = gson.fromJson(response, Vinedo::class.java)
                    callBack.onVinedoObtenido(vinedo)
                },
                { error ->
                    Log.e("Vinedo Error", "Error en la solicitud: ${error.toString()}")
                    callBack.onVinedoError(error.toString())
                })

            val queue: RequestQueue = Volley.newRequestQueue(context)
            queue.add(stringRequest)
        }

        fun agregarNuevoVinedo(context: Context, vinedo: Vinedo, callback: VinedoCallback) {
            val url = "http://192.168.1.132:8080/vinedo/usuario/${vinedo.usuarioId}/nuevo"
            val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener { response -> callback.onVinedoAgregado(response) },
                Response.ErrorListener { error ->
                    var errorMessage = "Error desconocido"
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        errorMessage = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    }
                    callback.onVinedoError(errorMessage)
                }) {
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val payload = gson.toJson(vinedo)
                    return payload.toByteArray(StandardCharsets.UTF_8)
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json; charset=utf-8"
                    return headers
                }
            }

            val queue = Volley.newRequestQueue(context)
            queue.add(stringRequest)
        }
        fun modificarVinedo(context: Context, vinedo: Vinedo, callback: VinedoCallback) {
            val url = "http://192.168.1.132:8080/vinedo/${vinedo.id}"
            val stringRequest = object : StringRequest(Request.Method.PUT, url,
                Response.Listener { response -> callback.onVinedoModificado(response) },
                Response.ErrorListener { error ->
                    var errorMessage = "Error desconocido"
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        errorMessage = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    }
                    callback.onVinedoError(errorMessage)
                }) {
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val payload = gson.toJson(vinedo)
                    return payload.toByteArray(StandardCharsets.UTF_8)
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json; charset=utf-8"
                    return headers
                }
            }

            val queue = Volley.newRequestQueue(context)
            queue.add(stringRequest)
        }

        fun borrarVinedo(context: Context, vinedoId: Int, callback: VinedoCallback) {
            val url = "http://192.168.1.132:8080/vinedo/$vinedoId"
            val stringRequest = StringRequest(Request.Method.DELETE, url,
                { response -> callback.onVinedoBorrado(response) },
                { error ->
                    var errorMessage = "Error desconocido"
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        errorMessage = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    }
                    callback.onVinedoError(errorMessage)
                })

            val queue: RequestQueue = Volley.newRequestQueue(context)
            queue.add(stringRequest)
        }

        fun obtenerSumHectareasPorUsuario(context: Context, usuarioId: Int, callback: VinedoCallback) {
            val url = "http://192.168.1.132:8080/vinedo/sum-hectareas/$usuarioId"
            val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    Log.d("Suma Hectareas Response", response)
                    val sumaHectareas = response.toBigDecimalOrNull()
                    if (sumaHectareas != null) {
                        callback.onSumaHectareasObtenida(sumaHectareas)
                    } else {
                        callback.onVinedoError("Error al obtener la suma de hectáreas")
                    }
                },
                { error ->
                    Log.e("Suma Hectareas Error", "Error en la solicitud: ${error.toString()}")
                    callback.onVinedoError(error.toString())
                })

            val queue: RequestQueue = Volley.newRequestQueue(context)
            queue.add(stringRequest)
        }


    }
}

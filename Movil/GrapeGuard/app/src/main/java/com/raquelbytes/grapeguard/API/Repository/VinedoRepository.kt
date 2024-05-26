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
import com.raquelbytes.grapeguard.API.Interface.VinedoObtenidoCallback
import com.raquelbytes.grapeguard.API.Interface.VinedosCallback
import com.raquelbytes.grapeguard.API.Model.Vinedo
import com.raquelbytes.grapeguard.Util.ApiMap
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class VinedoRepository {
    companion object {
        fun obtenerVinedosPorUsuario(context: Context, usuarioId: Int, callBack: VinedosCallback) {
            Log.d("UsuarioId", usuarioId.toString())
            val url = "${ApiMap.BASE_URL}${ApiMap.VINEDO_USUARIO_ID.replace("{ID_usuario}", usuarioId.toString())}"

            val utf8StringRequest = object : StringRequest(Request.Method.GET, url,
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

        fun obtenerVinedoPorId(context: Context, vinedoId: Int, callBack: VinedoObtenidoCallback) {
            val url = "${ApiMap.BASE_URL}${ApiMap.VINEDO_ID.replace("{ID_vinedo}", vinedoId.toString())}"
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

        fun agregarNuevoVinedo(context: Context, usuarioId: Int, vinedo: Vinedo, callback: VinedoCallback) {
            val url = "${ApiMap.BASE_URL}${ApiMap.VINEDO_USUARIO_NUEVO.replace("{ID_usuario}", usuarioId.toString())}"
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
    }
}

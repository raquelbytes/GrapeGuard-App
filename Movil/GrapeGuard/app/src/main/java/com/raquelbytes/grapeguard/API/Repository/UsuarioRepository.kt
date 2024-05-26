package com.raquelbytes.grapeguard.API.Repository

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.raquelbytes.grapeguard.API.Interface.UserFindCallback
import com.raquelbytes.grapeguard.API.Interface.UserLoginCallback
import com.raquelbytes.grapeguard.API.Interface.UserRegisterCallback
import com.raquelbytes.grapeguard.API.Interface.UserUpdateCallback
import com.raquelbytes.grapeguard.API.Model.Usuario
import com.raquelbytes.grapeguard.Util.ApiMap
import java.nio.charset.StandardCharsets

class UsuarioRepository {
    companion object {
        data class LoginResponse(
            val usuario: Usuario,
            val mensaje: String
        )

        fun loginUsuario(context: Context, email: String, contrasena: String, callBack: UserLoginCallback) {
            val url = "${ApiMap.BASE_URL}${ApiMap.USUARIO_BASE}${ApiMap.USUARIO_LOGIN}"
            val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener { response ->
                    Log.d("Login Response", response)
                    try {
                        val gson = Gson()
                        val loginResponse = gson.fromJson(response, LoginResponse::class.java)
                        callBack.onLoginSuccess(loginResponse.usuario)
                    } catch (e: JsonSyntaxException) {
                        Log.e("Login Error", "Error parsing response: ${e.message}")
                        callBack.onLoginError("Error parsing response")
                    }
                },
                Response.ErrorListener { error ->
                    val errorMessage = if (error is AuthFailureError) {
                        "Usuario o contraseña incorrectos"
                    } else {
                        "Error en la solicitud: ${error.message}"
                    }
                    Log.e("Login Error", errorMessage)
                    callBack.onLoginError(errorMessage)
                }) {
                override fun getBody(): ByteArray {
                    val params = HashMap<String, String>()
                    params["email"] = email
                    params["contrasena"] = contrasena
                    val requestBody = Gson().toJson(params)
                    Log.d("RequestBody", requestBody)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json; charset=utf-8"
                    return headers
                }
            }
            val queue: RequestQueue = Volley.newRequestQueue(context)
            queue.add(stringRequest)
        }

        fun registrarUsuario(context: Context, usuario: Usuario, callback: UserRegisterCallback) {
            val url = "${ApiMap.BASE_URL}${ApiMap.USUARIO_BASE}${ApiMap.USUARIO_REGISTER}"
            val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener { response -> callback.onUserRegisterSuccess(response) },
                Response.ErrorListener { error ->
                    var errorMessage = "Error desconocido"
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        errorMessage = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    }
                    callback.onUserRegisterFailed(errorMessage)
                }) {
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val payload = gson.toJson(usuario)
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


        fun modificarUsuario(context: Context, usuario: Usuario, idUsuario: Int, callback: UserUpdateCallback) {
            val url = "${ApiMap.BASE_URL}${ApiMap.USUARIO_BASE}${ApiMap.USUARIO_MODIFICAR.replace("{ID_usuario}", idUsuario.toString())}"
            val stringRequest = object : StringRequest(Request.Method.PUT, url,
                Response.Listener { response -> callback.onUserUpdateSuccess(response) },
                Response.ErrorListener { error ->
                    var errorMessage = "Error desconocido"
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        errorMessage = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    }
                    callback.onUserUpdateFailed(errorMessage)
                }) {
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val payload = gson.toJson(usuario)
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

        fun obtenerUsuarioPorId(context: Context, idUsuario: Int, callback: UserFindCallback) {
            val url = "${ApiMap.BASE_URL}${ApiMap.USUARIO_BASE}${ApiMap.USUARIO_POR_ID.replace("{ID_usuario}", idUsuario.toString())}"
            val stringRequest = object : StringRequest(Request.Method.GET, url,
                Response.Listener { response ->
                    // Manejar la respuesta exitosa aquí
                    Log.d("Obtener Usuario", response)
                    try {
                        val gson = Gson()
                        val usuario = gson.fromJson(response, Usuario::class.java)
                        callback.onUserUpdateSuccess(usuario)
                    } catch (e: JsonSyntaxException) {
                        Log.e("Obtener Usuario", "Error al parsear la respuesta: ${e.message}")
                        callback.onUserUpdateFailed("Error al obtener el usuario")
                    }
                },
                Response.ErrorListener { error ->
                    // Manejar errores de red aquí
                    Log.e("Obtener Usuario", "Error en la solicitud: ${error.message}")
                    callback.onUserUpdateFailed("Error al obtener el usuario")
                }) {

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

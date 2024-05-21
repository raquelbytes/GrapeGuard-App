package com.raquelbytes.grapeguard.API.Repository

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.raquelbytes.grapeguard.API.Interface.UserLoginCallback
import com.raquelbytes.grapeguard.API.Interface.UserRegisterCallback
import com.raquelbytes.grapeguard.API.Model.Usuario
import java.nio.charset.StandardCharsets

class UsuarioRepository {
    companion object {
        data class LoginResponse(
            val usuario: Usuario,
            val mensaje: String
        )
        fun loginUsuario(context: Context, email: String, contrasena: String, callBack: UserLoginCallback) {
            val url = "http://192.168.1.141:8080/usuarios/login"
            val stringRequest = object : StringRequest(Method.POST, url,
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
            val url = "http://192.168.1.141:8080/usuarios/register"
            val stringRequest = object : StringRequest(Method.POST, url,
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
    }
}

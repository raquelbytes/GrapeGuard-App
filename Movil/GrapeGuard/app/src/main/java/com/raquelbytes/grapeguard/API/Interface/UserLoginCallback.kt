package com.raquelbytes.grapeguard.API.Interface

import com.raquelbytes.grapeguard.API.Model.Usuario

interface UserLoginCallback {
    fun onLoginSuccess(usuario: Usuario)
    fun onLoginError(message: String)
}

package com.raquelbytes.grapeguard.API.Interface

import com.raquelbytes.grapeguard.API.Model.Usuario

interface UserFindCallback {
    fun onUserUpdateSuccess(usuario: Usuario)
    fun onUserUpdateFailed(error: String)
}

package com.raquelbytes.grapeguard.API.Interface

interface UserRegisterCallback {
    fun onUserRegisterSuccess(response: String)
    fun onUserRegisterFailed(error: String)
}

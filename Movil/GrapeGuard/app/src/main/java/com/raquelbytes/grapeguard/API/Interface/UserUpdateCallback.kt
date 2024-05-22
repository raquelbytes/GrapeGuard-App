package com.raquelbytes.grapeguard.API.Interface

interface UserUpdateCallback {
    fun onUserUpdateSuccess(response: String)
    fun onUserUpdateFailed(error: String)
}

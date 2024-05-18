package com.raquelbytes.grapeguard.API.Model

class Usuario {
    val id_usuario: Int? = null
    var nombre: String? = null
    var apellido: String? = null
    var email: String? = null
    var contrasena: String? = null
    var foto: String? = null

    constructor()

    constructor(nombre: String, apellido: String, email: String, contrasena: String, foto: String) {
        this.nombre = nombre
        this.apellido = apellido
        this.email = email
        this.contrasena = contrasena
        this.foto = foto
    }
}

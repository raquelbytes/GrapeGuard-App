package com.raquelbytes.grapeguard.SQLite

class Usuario {
        var id: Int? = null
        var nombre: String? = null
        var apellido: String? = null
        var foto: String? = null

    constructor()

    constructor(id: Int, nombre: String, apellido: String, foto: String) {
        this.id = id
        this.nombre = nombre
        this.apellido = apellido
        this.foto = foto
    }
}
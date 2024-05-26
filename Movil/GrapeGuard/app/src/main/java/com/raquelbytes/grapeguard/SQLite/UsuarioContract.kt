package com.raquelbytes.grapeguard.SQLite
import android.provider.BaseColumns

object UsuarioContract {

    object FeedEntry : BaseColumns {
        const val NOMBRE_TABLA = "Usuario"
        const val NOMBRE_COLUMNA_ID = "id"
        const val NOMBRE_COLUMNA_NOMBRE = "nombre"
        const val NOMBRE_COLUMNA_APELLIDO = "apellido"
        const val NOMBRE_COLUMNA_FOTO = "foto"
    }
}

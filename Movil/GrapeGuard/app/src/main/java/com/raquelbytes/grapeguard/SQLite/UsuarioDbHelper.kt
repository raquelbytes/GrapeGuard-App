package com.raquelbytes.grapeguard.SQLite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper





class UsuarioDbHelper(context: Context) : SQLiteOpenHelper(context, NOMBRE_DATABASE, null, VERSION_DATABASE) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREAR_ENTRADAS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_BORRAR_ENTRADAS)
        onCreate(db)
    }

    companion object {
        private var instancia: UsuarioDbHelper? = null
        private const val VERSION_DATABASE = 1
        const val NOMBRE_DATABASE = "UsuarioDB.db"

        private const val SQL_CREAR_ENTRADAS =
            "CREATE TABLE ${UsuarioContract.FeedEntry.NOMBRE_TABLA} (" +
                    "${UsuarioContract.FeedEntry.NOMBRE_COLUMNA_ID} INTEGER PRIMARY KEY," +
                    "${UsuarioContract.FeedEntry.NOMBRE_COLUMNA_NOMBRE} TEXT," +
                    "${UsuarioContract.FeedEntry.NOMBRE_COLUMNA_APELLIDO} TEXT," +
                    "${UsuarioContract.FeedEntry.NOMBRE_COLUMNA_FOTO} TEXT)"

        private const val SQL_BORRAR_ENTRADAS =
            "DROP TABLE IF EXISTS ${UsuarioContract.FeedEntry.NOMBRE_TABLA}"

        @Synchronized
        fun getInstance(context: Context): UsuarioDbHelper {
            if (instancia == null) {
                instancia = UsuarioDbHelper(context.applicationContext)
            }
            return instancia!!
        }
    }
}





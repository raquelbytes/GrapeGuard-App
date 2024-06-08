package com.raquelbytes.grapeguard.SQLite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UsuarioDAO(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UsuarioDB.db"
        private const val TABLE_NAME = "Usuario"

        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_APELLIDO = "apellido"
        private const val COLUMN_FOTO = "foto"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NOMBRE TEXT, $COLUMN_APELLIDO TEXT, $COLUMN_FOTO TEXT)")
        db?.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertarUsuario(db: SQLiteDatabase, id: Int, nombre: String, apellido: String, foto: String) {
        val usuarioExistente = obtenerUsuario(id)
        if (usuarioExistente != null) {
            // Si el usuario ya existe, actualiza sus datos
            actualizarUsuarioDatos(id, nombre, apellido, foto)
        } else {
            // Si el usuario no existe, lo inserta como un nuevo usuario
            val contentValues = ContentValues()
            contentValues.put(COLUMN_ID, id)
            contentValues.put(COLUMN_NOMBRE, nombre)
            contentValues.put(COLUMN_APELLIDO, apellido)
            contentValues.put(COLUMN_FOTO, foto)
            db.insert(TABLE_NAME, null, contentValues)
        }
    }

    private fun actualizarUsuarioDatos(id: Int, nombre: String, apellido: String, foto: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NOMBRE, nombre)
        contentValues.put(COLUMN_APELLIDO, apellido)
        contentValues.put(COLUMN_FOTO, foto)
        db.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }



    @SuppressLint("Range")
    fun obtenerUsuario(idUsuario: Int): Usuario? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $idUsuario"
        val cursor = db.rawQuery(query, null)
        var usuario: Usuario? = null

        if (cursor.moveToFirst()) {
            usuario = Usuario(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                apellido = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APELLIDO)),
                foto = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOTO))
            )
        }

        cursor.close()
        db.close()
        return usuario
    }

    fun borrarTablaUsuarios() {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.close()
    }
    fun actualizarUsuario(usuario: Usuario) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NOMBRE, usuario.nombre)
        contentValues.put(COLUMN_APELLIDO, usuario.apellido)
        contentValues.put(COLUMN_FOTO, usuario.foto)
        db.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(usuario.id.toString()))
        db.close()
    }


}

package com.example.vacinacao

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Paciente (var id: Long = -1 , var nome: String) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaPaciente.NOME_PACIENTE, nome)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Paciente {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaPaciente.NOME_PACIENTE)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)

            return Paciente(id, nome)
        }
    }
}
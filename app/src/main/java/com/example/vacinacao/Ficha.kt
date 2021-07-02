package com.example.vacinacao

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Ficha (var id: Long = -1, var data: String , var hora: String , var efeitos: String , var idPaciente: Long , var idVacina: Long) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaFicha.CAMPO_DATA, data)
            put(TabelaFicha.CAMPO_HORA, hora)
            put(TabelaFicha.CAMPO_EFEITOS, efeitos)
            put(TabelaFicha.CAMPO_ID_PACIENTE, idPaciente)
            put(TabelaFicha.CAMPO_ID_VACINA, idVacina)


        }
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Ficha {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colData = cursor.getColumnIndex(TabelaFicha.CAMPO_DATA)
            val colHora = cursor.getColumnIndex(TabelaFicha.CAMPO_HORA)
            val colEfeitos = cursor.getColumnIndex(TabelaFicha.CAMPO_EFEITOS)
            val colIdPaciente = cursor.getColumnIndex(TabelaFicha.CAMPO_ID_PACIENTE)
            val colIdVacina = cursor.getColumnIndex(TabelaFicha.CAMPO_ID_VACINA)


            val id = cursor.getLong(colId)
            val data = cursor.getString(colData)
            val hora = cursor.getString(colHora)
            val efeitos = cursor.getString(colEfeitos)
            val idPaciente = cursor.getLong(colIdPaciente)
            val idVacina = cursor.getLong(colIdVacina)


            return Ficha (id, data, hora, efeitos, idPaciente, idVacina)
        }
    }
}
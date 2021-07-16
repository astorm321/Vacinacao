package com.example.vacinacao

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Ficha (var id: Long = -1, var data: String , var hora: String , var efeitos: String , var idPaciente: Long , var idVacina: Long ,var nomePaciente: String? = null , var nomeVacina: String? = null) {
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
            val colNomePaci = cursor.getColumnIndex(TabelaFicha.CAMPO_EXTERNO_NOME_PACIENTE)
            val colNomeVaci = cursor.getColumnIndex(TabelaFicha.CAMPO_EXTERNO_NOME_VACINA)


            val id = cursor.getLong(colId)
            val data = cursor.getString(colData)
            val hora = cursor.getString(colHora)
            val efeitos = cursor.getString(colEfeitos)
            val idPaciente = cursor.getLong(colIdPaciente)
            val idVacina = cursor.getLong(colIdVacina)


            val nomePaciente = if (colNomePaci!= -1) cursor.getString(colNomePaci) else null
            val nomeVacina = if (colNomeVaci!= -1) cursor.getString(colNomeVaci) else null


            return Ficha (id, data, hora, efeitos, idPaciente , idVacina , nomePaciente , nomeVacina)
        }
    }
}
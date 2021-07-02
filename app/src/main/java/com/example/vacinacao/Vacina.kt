package com.example.vacinacao

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Vacina (var id: Long = -1, var nomeVacina: String , var fabricante: String , var validade: Long , var dose: Int){
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaVacina.CAMPO_NOME_VACINA, nomeVacina)
            put(TabelaVacina.CAMPO_FABRICANTE, fabricante)
            put(TabelaVacina.CAMPO_VALIDADE, validade)
            put(TabelaVacina.CAMPO_DOSE, dose)
        }


        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Vacina {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNomeVacina = cursor.getColumnIndex(TabelaVacina.CAMPO_NOME_VACINA)
            val colFabricante = cursor.getColumnIndex(TabelaVacina.CAMPO_FABRICANTE)
            val colValidade = cursor.getColumnIndex(TabelaVacina.CAMPO_VALIDADE)
            val colDose = cursor.getColumnIndex(TabelaVacina.CAMPO_DOSE)





            val id = cursor.getLong(colId)
            val nomeVacina = cursor.getString(colNomeVacina)
            val fabricante = cursor.getString(colFabricante)
            val validade = cursor.getLong(colValidade)
            val dose = cursor.getInt(colDose)


            return Vacina(id, nomeVacina , fabricante , validade , dose)
        }
    }
}
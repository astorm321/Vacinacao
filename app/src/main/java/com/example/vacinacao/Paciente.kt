package com.example.vacinacao

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Paciente (var id: Long = -1 , var nome: String , var morada: String, var contacto: Long , var NrUtente: Long , var altura: Long, var peso: Long, var DataNascimento: Long ) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaPaciente.NOME_PACIENTE, nome)
            put(TabelaPaciente.CAMPO_MORADA, morada)
            put(TabelaPaciente.CAMPO_CONTACTO, contacto)
            put(TabelaPaciente.CAMPO_NR_UTENTE, NrUtente)
            put(TabelaPaciente.CAMPO_ALTURA, altura)
            put(TabelaPaciente.CAMPO_PESO, peso)
            put(TabelaPaciente.CAMPO_DATA_NASCIMENTO, DataNascimento)


        }
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Paciente {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaPaciente.NOME_PACIENTE)
            val colMorada = cursor.getColumnIndex(TabelaPaciente.CAMPO_MORADA)
            val colContacto = cursor.getColumnIndex(TabelaPaciente.CAMPO_CONTACTO)
            val colNrUtente = cursor.getColumnIndex(TabelaPaciente.CAMPO_NR_UTENTE)
            val colAltura = cursor.getColumnIndex(TabelaPaciente.CAMPO_ALTURA)
            val colPeso = cursor.getColumnIndex(TabelaPaciente.CAMPO_PESO)
            val colDataNascimento = cursor.getColumnIndex(TabelaPaciente.CAMPO_DATA_NASCIMENTO)




            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)
            val morada = cursor.getString(colMorada)
            val contacto = cursor.getLong(colContacto)
            val NrUtente = cursor.getLong(colNrUtente)
            val altura = cursor.getLong(colAltura)
            val peso = cursor.getLong(colPeso)
            val DataNascimento = cursor.getLong(colDataNascimento)

            return Paciente(id, nome , morada , contacto , NrUtente , altura , peso , DataNascimento)
        }
    }
}
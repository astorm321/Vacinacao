package com.example.vacinacao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPaciente (db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db
    fun cria(){
        db.execSQL(
                "CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_MORADA TEXT NOT NULL ,$CAMPO_CONTACTO TEXT NOT NULL UNIQUE, $CAMPO_IDADE TEXT NOT NULL UNIQUE,$CAMPO_NR_UTENTE TEXT NOT NULL , $CAMPO_ALTURA TEXT NOT NULL,$CAMPO_PESO TEXT NOT NULL ,$CAMPO_FAIXA_ETARIA TEXT NOT NULL,$CAMPO_PROFISSAO TEXT NOT NULL, $CAMPO_HISTORICO TEXT NOT NULL)")
    }

    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABELA, null , values)
    }
    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>):Int {
        return db.update(NOME_TABELA, values, whereClause , whereArgs)

    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(NOME_TABELA,whereClause , whereArgs)
    }

    fun query(columns: Array<String>,
              selection: String,
              selectionArgs: Array<String>,
              groupBy: String,
              having: String,
              orderBy: String
    ) : Cursor? {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }


    companion object {
        const val NOME_TABELA = "Paciente"
        const val CAMPO_MORADA ="Morada"
        const val CAMPO_CONTACTO ="Contacto"
        const val CAMPO_IDADE = "Idade"
        const val CAMPO_NR_UTENTE ="NrUtente"
        const val CAMPO_ALTURA ="Altura"
        const val CAMPO_PESO ="Peso"
        const val CAMPO_FAIXA_ETARIA="FaixaEtaria"
        const val CAMPO_PROFISSAO ="Profissão"
        const val CAMPO_HISTORICO ="HistoricoMedico"

    }
}
package com.example.vacinacao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPaciente (db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db
    fun cria(){
        db.execSQL(
                "CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_MORADA TEXT NOT NULL ,$CAMPO_CONTACTO NUMERIC NOT NULL UNIQUE,$CAMPO_NR_UTENTE NUMERIC NOT NULL , $CAMPO_ALTURA NUMERIC NOT NULL,$CAMPO_PESO NUMERIC NOT NULL ,$CAMPO_DATA_NASCIMENTO DATE NOT NULL ,$NOME_PACIENTE TEXT NOT NULL)")
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
              selection: String?,
              selectionArgs: Array<String>?,
              groupBy: String?,
              having: String?,
              orderBy: String?
    ) : Cursor? {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }


    companion object {
        const val NOME_TABELA = "Paciente"
        const val NOME_PACIENTE ="Nome_Paciente"  //falta o nome
        const val CAMPO_MORADA ="Morada"
        const val CAMPO_CONTACTO ="Contacto"
        const val CAMPO_NR_UTENTE ="NrUtente"
        const val CAMPO_ALTURA ="Altura"
        const val CAMPO_PESO ="Peso"
        const val CAMPO_DATA_NASCIMENTO ="Data_Nascimento"
        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, NOME_PACIENTE , CAMPO_MORADA , CAMPO_CONTACTO, CAMPO_NR_UTENTE, CAMPO_ALTURA , CAMPO_PESO , CAMPO_DATA_NASCIMENTO)

    }
}
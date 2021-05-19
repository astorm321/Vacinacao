package com.example.vacinacao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BdVacinacaoOpenHelper(context: Context?)
    :SQLiteOpenHelper(context, NOME_BASE_DADOS, null , VERSAO_BASE_DADOS){

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            TabelaPaciente(db).cria()
            TabelaVacina(db).cria()
        }
    }



    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


    companion object {
        const val NOME_BASE_DADOS = "livros.db"
        const val VERSAO_BASE_DADOS = 1
    }
}
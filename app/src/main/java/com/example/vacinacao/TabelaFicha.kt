package com.example.vacinacao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaFicha(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db
    fun cria (){
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT ,$CAMPO_DATA  REAL NOT NULL ,$CAMPO_HORA REAL NOT NULL ,$CAMPO_EFEITOS TEXT NOT NULL, $CAMPO_ID_VACINA INTEGER NOT NULL , $CAMPO_ID_PACIENTE INTEGER NOT NULL , FOREIGN KEY($CAMPO_ID_PACIENTE) REFERENCES ${TabelaPaciente.NOME_TABELA} , FOREIGN KEY($CAMPO_ID_VACINA) REFERENCES ${TabelaVacina.NOME_TABELA})")
    }

    /* $CAMPO_ID_VACINA INTEGER NOT NULL , FOREIGN KEY($CAMPO_ID_VACINA) REFERENCES ${TabelaVacina.NOME_TABELA}) */
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
        //return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
        val ultimaColuna = columns.size - 1

        var posColNomePaciente = -1 // -1 indica que a coluna não foi pedida
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_PACIENTE) {
                posColNomePaciente = i
                break
            }
        }


        if (posColNomePaciente == -1) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var colunas = ""
        for (i in 0..ultimaColuna) {
            if (i > 0) colunas += ","

            colunas += if (i == posColNomePaciente) {
                "${TabelaPaciente.NOME_TABELA}.${TabelaPaciente.NOME_PACIENTE} AS $CAMPO_EXTERNO_NOME_PACIENTE"
                "${TabelaVacina.NOME_TABELA}.${TabelaVacina.CAMPO_NOME_VACINA} AS $CAMPO_EXTERNO_NOME_VACINA"
            } else {
                "${NOME_TABELA}.${columns[i]}"
            }
        }

        var posColNomeVacina = -1 // -1 indica que a coluna não foi pedida
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_VACINA) {
                posColNomeVacina = i
                break
            }
        }
        if (posColNomeVacina == -1) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
        }
        /*var colunas1 = ""
        for (i in 0..ultimaColuna) {
            if (i > 0) colunas1 += ","

            colunas1 += if (i == posColNomeVacina) {
                "${TabelaVacina.NOME_TABELA}.${TabelaVacina.CAMPO_NOME_VACINA} AS $CAMPO_EXTERNO_NOME_VACINA"
            } else {
                "${NOME_TABELA}.${columns[i]}"
            }
        }
        */
        val tabelas = "$NOME_TABELA INNER JOIN ${TabelaPaciente.NOME_TABELA} ON ${TabelaPaciente.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_PACIENTE"

        var sql = "SELECT $colunas FROM $tabelas"

        if (selection != null) sql += " WHERE $selection"

        if (groupBy != null) {
            sql += " GROUP BY $groupBy"
            if (having != null) " HAVING $having"
        }

        if (orderBy != null) sql += " ORDER BY $orderBy"

        return db.rawQuery(sql, selectionArgs)
    }


    companion object {
        const val NOME_TABELA = "Ficha_Tecnica"
        const val CAMPO_DATA ="Data"
        const val CAMPO_HORA ="Hora"
        const val CAMPO_EFEITOS ="Efeitos"
        const val CAMPO_ID_PACIENTE ="id_paciente"
        const val CAMPO_ID_VACINA ="id_vacina"
        const val CAMPO_EXTERNO_NOME_PACIENTE = "nome_paciente"
        const val CAMPO_EXTERNO_NOME_VACINA = "nome_vacina"

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_DATA, CAMPO_HORA , CAMPO_EFEITOS , CAMPO_ID_PACIENTE , CAMPO_ID_VACINA)
    }

}
package com.example.vacinacao

import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TesteBaseDados {
    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBdVacinacaoOpenHelper() = BdVacinacaoOpenHelper(getAppContext())

    private fun inserePaciente(tabela: TabelaPaciente, paciente: Paciente): Long {
        val id = tabela.insert(paciente.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun getPacienteBaseDados(tabela: TabelaPaciente, id: Long): Paciente {
        val cursor = tabela.query(
            TabelaPaciente.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Paciente.fromCursor(cursor)
    }


        @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BdVacinacaoOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val db = getBdVacinacaoOpenHelper().readableDatabase
        assert(db.isOpen)
        db.close()
    }

    @Test
    fun consegueInserirPaciente() {
        val db = getBdVacinacaoOpenHelper().writableDatabase
        val tabelaPaciente = TabelaPaciente(db)

        val paciente = Paciente(
            nome = "Jose" ,
            morada = "Rua Das Flores",
            contacto = "963493871" ,
            NrUtente = "222222222",
            altura = "128",
            peso ="70",
            DataNascimento = "16/12/1996"

            )
        paciente.id = inserePaciente(tabelaPaciente, paciente)

        assertEquals(paciente, getPacienteBaseDados(tabelaPaciente, paciente.id))

        db.close()
    }

    @Test
    fun consegueAlterarPaciente() {
        val db = getBdVacinacaoOpenHelper().writableDatabase
        val tabelaPaciente = TabelaPaciente(db)

        val paciente = Paciente(
            nome = "Jose" ,
            morada = "Rua Das Flores",
            contacto = "963493871" ,
            NrUtente = "222222222",
            altura = "128",
            peso ="70",
            DataNascimento = "16/12/1996"
        )
        paciente.id = inserePaciente(tabelaPaciente, paciente)

        paciente.nome = "Joao"

        val registosAlterados = tabelaPaciente.update(
            paciente.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(paciente.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(paciente, getPacienteBaseDados(tabelaPaciente, paciente.id))

        db.close()
    }

    @Test
    fun consegueEliminarPaciente() {
        val db = getBdVacinacaoOpenHelper().writableDatabase
        val tabelaPaciente = TabelaPaciente(db)

        val paciente = Paciente(
            nome = "Fernando",
            morada = "Rua Das Flores",
            contacto = "963493871" ,
            NrUtente = "222222222",
            altura = "128",
            peso ="70",
            DataNascimento = "16/12/1996"

        )
        paciente.id = inserePaciente(tabelaPaciente, paciente)

        val registosEliminados = tabelaPaciente.delete(
            "${BaseColumns._ID}=?",
            arrayOf(paciente.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerCategorias() {
        val db = getBdVacinacaoOpenHelper().writableDatabase
        val tabelaPaciente = TabelaPaciente(db)

        val paciente = Paciente(
            nome = "Antonio",
            morada = "Rua Das Flores",
            contacto = "963493871" ,
            NrUtente = "222222222",
            altura = "128",
            peso ="70",
            DataNascimento = "16/12/1996"
        )
        paciente.id = inserePaciente(tabelaPaciente, paciente)

        assertEquals(paciente, getPacienteBaseDados(tabelaPaciente, paciente.id))

        db.close()
    }
}
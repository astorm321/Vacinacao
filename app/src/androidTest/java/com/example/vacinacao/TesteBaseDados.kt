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

    private fun insereVacina(tabela: TabelaVacina, vacina: Vacina): Long {
        val id = tabela.insert(vacina.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun insereFicha(tabela: TabelaFicha, ficha: Ficha): Long {
        val id = tabela.insert(ficha.toContentValues())
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

    private fun getVacinaBaseDados(tabela: TabelaVacina, id: Long): Vacina {
        val cursor = tabela.query(
            TabelaVacina.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Vacina.fromCursor(cursor)
    }

    private fun getFichaBaseDados(tabela: TabelaFicha, id: Long): Ficha {
        val cursor = tabela.query(
            TabelaFicha.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Ficha.fromCursor(cursor)
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
    fun consegueLerPaciente() {
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

    @Test
    fun consegueInserirVacina() {
        val db = getBdVacinacaoOpenHelper().writableDatabase
        val tabelaVacina = TabelaVacina(db)

        val vacina = Vacina(
            nomeVacina = "BioNtech" ,
            fabricante = "Pfizer",
            validade = "20/12/2022" ,
            dose = "2"
        )
        vacina.id = insereVacina(tabelaVacina, vacina)

        assertEquals(vacina, getVacinaBaseDados(tabelaVacina, vacina.id))

        db.close()
    }

    @Test
    fun consegueAlterarVacina() {
        val db = getBdVacinacaoOpenHelper().writableDatabase
        val tabelaVacina = TabelaVacina(db)

        val vacina = Vacina(
            nomeVacina = "asbbasd" ,
            fabricante = "Pfizer",
            validade = "20/12/2022" ,
            dose = "2"
        )
        vacina.id = insereVacina(tabelaVacina, vacina)

        vacina.nomeVacina = "BioNTech"

        val registosAlterados = tabelaVacina.update(
            vacina.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(vacina, getVacinaBaseDados(tabelaVacina, vacina.id))

        db.close()
    }
    @Test
    fun consegueEliminarVacina() {
        val db = getBdVacinacaoOpenHelper().writableDatabase
        val tabelaVacina = TabelaVacina(db)

        val vacina = Vacina(
            nomeVacina = "BioNtech" ,
            fabricante = "Pfizer",
            validade = "20/12/2022" ,
            dose = "2"

        )
        vacina.id = insereVacina(tabelaVacina, vacina)

        val registosEliminados = tabelaVacina.delete(
            "${BaseColumns._ID}=?",
            arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerVacina() {
        val db = getBdVacinacaoOpenHelper().writableDatabase
        val tabelaVacina = TabelaVacina(db)

        val vacina = Vacina(
            nomeVacina = "BioNtech" ,
            fabricante = "Pfizer",
            validade = "20/12/2022" ,
            dose = "2"
        )
        vacina.id = insereVacina(tabelaVacina, vacina)

        assertEquals(vacina, getVacinaBaseDados(tabelaVacina, vacina.id))

        db.close()
    }

    @Test
    fun consegueInserirFicha() {
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

        val tabelaVacina = TabelaVacina(db)
        val vacina = Vacina(
            nomeVacina = "BioNtech" ,
            fabricante = "Pfizer",
            validade = "20/12/2022" ,
            dose = "2"
        )
        vacina.id = insereVacina(tabelaVacina, vacina)

        val tabelaFicha = TabelaFicha(db)
        val ficha = Ficha(
            data = "20/12/2020",
            hora = "17:55",
            efeitos ="Dor de Cabeça",
            idPaciente = paciente.id,
            idVacina = vacina.id

        )
        ficha.id = insereFicha(tabelaFicha, ficha)

        assertEquals(ficha, getFichaBaseDados(tabelaFicha, ficha.id))

        db.close()
    }

    @Test
    fun consegueAlterarFicha() {
        val db = getBdVacinacaoOpenHelper().writableDatabase

        val tabelaPaciente = TabelaPaciente(db)

        val pacienteJoao = Paciente(
            nome = "Joao",
            morada = "Rua Das Flores",
            contacto = "963493871" ,
            NrUtente = "222222222",
            altura = "128",
            peso ="70",
            DataNascimento = "16/12/1996"
        )
        pacienteJoao.id = inserePaciente(tabelaPaciente, pacienteJoao)


        val pacienteFernando = Paciente(
            nome = "Fernando",
            morada = "Rua Das Flores",
            contacto = "963493871" ,
            NrUtente = "222222222",
            altura = "128",
            peso ="70",
            DataNascimento = "16/12/1996"
        )
        pacienteFernando.id = inserePaciente(tabelaPaciente, pacienteFernando)

        val tabelaVacina = TabelaVacina(db)
        val vacinaBio = Vacina(
            nomeVacina = "BioNtech" ,
            fabricante = "Pfizer",
            validade = "20/12/2022" ,
            dose = "2"
        )
        vacinaBio.id = insereVacina(tabelaVacina, vacinaBio)

        val vacinaJhon = Vacina(
            nomeVacina = "Jhonson" ,
            fabricante = "Jhonson&Jhonson",
            validade = "20/12/2023" ,
            dose = "3"
        )
        vacinaJhon.id = insereVacina(tabelaVacina, vacinaJhon)



        val tabelaFicha = TabelaFicha(db)
        val ficha = Ficha(
            data = "?",
            hora = "?",
            efeitos ="?",
            idPaciente = pacienteJoao.id,
            nomePaciente = pacienteJoao.nome,
            idVacina = vacinaBio.id,
            nomeVacina = vacinaBio.nomeVacina

        )
        ficha.id = insereFicha(tabelaFicha,ficha)

        ficha.data = "12/12/2020"
        ficha.hora = "15:45"
        ficha.efeitos = "Nenhum"
        ficha.idPaciente = pacienteFernando.id
        ficha.nomePaciente = pacienteFernando.nome
        ficha.idVacina = vacinaJhon.id
        ficha.nomeVacina = vacinaJhon.nomeVacina
        val registosAlterados = tabelaFicha.update(
            ficha.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(ficha.id.toString())
        )

        assertEquals(2, registosAlterados)

        assertEquals(ficha, getFichaBaseDados(tabelaFicha, ficha.id))

        db.close()
    }

    @Test
    fun consegueEliminarFicha() {

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

        val tabelaVacina = TabelaVacina(db)
        val vacina = Vacina(
            nomeVacina = "BioNtech" ,
            fabricante = "Pfizer",
            validade = "20/12/2022" ,
            dose = "2"
        )
        vacina.id = insereVacina(tabelaVacina, vacina)

        val tabelaFicha = TabelaFicha(db)
        val ficha = Ficha(
            data = "20/12/2020",
            hora = "17:55",
            efeitos ="Dor de Cabeça",
            idPaciente = paciente.id,
            nomePaciente = paciente.nome,
            idVacina = vacina.id ,
            nomeVacina = vacina.nomeVacina


        )
        ficha.id = insereFicha(tabelaFicha, ficha)

        val registosEliminados = tabelaFicha.delete(
            "${BaseColumns._ID}=?",
            arrayOf(ficha.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

}
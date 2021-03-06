package com.example.vacinacao

import androidx.fragment.app.Fragment

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        lateinit var fragment: Fragment

        var fichaSelecionado : Ficha? = null
        var pacienteSelecionado: Paciente? = null
        var vacinaSelecionado: Vacina? = null
    }
}
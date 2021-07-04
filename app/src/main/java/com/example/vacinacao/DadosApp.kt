package com.example.vacinacao

import androidx.fragment.app.Fragment

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        lateinit var fragment: Fragment

        var vacinaSelecionado : Vacina? = null
    }
}
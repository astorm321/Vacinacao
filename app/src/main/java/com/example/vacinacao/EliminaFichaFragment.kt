package com.example.vacinacao

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass.
 * Use the [EliminaFichaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EliminaFichaFragment : Fragment() {
    private lateinit var textViewNomePaciente: TextView
    private lateinit var textViewNomeVacina: TextView
    private lateinit var textViewData1: TextView
    private lateinit var textViewHora: TextView
    private lateinit var textViewEfeitos: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_ficha

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_ficha, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNomePaciente = view.findViewById(R.id.textViewNomePaciente)
        textViewNomeVacina = view.findViewById(R.id.textViewNomeVacina)
        textViewData1 = view.findViewById(R.id.textViewData1)
        textViewHora = view.findViewById(R.id.textViewHora)
        textViewEfeitos = view.findViewById(R.id.textViewEfeitos)

        val ficha = DadosApp.fichaSelecionado!!
        textViewNomePaciente.setText(ficha.nomePaciente)
        textViewNomeVacina.setText(ficha.nomeVacina)
        textViewData1.setText(ficha.data)
        textViewHora.setText(ficha.hora)
        textViewEfeitos.setText(ficha.efeitos)
    }

    fun navegaListaFicha() {
        findNavController().navigate(R.id.action_eliminaFichaFragment_to_listaFichaFragment)
    }

    fun elimina() {
        val uriFicha = Uri.withAppendedPath(
            ContentProviderVacinacao.ENDERECO_FICHAS,
            DadosApp.fichaSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriFicha,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_ficha,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.ficha_eliminada_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaFicha()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_ficha -> elimina()
            R.id.action_cancelar_eliminar_ficha -> navegaListaFicha()
            else -> return false
        }

        return true
    }
}
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
 * Use the [EliminaPacienteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EliminaPacienteFragment : Fragment() {
    private lateinit var textViewNomePaciente: TextView
    private lateinit var textViewMorada: TextView
    private lateinit var textViewContact: TextView
    private lateinit var textViewUtent: TextView
    private lateinit var textViewAltura: TextView
    private lateinit var textViewPeso: TextView
    private lateinit var textViewDataNasciment: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_paciente

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_paciente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNomePaciente = view.findViewById(R.id.textViewNomePaciente)
        textViewMorada = view.findViewById(R.id.textViewMorada)
        textViewContact = view.findViewById(R.id.textViewContact)
        textViewUtent = view.findViewById(R.id.textViewUtent)
        textViewAltura = view.findViewById(R.id.textViewAltura)
        textViewPeso = view.findViewById(R.id.textViewPeso)
        textViewDataNasciment = view.findViewById(R.id.textViewDataNasciment)

        val paciente = DadosApp.pacienteSelecionado!!
        textViewNomePaciente.setText(paciente.nome)
        textViewMorada.setText(paciente.morada)
        textViewContact.setText(paciente.contacto)
        textViewUtent.setText(paciente.NrUtente)
        textViewAltura.setText(paciente.altura)
        textViewPeso.setText(paciente.peso)
        textViewDataNasciment.setText(paciente.DataNascimento)
    }

    fun navegaListaPaciente() {
        findNavController().navigate(R.id.action_eliminaPacienteFragment_to_listaPacienteFragment)
    }

    fun elimina() {
        val uriPaciente = Uri.withAppendedPath(
            ContentProviderVacinacao.ENDERECO_PACIENTES,
            DadosApp.pacienteSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriPaciente,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_paciente,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.paciente_eliminado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaPaciente()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_paciente -> elimina()
            R.id.action_cancelar_eliminar_paciente -> navegaListaPaciente()
            else -> return false
        }

        return true
    }
}
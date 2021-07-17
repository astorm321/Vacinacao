package com.example.vacinacao

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass.
 * Use the [EditaPacienteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditaPacienteFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var editTextNomePacient: EditText
    private lateinit var editTextMorada: EditText
    private lateinit var editTextContacto: EditText
    private lateinit var editTextViewNumeroUtente: EditText
    private lateinit var editTextViewAltura: EditText
    private lateinit var editTextViewPeso: EditText
    private lateinit var editTextViewDataNascimento: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_paciente

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edita_paciente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        editTextNomePacient = view.findViewById(R.id.editTextNomePacient)
        editTextMorada = view.findViewById(R.id.editTextMorada)
        editTextContacto = view.findViewById(R.id.editTextContacto)
        editTextViewNumeroUtente = view.findViewById(R.id.editTextViewNumeroUtente)
        editTextViewAltura = view.findViewById(R.id.editTextViewAltura)
        editTextViewPeso = view.findViewById(R.id.editTextViewPeso)
        editTextViewDataNascimento = view.findViewById(R.id.editTextViewDataNascimento)

        editTextNomePacient.setText(DadosApp.pacienteSelecionado!!.nome)
        editTextMorada.setText(DadosApp.pacienteSelecionado!!.morada)
        editTextContacto.setText(DadosApp.pacienteSelecionado!!.contacto)
        editTextViewNumeroUtente.setText(DadosApp.pacienteSelecionado!!.NrUtente)
        editTextViewAltura.setText(DadosApp.pacienteSelecionado!!.altura)
        editTextViewPeso.setText(DadosApp.pacienteSelecionado!!.peso)
        editTextViewDataNascimento.setText(DadosApp.pacienteSelecionado!!.DataNascimento)

    }

    fun navegaListaPaciente() {
        findNavController().navigate(R.id.action_editaPacienteFragment_to_listaPacienteFragment)
    }

    fun guardar() {


        val nomePacient= editTextNomePacient.text.toString()
        if (nomePacient.isEmpty()) {
            editTextNomePacient.setError(getString(R.string.preencha_nome))
            editTextNomePacient.requestFocus()
            return
        }

        val morada = editTextMorada.text.toString()
        if (morada.isEmpty()) {
            editTextMorada.setError(getString(R.string.preencha_morada))
            editTextMorada.requestFocus()
            return
        }

        val contacto = editTextContacto.text.toString()
        if (contacto.isEmpty()) {
            editTextContacto.setError(getString(R.string.preencha_Contacto))
            editTextContacto.requestFocus()
            return
        }
        val NrUtente = editTextViewNumeroUtente.text.toString()
        if (NrUtente.isEmpty()) {
            editTextViewNumeroUtente.setError(getString(R.string.preencha_Utente))
            editTextViewNumeroUtente.requestFocus()
            return
        }

        val Altura = editTextViewAltura.text.toString()
        if (Altura.isEmpty()) {
            editTextViewAltura.setError(getString(R.string.preencha_altura))
            editTextViewAltura.requestFocus()
            return
        }

        val Peso = editTextViewPeso.text.toString()
        if (Peso.isEmpty()) {
            editTextViewPeso.setError(getString(R.string.preencha_peso))
            editTextViewPeso.requestFocus()
            return
        }

        val DataNascimento = editTextViewDataNascimento.text.toString()
        if (DataNascimento.isEmpty()) {
            editTextViewDataNascimento.setError(getString(R.string.preencha_datanascimento))
            editTextViewDataNascimento.requestFocus()
            return
        }


        val paciente = DadosApp.pacienteSelecionado!!
        paciente.nome = nomePacient
        paciente.morada = morada
        paciente.contacto = contacto
        paciente.NrUtente = NrUtente
        paciente.altura = Altura
        paciente.peso = Peso
        paciente.DataNascimento = DataNascimento

        val uriPaciente = Uri.withAppendedPath(
            ContentProviderVacinacao.ENDERECO_PACIENTES,
            paciente.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriPaciente,
            paciente.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_alterar_paciente,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.paciente_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaPaciente()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_paciente -> guardar()
            R.id.action_cancelar_edita_paciente -> navegaListaPaciente()
            else -> return false
        }

        return true
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */


    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */


    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */





    companion object {
        const val ID_LOADER_MANAGER_PACIENTES = 0

    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        TODO("Not yet implemented")
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        TODO("Not yet implemented")
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }
}
package com.example.vacinacao

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.example.vacinacao.databinding.FragmentNovoPacienteBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovoPacienteFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentNovoPacienteBinding? = null

    private lateinit var editTextNomePacient: EditText
    private lateinit var editTextMorada: EditText
    private lateinit var editTextContacto: EditText
    private lateinit var editTextNumeroUtente: EditText
    private lateinit var editTextAltura: EditText
    private lateinit var editTextPeso: EditText
    private lateinit var editTextDataNascimento: EditText


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_paciente

        _binding = FragmentNovoPacienteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomePacient = view.findViewById(R.id.editTextNomePacient)
        editTextMorada = view.findViewById(R.id.editTextMorada)
        editTextContacto = view.findViewById(R.id.editTextContacto)
        editTextNumeroUtente = view.findViewById(R.id.editTextNumeroUtente)
        editTextAltura= view.findViewById(R.id.editTextAltura)
        editTextPeso= view.findViewById(R.id.editTextPeso)
        editTextDataNascimento = view.findViewById(R.id.editTextDataNascimento)



        /*LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_PACIENTES, null, this)
        */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navegaListaPaciente() {
        findNavController().navigate(R.id.action_NovoPacienteFragment_to_ListaPacienteFragment)
    }

    fun guardar() {
        val nomePacient = editTextNomePacient.text.toString()
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

        val NrUtente = editTextNumeroUtente.text.toString()
        if (NrUtente.isEmpty()) {
            editTextNumeroUtente.setError(getString(R.string.preencha_Utente))
            editTextNumeroUtente.requestFocus()
            return
        }

        val Altura = editTextAltura.text.toString()
        if (Altura.isEmpty()) {
            editTextAltura.setError(getString(R.string.preencha_altura))
            editTextAltura.requestFocus()
            return
        }

        val Peso = editTextPeso.text.toString()
        if (Peso.isEmpty()) {
            editTextPeso.setError(getString(R.string.preencha_peso))
            editTextPeso.requestFocus()
            return
        }
        val DataNascimento = editTextDataNascimento.text.toString()
        if (DataNascimento.isEmpty()) {
            editTextDataNascimento.setError(getString(R.string.preencha_datanascimento))
            editTextDataNascimento.requestFocus()
            return
        }


        val paciente = Paciente( nome = nomePacient, morada = morada, contacto = contacto , NrUtente = NrUtente, altura = Altura , peso = Peso , DataNascimento = DataNascimento )

        val uri = activity?.contentResolver?.insert(
            ContentProviderVacinacao.ENDERECO_PACIENTES,
            paciente.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNomePacient,
                getString(R.string.erro_inserir_paciente),
                Snackbar.LENGTH_LONG
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
            R.id.action_guardar_novo_paciente -> guardar()
            R.id.action_cancelar_novo_paciente -> navegaListaPaciente()
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
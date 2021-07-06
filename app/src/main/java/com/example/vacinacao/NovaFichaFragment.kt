package com.example.vacinacao

import android.database.Cursor
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
import com.google.android.material.snackbar.Snackbar
import com.example.vacinacao.databinding.FragmentNovaFichaBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovaFichaFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentNovaFichaBinding? = null

    private lateinit var editTextNomePaciente: EditText
    private lateinit var editTextData: EditText
    private lateinit var editTextHora: EditText
    private lateinit var editTextEfeitos: EditText
    private lateinit var spinnerVacinas: Spinner

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_nova_ficha

        _binding = FragmentNovaFichaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomePaciente = view.findViewById(R.id.editTextNomePaciente)
        editTextData = view.findViewById(R.id.editTextData)
        editTextHora = view.findViewById(R.id.editTextHora)
        editTextEfeitos = view.findViewById(R.id.editTextEfeitos)
        spinnerVacinas = view.findViewById(R.id.spinnerVacinas)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_FICHAS, null, this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navegaListaLivros() {
        findNavController().navigate(R.id.action_NovoFichaFragment_to_ListaFichaFragment)
    }

    fun guardar() {
        val paciente = editTextNomePaciente.text.toString()
        if (paciente.isEmpty()) {
            editTextNomePaciente.setError(getString(R.string.preencha_nome))
            editTextNomePaciente.requestFocus()
            return
        }

        val data = editTextData.text.toString()
        if (data.isEmpty()) {
            editTextData.setError(getString(R.string.preencha_data))
            editTextData.requestFocus()
            return
        }

        val hora = editTextHora.text.toString()
        if (hora.isEmpty()) {
            editTextHora.setError(getString(R.string.preencha_hora))
            editTextHora.requestFocus()
            return
        }

        val efeitos = editTextEfeitos.text.toString()
        if (efeitos.isEmpty()) {
            editTextEfeitos.setError(getString(R.string.preencha_efeitos))
            editTextEfeitos.requestFocus()
            return
        }


        val idVacina = spinnerVacinas.selectedItemId

        val ficha = Ficha(nomePaciente = paciente, data = data, hora = hora , efeitos = efeitos, idVacina = idVacina )

        val uri = activity?.contentResolver?.insert(
            ContentProviderFicha.ENDERECO_FICHAS,
            ficha.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNomePaciente,
                getString(R.string.erro_inserir_ficha),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.ficha_guardada_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaLivros()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_ficha -> guardar()
            R.id.action_cancelar_novo_ficha -> navegaListaLivros()
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
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderFicha.ENDERECO_PACIENTES,
            TabelaPaciente.TODAS_COLUNAS,
            null, null,
            TabelaPaciente.NOME_PACIENTE
        )
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
        atualizaSpinnerVacinas(data)
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
        atualizaSpinnerVacinas(null)
    }

    private fun atualizaSpinnerVacinas(data: Cursor?) {
        spinnerVacinas.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaVacina.CAMPO_NOME_VACINA),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    companion object {
        const val ID_LOADER_MANAGER_FICHAS = 0
    }
}
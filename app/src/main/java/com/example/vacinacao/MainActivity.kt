package com.example.vacinacao

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.vacinacao.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var menu: Menu

    var menuAtual = R.menu.menu_lista_ficha
        set(value) {
            field = value
            invalidateOptionsMenu()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        DadosApp.activity = this


        }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(menuAtual, menu)
        this.menu = menu

        if(menuAtual == R.menu.menu_lista_ficha){
            atualizaMenuListaFicha(false)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val opcaoProcessada = when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, R.string.versao, Toast.LENGTH_LONG).show()
                true
            }

            else -> when (menuAtual) {
                R.menu.menu_lista_ficha -> (DadosApp.fragment as ListaFichaFragment).processaOpcaoMenu(
                    item
                )
                R.menu.menu_nova_ficha -> (DadosApp.fragment as NovaFichaFragment).processaOpcaoMenu(
                    item
                )
                R.menu.menu_edita_ficha -> (DadosApp.fragment as EditaFichaFragment).processaOpcaoMenu(
                item
                )
                R.menu.menu_elimina_ficha -> (DadosApp.fragment as EliminaFichaFragment).processaOpcaoMenu(
                    item
                )
                R.menu.menu_lista_paciente -> (DadosApp.fragment as ListaPacienteFragment).processaOpcaoMenu(
                    item
                )
                R.menu.menu_novo_paciente -> (DadosApp.fragment as NovoPacienteFragment).processaOpcaoMenu(
                    item
                )
                R.menu.menu_edita_paciente -> (DadosApp.fragment as EditaPacienteFragment).processaOpcaoMenu(
                    item
                )
               R.menu.menu_elimina_paciente -> (DadosApp.fragment as EliminaPacienteFragment).processaOpcaoMenu(
                    item
                )
                R.menu.menu_lista_vacina -> (DadosApp.fragment as ListaVacinaFragment).processaOpcaoMenu(
                    item
                )
                R.menu.menu_nova_vacina -> (DadosApp.fragment as NovaVacinaFragment).processaOpcaoMenu(
                    item
                )



                else -> false
            }

        }
        return if(opcaoProcessada) true else super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun atualizaMenuListaFicha(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_ficha).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_ficha).setVisible(mostraBotoesAlterarEliminar)
    }

    fun atualizaMenuListaPaciente(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_paciente).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_paciente).setVisible(mostraBotoesAlterarEliminar)
    }

    fun atualizaMenuListaVacina(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_vacina).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_vacina).setVisible(mostraBotoesAlterarEliminar)

    }
}
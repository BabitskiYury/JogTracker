package com.example.yellow.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yellow.Constants.ACCESS_TOKEN
import com.example.yellow.R
import com.example.yellow.dialog.AddNewJogDialog
import com.example.yellow.dialog.UpdateJogDialog
import com.example.yellow.listeners.AddNewJogListener
import com.example.yellow.listeners.EditButtonClickListener
import com.example.yellow.listeners.UpdateJogListener
import com.example.yellow.model.body.AddNewJogBody
import com.example.yellow.model.body.JogUpdateBody
import com.example.yellow.ui.main.recycler.JogAdapter
import com.example.yellow.ui.main.recycler.JogData
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_jogs.floating_action_button
import kotlinx.android.synthetic.main.activity_jogs.jogsRecyclerView

@AndroidEntryPoint
class JogsActivity : AppCompatActivity(), EditButtonClickListener, UpdateJogListener,
    AddNewJogListener {

    private val viewModel by viewModels<JogsViewModel>()

    private lateinit var jogAdapter: JogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogs)

        setToken()
        initRecycler()

        floating_action_button.setOnClickListener {
            addNewJog()
        }

        viewModel.jogs.observe(this, {
            jogAdapter.setJogs(it)
        })
        viewModel.viewCommands.observe(this, { command ->
            when (command) {
                JogsViewModel.ViewCommand.ServerError -> {
                    Toast.makeText(this, R.string.server_error, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.sync()
    }

    private fun setToken() {
        viewModel.setToken(
            Prefs.getString(ACCESS_TOKEN)
        )
    }

    private fun initRecycler() {
        jogAdapter = JogAdapter(this)

        jogsRecyclerView.layoutManager = LinearLayoutManager(this)
        jogsRecyclerView.adapter = jogAdapter
    }

    override fun onEditButtonClick(item: JogData) {
        UpdateJogDialog.newInstance(item).show(supportFragmentManager, null)
    }

    override fun onUpdateJog(item: JogUpdateBody) {
        viewModel.updateJog(item)
    }

    private fun addNewJog() {
        AddNewJogDialog().show(supportFragmentManager, null)
    }

    override fun onAddNewJog(item: AddNewJogBody) {
        viewModel.addNewJog(item)
    }
}

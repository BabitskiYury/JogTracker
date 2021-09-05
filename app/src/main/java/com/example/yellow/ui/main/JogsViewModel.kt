package com.example.yellow.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yellow.SingleLiveEvent
import com.example.yellow.mapper.JogDataMapper
import com.example.yellow.model.body.AddNewJogBody
import com.example.yellow.model.body.JogUpdateBody
import com.example.yellow.repo.JogRepository
import com.example.yellow.ui.main.recycler.JogData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class JogsViewModel @Inject constructor(
    private val repository: JogRepository,
    private val jogDataMapper: JogDataMapper
) : ViewModel() {

    private lateinit var accessToken: String

    val viewCommands = SingleLiveEvent<ViewCommand>()

    private val _jogs = MutableLiveData<List<JogData>>()
    val jogs: LiveData<List<JogData>>
        get() = _jogs

    fun sync() {
        repository.getJogs(accessToken)
            .map { jogDataMapper.map(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _jogs.value = it },
                { viewCommands.value = ViewCommand.ServerError }
            )
    }

    fun updateJog(jog: JogUpdateBody) {
        repository.updateJog(accessToken, jog)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { sync() },
                { viewCommands.value = ViewCommand.ServerError }
            )
    }

    fun addNewJog(jog: AddNewJogBody) {
        repository.addNewJog(accessToken, jog)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { sync() },
                { viewCommands.value = ViewCommand.ServerError }
            )
    }

    fun setToken(token: String) {
        accessToken = token
    }

    sealed class ViewCommand {
        object ServerError : ViewCommand()
    }
}

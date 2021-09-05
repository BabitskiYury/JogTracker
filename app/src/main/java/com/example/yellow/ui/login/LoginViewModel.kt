package com.example.yellow.ui.login

import androidx.lifecycle.ViewModel
import com.example.yellow.SingleLiveEvent
import com.example.yellow.repo.JogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: JogRepository,
) : ViewModel() {

    val viewCommands = SingleLiveEvent<ViewCommand>()

    fun auth(isNetworkAvailable: Boolean, uuid: String) {
        if(isNetworkAvailable){
            repository.authByUUID(uuid)
                .map { it.response.access_token.toString() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { token -> viewCommands.value = ViewCommand.OnSuccessLogin(token) },
                    { viewCommands.value = ViewCommand.OnFailureLogin }
                )
        }
        else {
            viewCommands.value = ViewCommand.NoInternetConnection
        }
    }

    sealed class ViewCommand {
        class OnSuccessLogin(val token: String) : ViewCommand()
        object OnFailureLogin : ViewCommand()
        object NoInternetConnection : ViewCommand()
    }
}

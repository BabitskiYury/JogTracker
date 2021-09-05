package com.example.yellow.repo

import com.example.yellow.model.body.AddNewJogBody
import com.example.yellow.model.body.AuthBody
import com.example.yellow.model.body.JogUpdateBody
import com.example.yellow.model.response.AuthResponse
import com.example.yellow.model.response.JogUpdateResponse
import com.example.yellow.model.response.JogsResponse
import com.example.yellow.network.JogService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JogRepository @Inject constructor(
    private val jogService: JogService
) {

    fun authByUUID(uuid: String): Single<AuthResponse> =
        jogService.auth(AuthBody(uuid))
            .subscribeOn(Schedulers.io())

    fun updateJog(accessToken: String, jog: JogUpdateBody): Single<JogUpdateResponse> =
        jogService.updateJog(accessToken, jog)
            .subscribeOn(Schedulers.io())

    fun addNewJog(accessToken: String, jog: AddNewJogBody): Single<JogUpdateResponse> =
        jogService.addNewJog(accessToken, jog)
            .subscribeOn(Schedulers.io())

    fun getJogs(accessToken: String): Single<JogsResponse> =
        jogService.getJogList(accessToken)
            .subscribeOn(Schedulers.io())

}

package com.example.yellow.network

import com.example.yellow.model.body.AddNewJogBody
import com.example.yellow.model.body.AuthBody
import com.example.yellow.model.body.JogUpdateBody
import com.example.yellow.model.response.AuthResponse
import com.example.yellow.model.response.JogUpdateResponse
import com.example.yellow.model.response.JogsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface JogService {

    @POST("auth/uuidLogin")
    fun auth(@Body authBody: AuthBody): Single<AuthResponse>

    @POST("data/jog")
    fun addNewJog(
        @Query("access_token") access_token: String,
        @Body jog: AddNewJogBody
    ): Single<JogUpdateResponse>

    @PUT("data/jog")
    fun updateJog(
        @Query("access_token") access_token: String,
        @Body jog: JogUpdateBody
    ): Single<JogUpdateResponse>

    @GET("data/sync")
    fun getJogList(@Query("access_token") access_token: String): Single<JogsResponse>
}

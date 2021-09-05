package com.example.yellow.ui.main.recycler

import java.io.Serializable

data class JogData(
    val id: Int,
    val user_id: String,
    var distance: Float,
    var time: Int,
    var date: String
) : Serializable

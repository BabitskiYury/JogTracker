package com.example.yellow.listeners

import com.example.yellow.model.body.JogUpdateBody

interface UpdateJogListener {
    fun onUpdateJog(item: JogUpdateBody)
}

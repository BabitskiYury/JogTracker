package com.example.yellow.mapper

import com.example.yellow.model.body.JogUpdateBody
import com.example.yellow.ui.main.recycler.JogData

class JogUpdateBodyMapper {

    fun map(jogData: JogData): JogUpdateBody =
        JogUpdateBody(
            date = jogData.date,
            time = jogData.time,
            distance = jogData.distance,
            jog_id = jogData.id,
            user_id = jogData.user_id
        )
}

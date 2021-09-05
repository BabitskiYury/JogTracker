package com.example.yellow.mapper

import com.example.yellow.Constants.DATE_FORMAT
import com.example.yellow.model.response.JogsResponse
import com.example.yellow.ui.main.recycler.JogData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class JogDataMapper {

    private val sdf = SimpleDateFormat(DATE_FORMAT, Locale.US)

    fun map(jogsResponse: JogsResponse): List<JogData> {
        val jogList = mutableListOf<JogData>()
        jogsResponse.response.jogs.map {
            val serverDate = Date(it.date * 1000)
            val date = sdf.format(serverDate)
            jogList.add(
                JogData(
                    it.id,
                    it.user_id,
                    it.distance,
                    it.time,
                    date
                )
            )
        }
        return jogList.toList()
    }
}

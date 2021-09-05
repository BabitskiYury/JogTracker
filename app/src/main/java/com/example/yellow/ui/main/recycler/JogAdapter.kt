package com.example.yellow.ui.main.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yellow.R
import com.example.yellow.listeners.EditButtonClickListener
import kotlinx.android.synthetic.main.item_jog.view.date
import kotlinx.android.synthetic.main.item_jog.view.distance
import kotlinx.android.synthetic.main.item_jog.view.editButton
import kotlinx.android.synthetic.main.item_jog.view.time

class JogAdapter(
    private val editButtonClickListener: EditButtonClickListener
) : RecyclerView.Adapter<JogAdapter.JogViewHolder>() {

    private var jogs: List<JogData> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JogViewHolder {
        return JogViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: JogViewHolder, position: Int) {
        holder.bind(jogs[position], editButtonClickListener)
    }

    override fun getItemCount(): Int = jogs.size

    fun setJogs(jogs: List<JogData>) {
        this.jogs = jogs
        notifyDataSetChanged()
    }

    class JogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: JogData, listener: EditButtonClickListener) {
            val distanceText = "${item.distance.toBigDecimal().toPlainString()} m"
            val timeText = "${item.time} sec"
            itemView.apply {
                distance.text = distanceText
                time.text = timeText
                date.text = item.date
                editButton.setOnClickListener { listener.onEditButtonClick(item) }
            }
        }

        companion object {
            fun from(parent: ViewGroup): JogViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_jog, parent, false)
                return JogViewHolder(itemView)
            }
        }
    }
}

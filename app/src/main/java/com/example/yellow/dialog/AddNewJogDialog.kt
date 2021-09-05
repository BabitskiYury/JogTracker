package com.example.yellow.dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.yellow.Constants.DAY
import com.example.yellow.Constants.MONTH
import com.example.yellow.Constants.YEAR
import com.example.yellow.InternetConnectionChecker
import com.example.yellow.R
import com.example.yellow.listeners.AddNewJogListener
import com.example.yellow.model.body.AddNewJogBody
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject
import kotlinx.android.synthetic.main.dialog_add_new_jog.view.datePicker
import kotlinx.android.synthetic.main.dialog_add_new_jog.view.distance
import kotlinx.android.synthetic.main.dialog_add_new_jog.view.time

@AndroidEntryPoint
class AddNewJogDialog : DialogFragment() {

    @Inject
    lateinit var connectionChecker: InternetConnectionChecker

    lateinit var datePicker: DatePicker
    lateinit var time: EditText
    lateinit var distance: EditText

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(YEAR, datePicker.year)
        outState.putInt(MONTH, datePicker.month)
        outState.putInt(DAY, datePicker.dayOfMonth)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val layout = inflater.inflate(R.layout.dialog_add_new_jog, null)
            time = layout.time
            distance = layout.distance
            datePicker = layout.datePicker

            val year = savedInstanceState?.getInt(YEAR)
            val month = savedInstanceState?.getInt(MONTH)
            val day = savedInstanceState?.getInt(DAY)
            if (year != null && month != null && day != null) {
                datePicker.updateDate(year, month, day)
            } else {
                val calendar = Calendar.getInstance()
                datePicker.updateDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
            }

            builder.setView(layout)
                .setPositiveButton(R.string.add, null)
                .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onResume() {
        super.onResume()
        val dialog = dialog as AlertDialog?
        if (dialog != null) {
            val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE) as Button
            positiveButton.setOnClickListener {
                if (connectionChecker.isNetworkAvailable(context)) {
                    val day = datePicker.dayOfMonth
                    val month = datePicker.month
                    val year = datePicker.year

                    val timeString = time.text.toString()
                    val distanceString = distance.text.toString()

                    if (timeString.isNotEmpty() && distanceString.isNotEmpty()) {
                        val time = timeString.toInt()
                        val distance = distanceString.toFloat()
                        val date = "$day/${month + 1}/$year"
                        (activity as AddNewJogListener).onAddNewJog(
                            AddNewJogBody(
                                date,
                                time,
                                distance
                            )
                        )
                        dialog.dismiss()
                    } else {
                        Toast.makeText(context, R.string.fields_cannot_be_empty, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

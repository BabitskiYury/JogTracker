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
import com.example.yellow.Constants.ITEM
import com.example.yellow.Constants.MONTH
import com.example.yellow.Constants.YEAR
import com.example.yellow.InternetConnectionChecker
import com.example.yellow.R
import com.example.yellow.listeners.UpdateJogListener
import com.example.yellow.mapper.JogUpdateBodyMapper
import com.example.yellow.ui.main.recycler.JogData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.android.synthetic.main.dialog_update_jog.view.datePicker
import kotlinx.android.synthetic.main.dialog_update_jog.view.distance
import kotlinx.android.synthetic.main.dialog_update_jog.view.time

@AndroidEntryPoint
class UpdateJogDialog : DialogFragment() {

    private lateinit var item: JogData

    @Inject
    lateinit var connectionChecker: InternetConnectionChecker

    private lateinit var datePicker: DatePicker
    private lateinit var time: EditText
    private lateinit var distance: EditText

    companion object {
        fun newInstance(item: JogData): UpdateJogDialog {
            val args = Bundle()
            args.putSerializable(ITEM, item)

            val dialog = UpdateJogDialog()
            dialog.arguments = args
            return dialog
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(YEAR, datePicker.year)
        outState.putInt(MONTH, datePicker.month)
        outState.putInt(DAY, datePicker.dayOfMonth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = arguments?.getSerializable(ITEM) as JogData
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val layout = inflater.inflate(R.layout.dialog_update_jog, null)
            time = layout.time
            distance = layout.distance
            datePicker = layout.datePicker

            val year = savedInstanceState?.getInt(YEAR)
            val month = savedInstanceState?.getInt(MONTH)
            val day = savedInstanceState?.getInt(DAY)
            if (year != null && month != null && day != null) {
                datePicker.updateDate(year, month, day)
            } else {
                val dateParts: List<String> = item.date.split("/")

                datePicker.updateDate(
                    dateParts[2].toInt(),
                    dateParts[1].toInt(),
                    dateParts[0].toInt()
                )
            }

            builder.setView(layout)
                .setPositiveButton(R.string.save, null)
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

                    val time = time.text.toString()
                    val distance = distance.text.toString()

                    if (time.isNotEmpty() && distance.isNotEmpty()) {
                        item.time = time.toInt()
                        item.distance = distance.toFloat()
                        item.date = "$day/${month + 1}/$year"
                        (activity as UpdateJogListener).onUpdateJog(
                            JogUpdateBodyMapper().map(item)
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

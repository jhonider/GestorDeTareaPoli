package com.example.gestordetarea

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class CustomCalendarDialogFragment : DialogFragment() {

    private var onDateSelected: ((String, String) -> Unit)? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout
        val view = inflater.inflate(R.layout.dialog_calendar, container, false)

        // Acceder a las vistas usando findViewById
        val calendarViewInicio: CalendarView = view.findViewById(R.id.calendarViewInicio)
        val calendarViewFin: CalendarView = view.findViewById(R.id.calendarViewFin)
        val btnAceptar: Button = view.findViewById(R.id.btnAceptarInicio)
        val btnCancelar: Button = view.findViewById(R.id.btnCancelarInicio) // Asegúrate de que este ID exista

        // Configurar el botón para aceptar
        btnAceptar.setOnClickListener {
            val selectedStartDate = getSelectedDate(calendarViewInicio)
            val selectedEndDate = getSelectedDate(calendarViewFin)
            onDateSelected?.invoke(selectedStartDate, selectedEndDate)
            dismiss()
        }

        // Configurar el botón para cancelar
        btnCancelar.setOnClickListener {
            dismiss()
        }

        return view
    }

    private fun getSelectedDate(calendarView: CalendarView): String {
        val dateMillis = calendarView.date
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateMillis
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1 // Los meses son indexados desde 0
        val year = calendar.get(Calendar.YEAR)
        return "$day/$month/$year"
    }

    fun setOnDateSelectedListener(listener: (String, String) -> Unit) {
        onDateSelected = listener
    }
}

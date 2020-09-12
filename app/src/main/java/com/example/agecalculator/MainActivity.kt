package com.example.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectDate.setOnClickListener{view ->
            selectDateFromUser(view)
        }
    }

    fun selectDateFromUser(view: View){
        val today = Calendar.getInstance()
        val todayDay = today.get(Calendar.DAY_OF_MONTH)
        val todayMonth = today.get(Calendar.MONTH)
        val todayYear = today.get(Calendar.YEAR)

        val todayDate = "$todayDay/${todayMonth+1}/$todayYear"
        val sdfToday = SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH).parse(todayDate)

        showToast("today is $todayDay/${todayMonth+1}/$todayYear")
        val datePickerDialogue = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{_, year, monthOfYear, dayOfMonth ->
                val birthDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                val sdfBirthDate = SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH).parse(birthDate)
                selectedDate.text = birthDate
                val difference = sdfToday!!.time - sdfBirthDate!!.time
                Log.i("difference", (difference).toString())
                val yearDivisor = (1000 * 60 * 60 * 24 * 365.0)
                val monthDivisor = (1000 * 60 * 60 * 24 * 30.0)
                val dayDivisor = (1000 * 60 * 60 * 24.0)
                val hourDivisor = (1000 * 60 * 60.0)
                val minuteDivisor = (1000 * 60.0)
                val secondsDivisor = 1000.0
                println(yearDivisor)
                val y = (difference / yearDivisor).toInt()
                val mn = ((difference % yearDivisor) / monthDivisor).toInt()
                val d = (((difference % yearDivisor) % monthDivisor) / dayDivisor).toInt()
                val h = ((((difference % yearDivisor) % monthDivisor) % dayDivisor) / hourDivisor).toInt()
                val min = (((((difference % yearDivisor) % monthDivisor) % dayDivisor) % hourDivisor) / minuteDivisor).toInt()
                val s = ((((((difference % yearDivisor) % monthDivisor) % dayDivisor) % hourDivisor) % minuteDivisor) / secondsDivisor).toInt()

                calculatedAge.text = "$y years $mn months $d days"
                showToast("selected date is $dayOfMonth/${monthOfYear+1}/$year")
            },
            todayYear,
            todayMonth,
            todayDay
        )
        datePickerDialogue.datePicker.maxDate = Date().time;
        datePickerDialogue.show()
    }

    fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
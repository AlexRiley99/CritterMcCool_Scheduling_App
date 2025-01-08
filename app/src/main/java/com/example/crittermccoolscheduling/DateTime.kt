package com.example.crittermccoolscheduling

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class DateTime : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get all previously passed data
        val fullName = intent.getStringExtra("fullName")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val emailAddress = intent.getStringExtra("emailAddress")
        val serviceAddress = intent.getStringExtra("serviceAddress")
        val insectType = intent.getStringExtra("insectType")
        val problemDuration = intent.getStringExtra("problemDuration")

        /*SPINNERS*/
        //Spinner for Time
        val Time: Spinner = findViewById(R.id.time)//Defining spinner
        val timeItems = listOf("*Request A Time", "8am", "9am", "10am", "11am", "12pm", "1pm", "2pm", "3pm", "4pm", "5pm", "6pm", "7pm")

        //ArrayAdapter to bind the list to the spinner
        val timeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeItems)
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //Setting the adapter to the spinner
        Time.adapter = timeAdapter

        //Spinner for Date
        val Date: Spinner = findViewById(R.id.date)
        val dateItems = listOf("*Request A Date", "This coming Monday", "This coming Tuesday", "This coming Wednesday", "This coming Thursday", "This coming Friday", "Other")
        //ArrayAdapter for Date
        val dateAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dateItems)
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //Setting the adapter to the spinner
        Date.adapter = dateAdapter


      /*BUTTONS*/
        //Handler for the "Finish" button
        val Finish_Btn: Button = findViewById(R.id.Finish_Btn)
        Finish_Btn.setOnClickListener{
            // Pass everything to the InfoConfirmation activity
            val intent = Intent(this, InfoConfirmation::class.java)
            intent.putExtra("fullName", fullName)
            intent.putExtra("phoneNumber", phoneNumber)
            intent.putExtra("emailAddress", emailAddress)
            intent.putExtra("serviceAddress", serviceAddress)
            intent.putExtra("insectType", insectType)
            intent.putExtra("problemDuration", problemDuration)
            //ADD DATE
            //ADD TIME
        }

        //Handler for the "Back" button
        val Back_Btn: Button = findViewById(R.id.dateTime_back_btn)
        Back_Btn.setOnClickListener{
            val intent = Intent(this, AppointmentInfo::class.java)
            startActivity(intent)
        }
    }

}
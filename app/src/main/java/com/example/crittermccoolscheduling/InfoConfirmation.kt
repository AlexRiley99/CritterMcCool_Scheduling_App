package com.example.crittermccoolscheduling

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class InfoConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_info_confirmation)

        // Retrieve the data from the Intent
        val fullName = intent.getStringExtra("fullName")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val emailAddress = intent.getStringExtra("emailAddress")
        val serviceAddress = intent.getStringExtra("serviceAddress")
        val insectType = intent.getStringExtra("insectType")
        val problemDuration = intent.getStringExtra("problemDuration")
        val requestedTime = intent.getStringExtra("requestedTime")
        val requestedDate = intent.getStringExtra("requestedDate")

        // Display the data in the TextViews
        findViewById<TextView>(R.id.fullNameText).text = fullName
        findViewById<TextView>(R.id.phoneNumberText).text = phoneNumber
        findViewById<TextView>(R.id.emailAddressText).text = emailAddress
        findViewById<TextView>(R.id.serviceAddressText).text = serviceAddress
        findViewById<TextView>(R.id.insectTypeText).text = insectType
        findViewById<TextView>(R.id.problemDurationText).text = problemDuration
        findViewById<TextView>(R.id.requestedTimeText).text = requestedTime
        findViewById<TextView>(R.id.requestedDateText).text = requestedDate

        // Return to AppointmentInfo page to correct the information
        val No_Btn: Button = findViewById(R.id.noBtn)
        No_Btn.setOnClickListener{
            val intent = Intent(this, AppointmentInfo::class.java)
            startActivity(intent)
        }

        // Save info to the database and move on to the next page
        val Yes_Btn: Button = findViewById(R.id.yesBtn)
        Yes_Btn.setOnClickListener{
            val databaseHelper = DatabaseHelper(this) //pass the context to DatabaseHelper
            val repository = AppointmentRepository(databaseHelper) // Instance of AppointmentRepository

            // Insert the appointment data into the repository
            val appointmentId = repository.insertAppointment(
                fullName ?: "",
                phoneNumber ?: "",
                emailAddress ?: "",
                serviceAddress ?: "",
                insectType ?: "",
                problemDuration ?: "",
                requestedTime ?: "",
                requestedDate ?: ""
            )

            //If insertion failes, log error
            //Else insert appointment, log success, and go to next page
            if (appointmentId == -1L) {
                Log.e("InfoConfirmation", "Error inserting appointment")
            } else {
                Log.d("InfoConfirmation", "Appointment inserted with ID: $appointmentId")
                // Navigate to the confirmation page
                val intent = Intent(this, Confirmation::class.java)
                startActivity(intent)
            }
        }
    }
}

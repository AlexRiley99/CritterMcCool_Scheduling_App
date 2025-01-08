package com.example.crittermccoolscheduling

import AppointmentInfo
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
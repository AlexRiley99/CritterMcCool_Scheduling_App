package com.example.crittermccoolscheduling

import AppointmentInfo
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class InfoConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_info_confirmation){

            /*// Retrieve the data from the Intent
            val fullName = intent.getStringExtra("fullName")
            val phoneNumber = intent.getStringExtra("phoneNumber")
            val emailAddress = intent.getStringExtra("emailAddress")
            val serviceAddress = intent.getStringExtra("serviceAddress")
            val insectType = intent.getStringExtra("insectType")
            val problemDuration = intent.getStringExtra("problemDuration")

            // Display the data
            findViewById<TextView>(R.id.fullNameText).text = fullName
            findViewById<TextView>(R.id.phoneNumberText).text = phoneNumber
            findViewById<TextView>(R.id.emailAddressText).text = emailAddress
            findViewById<TextView>(R.id.serviceAddressText).text = serviceAddress
            findViewById<TextView>(R.id.insectTypeText).text = insectType
            findViewById<TextView>(R.id.problemDurationText).text = problemDuration
            //ADD DATE AND TIME
        }
        //Return to AppointmentInfo page to correct the information
        val No_Btn: Button = findViewById(R.id.noBtn)
        No_Btn.setOnClickListener{
            val intent = Intent(this, AppointmentInfo::class.java)
            startActivity(intent)
        }

        //Save info to the database and move on to the next page
        val Yes_Btn: Button = findViewById(R.id.yesBtn)
        Yes_Btn.setOnClickListener{
            val repository = AppointmentRepository(this) //Instance of AppointmentRepository

            repository.insertAppointment(
                fullName ?: "",
                phoneNumber?: "",
                emailAddress ?: "",
                serviceAddress ?: "",
                insectType ?: "",
                problemDuration ?: "",
                //ADD DATE AND TIME)*/

            val intent = Intent(this, Confirmation::class.java)
            startActivity(intent)
        }
    }

    private fun setContentView(view: Int, params: () -> Unit) {

    }
}
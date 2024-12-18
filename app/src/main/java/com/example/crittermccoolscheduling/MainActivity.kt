package com.example.crittermccoolscheduling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Handler for the "Request Appointment" button
        val RequestAppointment_Btn: Button = findViewById(R.id.RequestAppointment_Btn) //Creating OnClickListener for the "Request Appointment" button
        RequestAppointment_Btn.setOnClickListener{
            val intent = Intent(this, AppointmentInfo::class.java)
            startActivity(intent)
        }

        //Handler for the "Admin" button
        val adminLogin_Btn: Button = findViewById(R.id.adminLogin)
        adminLogin_Btn.setOnClickListener{
            val intent = Intent(this, AdminLogin::class.java)
            startActivity(intent)
        }
    }
}
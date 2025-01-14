package com.example.crittermccoolscheduling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    // Retrofit instance for API requests
    private lateinit var retrofit: Retrofit
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Retrofit
        initializeRetrofit()

        // Handler for the "Request Appointment" button
        val requestAppointmentBtn: Button = findViewById(R.id.RequestAppointment_Btn)
        requestAppointmentBtn.setOnClickListener {
            // Start the AppointmentInfo activity when the "Request Appointment" button is clicked
            val intent = Intent(this, AppointmentInfo::class.java)
            startActivity(intent)
        }

        // Handler for the "Admin" button
        val adminLoginBtn: Button = findViewById(R.id.adminLogin)
        adminLoginBtn.setOnClickListener {
            // Start the AdminLogin activity when the "Admin" button is clicked
            val intent = Intent(this, AdminLogin::class.java)
            startActivity(intent)
        }
    }

    // Initialize Retrofit for API requests
    private fun initializeRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }
}

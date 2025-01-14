package com.example.crittermccoolscheduling

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        No_Btn.setOnClickListener {
            val intent = Intent(this, AppointmentInfo::class.java)
            startActivity(intent)
        }

        // Save info to the database and move on to the next page
        val Yes_Btn: Button = findViewById(R.id.yesBtn)
        Yes_Btn.setOnClickListener {
            // Check if any required field is empty
            if (fullName.isNullOrEmpty() || phoneNumber.isNullOrEmpty() || emailAddress.isNullOrEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create an Appointment object with the data
            val appointment = Appointment(
                fullName = fullName ?: "",
                phoneNumber = phoneNumber ?: "",
                emailAddress = emailAddress ?: "",
                serviceAddress = serviceAddress ?: "",
                insectType = insectType ?: "",
                problemDuration = problemDuration ?: "",
                requestedTime = requestedTime ?: "",
                requestedDate = requestedDate ?: ""
            )

            // Make the API call to insert the appointment
            RetrofitInstance.apiService.insertAppointment(appointment).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        Log.d("InfoConfirmation", "Response: ${response.body()}")
                        if (response.body()?.success == true) {
                            Log.d("InfoConfirmation", "Appointment inserted successfully")
                            val intent = Intent(this@InfoConfirmation, Confirmation::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                            finish()
                        } else {
                            Log.e("InfoConfirmation", "Error inserting appointment: ${response.message()}")
                            Toast.makeText(applicationContext, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.e("InfoConfirmation", "Error: ${response.errorBody()?.string()}")
                        Toast.makeText(applicationContext, "Request failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.e("InfoConfirmation", "Request failed: ${t.message}")
                    Toast.makeText(applicationContext, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }
}

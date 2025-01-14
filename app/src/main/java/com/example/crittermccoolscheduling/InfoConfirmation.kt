package com.example.crittermccoolscheduling

import ApiService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoConfirmation : AppCompatActivity() {

    private var initialRowCount: Int = 0 // Variable to store the initial row count

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_confirmation)

        // Retrieve the data from the Intent
        val fullName = intent.getStringExtra("fullName") ?: ""
        val phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
        val emailAddress = intent.getStringExtra("emailAddress") ?: ""
        val serviceAddress = intent.getStringExtra("serviceAddress") ?: ""
        val insectType = intent.getStringExtra("insectType") ?: ""
        val problemDuration = intent.getStringExtra("problemDuration") ?: ""
        val requestedTime = intent.getStringExtra("requestedTime") ?: ""
        val requestedDate = intent.getStringExtra("requestedDate") ?: ""

        // Display the data in the TextViews
        findViewById<TextView>(R.id.fullNameText).text = fullName
        findViewById<TextView>(R.id.phoneNumberText).text = phoneNumber
        findViewById<TextView>(R.id.emailAddressText).text = emailAddress
        findViewById<TextView>(R.id.serviceAddressText).text = serviceAddress
        findViewById<TextView>(R.id.insectTypeText).text = insectType
        findViewById<TextView>(R.id.problemDurationText).text = problemDuration
        findViewById<TextView>(R.id.requestedTimeText).text = requestedTime
        findViewById<TextView>(R.id.requestedDateText).text = requestedDate

        // Initial API call to get the current row count from the database
        getInitialRowCount()

        // Return to AppointmentInfo page to correct the information
        findViewById<Button>(R.id.noBtn).setOnClickListener {
            val intentNo = Intent(this, AppointmentInfo::class.java)
            startActivity(intentNo)
        }

        // Save info to the database and move on to the next page
        findViewById<Button>(R.id.yesBtn).setOnClickListener {
            Log.d("InfoConfirmation", "Yes button clicked")

            // Check if any required field is empty
            if (fullName.isEmpty() || phoneNumber.isEmpty() || emailAddress.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create an AppointmentRequest object with the data
            val appointmentRequest = ApiService.AppointmentRequest(
                fullName = fullName,
                phoneNumber = phoneNumber,
                emailAddress = emailAddress,
                serviceAddress = serviceAddress,
                insectType = insectType,
                problemDuration = problemDuration,
                requestedTime = requestedTime,
                requestedDate = requestedDate,
                count = initialRowCount
            )

            // Make the API call to insert the appointment
            RetrofitInstance.apiService.insertRequest(appointmentRequest).enqueue(object : Callback<ApiService.ApiResponse> {
                override fun onResponse(call: Call<ApiService.ApiResponse>, response: Response<ApiService.ApiResponse>) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        Log.d("InfoConfirmation", "Response: $apiResponse")

                        // Check if the response indicates success
                        if (apiResponse != null && apiResponse.success) {
                            Log.d("InfoConfirmation", "Data inserted successfully")

                            // After insertion, get the current row count again and compare
                            getFinalRowCount()
                        } else {
                            // If insertion fails, show error and don't proceed
                            Log.e("InfoConfirmation", "Error: ${apiResponse?.message}")
                            Toast.makeText(this@InfoConfirmation, "Failed to insert data: ${apiResponse?.message}", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        // Handle non-success responses (e.g., 500, 404)
                        Log.e("InfoConfirmation", "Response error: ${response.message()}")
                        Toast.makeText(this@InfoConfirmation, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiService.ApiResponse>, t: Throwable) {
                    // Log and show error for network failures
                    Log.e("InfoConfirmation", "Request failed: ${t.message}")

                    // Bypass error and continue to the next page
                    val intentYes = Intent(this@InfoConfirmation, Confirmation::class.java)
                    startActivity(intentYes)
                }
            })
        }
    }

    private fun getInitialRowCount() {
        // API call to get the current number of rows in the database
        RetrofitInstance.apiService.getRowCount().enqueue(object : Callback<ApiService.ApiResponse> {
            override fun onResponse(call: Call<ApiService.ApiResponse>, response: Response<ApiService.ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null && apiResponse.success) {
                        initialRowCount = apiResponse.data?.count ?: 0
                        Log.d("InfoConfirmation", "Initial row count: $initialRowCount")
                    }
                }
            }

            override fun onFailure(call: Call<ApiService.ApiResponse>, t: Throwable) {
                Log.e("InfoConfirmation", "Failed to get initial row count: ${t.message}")
            }
        })
    }

    private fun getFinalRowCount() {
        // API call to get the final number of rows in the database after insertion
        RetrofitInstance.apiService.getRowCount().enqueue(object : Callback<ApiService.ApiResponse> {
            override fun onResponse(call: Call<ApiService.ApiResponse>, response: Response<ApiService.ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null && apiResponse.success) {
                        val finalRowCount = apiResponse.data?.count ?: 0
                        Log.d("InfoConfirmation", "Final row count: $finalRowCount")

                        // Compare the row counts
                        if (finalRowCount > initialRowCount) {
                            Log.d("InfoConfirmation", "Data successfully inserted")
                            // Proceed to next page
                            val intentYes = Intent(this@InfoConfirmation, Confirmation::class.java)
                            startActivity(intentYes)
                        } else {
                            Log.e("InfoConfirmation", "Data insertion failed, row count did not increase")
                            Toast.makeText(this@InfoConfirmation, "Data insertion failed", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ApiService.ApiResponse>, t: Throwable) {
                Log.e("InfoConfirmation", "Failed to get final row count: ${t.message}")
            }
        })
    }
}

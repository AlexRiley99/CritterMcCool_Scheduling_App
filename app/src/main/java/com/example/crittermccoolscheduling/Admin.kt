package com.example.crittermccoolscheduling

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Admin : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TableItemAdapter
    private var tableItems: MutableList<ApiService.AppointmentRequest> =
        mutableListOf() // Assuming AppointmentRequest is the data class
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin)

        // Set up system bar insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the RecyclerView and ProgressBar
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        progressBar = findViewById(R.id.adminProgressBar)

        // Initialize the adapter and set it to the RecyclerView
        adapter = TableItemAdapter(
            tableItems, // Pass tableItems for both tableItems and appointments
            tableItems,
            RetrofitInstance.apiService, // Pass the apiService from RetrofitInstance
            { item: ApiService.AppointmentRequest -> deleteItem(item) } // Pass delete action
        )

        recyclerView.adapter = adapter

        // Load data from the API (via Retrofit)
        loadTableData()

        // Go back to Main activity when Exit button is clicked
        findViewById<Button>(R.id.exitButton).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadTableData() {
        // Clear any existing data
        tableItems.clear()
        progressBar.visibility = View.VISIBLE // Show progress bar

        // Make the network call to fetch appointments
        RetrofitInstance.apiService.getAllRequests()
            .enqueue(object : Callback<List<ApiService.AppointmentRequest>> {
                override fun onResponse(
                    call: Call<List<ApiService.AppointmentRequest>>,
                    response: Response<List<ApiService.AppointmentRequest>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            tableItems.addAll(it)
                            adapter.setItems(tableItems) // Update the RecyclerView adapter
                        }
                    } else {
                        Toast.makeText(this@Admin, "Failed to load data", Toast.LENGTH_SHORT).show()
                    }
                    progressBar.visibility = View.GONE // Hide progress bar after loading
                }

                override fun onFailure(
                    call: Call<List<ApiService.AppointmentRequest>>,
                    t: Throwable
                ) {
                    Toast.makeText(this@Admin, "Request failed: ${t.message}", Toast.LENGTH_SHORT)
                        .show()
                    progressBar.visibility = View.GONE // Hide progress bar on failure
                }
            })
    }

    private fun deleteItem(item: ApiService.AppointmentRequest) {
        // Check if the id is non-null before calling the API
        val id = item.id
        if (id != null) {
            // Make the network call to delete the selected appointment using the id
            RetrofitInstance.apiService.deleteRequest(id)
                .enqueue(object : Callback<ApiService.ApiResponse> {
                    override fun onResponse(
                        call: Call<ApiService.ApiResponse>,
                        response: Response<ApiService.ApiResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { apiResponse ->
                                // Check the success field in ApiResponse
                                if (apiResponse.success) {
                                    Toast.makeText(
                                        this@Admin,
                                        "Appointment deleted successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    loadTableData() // Refresh the data in the RecyclerView
                                } else {
                                    Toast.makeText(
                                        this@Admin,
                                        "Failed to delete appointment",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                this@Admin,
                                "Failed to delete appointment",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiService.ApiResponse>, t: Throwable) {
                        Toast.makeText(
                            this@Admin,
                            "Request failed: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        } else {
            Toast.makeText(this@Admin, "Appointment ID is null", Toast.LENGTH_SHORT).show()
        }
    }

}

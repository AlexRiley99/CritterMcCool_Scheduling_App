package com.example.crittermccoolscheduling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
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
import kotlinx.coroutines.*

class Admin : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TableItemAdapter
    private var tableItems: MutableList<DatabaseHelper.RequestedAppointment> = mutableListOf()
    private lateinit var appointmentRepository: DatabaseRepository

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

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the repository
        val dbHelper = DatabaseHelper()
        appointmentRepository = DatabaseRepository(dbHelper)

        // Initialize the adapter and set it to the RecyclerView
        adapter = TableItemAdapter(tableItems) { item: DatabaseHelper.RequestedAppointment -> deleteItem(item) }
        recyclerView.adapter = adapter

        // Load data from the database (via the repository)
        loadTableData()

        // Go back to Main activity when Exit button is clicked
        val Exit_Btn: Button = findViewById(R.id.exitButton)
        Exit_Btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadTableData() {
        //Clear any existing data
        tableItems.clear()
        findViewById<ProgressBar>(R.id.adminProgressBar).visibility = View.VISIBLE //Make progress bar visible

        //Launch a coroutine to run the network call in the background, to prevent performance issues/UI freezing
        CoroutineScope(Dispatchers.IO).launch{
            val appointments = appointmentRepository.getAllAppointments()

            //Add fetched appointment requests to the list and update the adapter
            withContext(Dispatchers.Main) {
                tableItems.addAll(appointments)
                adapter.setItems(tableItems)
                findViewById<ProgressBar>(R.id.adminProgressBar).visibility = View.GONE //Hide progress bar when done loading information
            }
        }
    }


    private fun deleteItem(item: DatabaseHelper.RequestedAppointment) {
        // Delete the selected item via the repository
        val success = if (item.id != null) {
            appointmentRepository.deleteAppointment(item.id)
        } else {
            Toast.makeText(this, "Invalid item ID", Toast.LENGTH_SHORT).show()
            false
        }


        // Handle success or failure based on the result
        if (success) {
            Toast.makeText(this, "Item deleted successfully", Toast.LENGTH_SHORT).show()
            loadTableData() // Refresh the data in the RecyclerView
        } else {
            Toast.makeText(this, "Failed to delete item", Toast.LENGTH_SHORT).show()
        }
    }
}

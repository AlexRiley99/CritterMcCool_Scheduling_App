package com.example.crittermccoolscheduling

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Admin : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TableItemAdapter
    private var tableItems: MutableList<Appointment> = mutableListOf()
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var appointmentRepository: AppointmentRepository

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

        // Initialize the database and repository
        dbHelper = DatabaseHelper(this)
        appointmentRepository = AppointmentRepository(dbHelper)

        // Initialize the adapter and set it to the RecyclerView
        adapter = TableItemAdapter(tableItems) { item -> deleteItem(item) }
        recyclerView.adapter = adapter

        // Load data from the database
        loadTableData()
    }

    private fun loadTableData() {
        tableItems.clear()
        tableItems.addAll(appointmentRepository.getAllAppointments())
        adapter.setItems(tableItems)
    }

    private fun deleteItem(item: Appointment) {
        val success = appointmentRepository.deleteAppointment(item.ID)

        if (success > 0) {
            Toast.makeText(this, "Item deleted successfully", Toast.LENGTH_SHORT).show()
            loadTableData() // Refresh the data in the RecyclerView
        } else {
            Toast.makeText(this, "Failed to delete item", Toast.LENGTH_SHORT).show()
        }
    }
}

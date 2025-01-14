package com.example.crittermccoolscheduling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter for displaying the list of appointments in the RecyclerView on Admin page
class TableItemAdapter(
    private var appointments: List<DatabaseHelper.RequestedAppointment>, // Data source (appointments list)
    private val onDeleteClick: (DatabaseHelper.RequestedAppointment) -> Unit // Lambda function to handle delete button clicks
) : RecyclerView.Adapter<TableItemAdapter.AppointmentViewHolder>() {

    // ViewHolder for the RecyclerView
    inner class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName: TextView = itemView.findViewById(R.id.customerName)
        val typeOfInsect: TextView = itemView.findViewById(R.id.typeOfInsect)
        val durationOfProblem: TextView = itemView.findViewById(R.id.durationOfProblem)
        val addressOfService: TextView = itemView.findViewById(R.id.addressOfService)
        val customerPhone: TextView = itemView.findViewById(R.id.customerPhone)
        val customerEmail: TextView = itemView.findViewById(R.id.customerEmail)
        val requestedTimeOfDay: TextView = itemView.findViewById(R.id.requestedTimeOfDay)
        val requestedDateRange: TextView = itemView.findViewById(R.id.requestedDateRange)
        val deleteButton: View = itemView.findViewById(R.id.deleteButton)

        // Bind the appointment data to the views
        fun bind(appointment: DatabaseHelper.RequestedAppointment) {
            customerName.text = appointment.fullName
            typeOfInsect.text = appointment.insectType
            durationOfProblem.text = appointment.problemDuration
            addressOfService.text = appointment.serviceAddress
            customerPhone.text = appointment.phoneNumber
            customerEmail.text = appointment.emailAddress
            requestedTimeOfDay.text = appointment.requestedTime
            requestedDateRange.text = appointment.requestedDate

            // Handle the delete button click
            deleteButton.setOnClickListener { onDeleteClick(appointment) }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false) // Inflate the custom item layout
        return AppointmentViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.bind(appointment)
    }

    // Return the size of the dataset
    override fun getItemCount(): Int {
        return appointments.size
    }

    // Set the new list of appointments when data changes
    fun setItems(newAppointments: List<DatabaseHelper.RequestedAppointment>) {
        appointments = newAppointments
        notifyDataSetChanged() // Notify the adapter that data has changed
    }
}

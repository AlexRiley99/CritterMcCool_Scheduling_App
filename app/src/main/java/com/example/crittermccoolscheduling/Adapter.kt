package com.example.crittermccoolscheduling

import ApiService
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Adapter for displaying the list of appointments in the RecyclerView on Admin page
class TableItemAdapter(
    private var tableItems: MutableList<ApiService.AppointmentRequest>,
    private var appointments: List<ApiService.AppointmentRequest>,
    private val apiService: ApiService,
    private val onDeleteClick: (ApiService.AppointmentRequest) -> Unit
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
        fun bind(appointment: ApiService.AppointmentRequest) {
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

    // Create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false) // Inflate the custom item layout
        return AppointmentViewHolder(itemView)
    }

    // Replace the contents of a view
    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.bind(appointment)
    }

    // Return the size of the dataset
    override fun getItemCount(): Int {
        return appointments.size
    }

    // Set the new list of appointments when data changes
    fun setItems(newAppointments: List<ApiService.AppointmentRequest>) {
        appointments = newAppointments
        notifyDataSetChanged() // Notify the adapter that data has changed
    }

    // Function to delete appointment by ID via Retrofit
    fun deleteAppointment(appointment: ApiService.AppointmentRequest) {
        apiService.deleteRequest(appointment.id ?: 0).enqueue(object : Callback<ApiService.ApiResponse> {
            override fun onResponse(
                call: Call<ApiService.ApiResponse>,
                response: Response<ApiService.ApiResponse>
            ) {

            }
            override fun onFailure(call: Call<ApiService.ApiResponse>, t: Throwable) {
                Log.e("ApiError", "Failed to delete appointment: ${t.message}")
            }
        })
    }

}

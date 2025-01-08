package com.example.crittermccoolscheduling

import android.content.Context

//Intermediary between DatabaseHelper and the UI
//Ensures other parts of the app don't directly interact with the database

class AppointmentRepository(context: Context) {
    private val databaseHelper = DatabaseHelper(context)

    //Insert appointment
    fun insertAppointment(Name: String, Phone:String, Email: String, Address: String, Insect_Type: String, Problem_Duration: String, Date: String, Time: String){
        return databaseHelper.insertAppointment(Name, Phone, Email, Address, Insect_Type, Problem_Duration, Date, Time)
    }

    fun getAppointmentByID(ID: Long) : Appointment?{
        return databaseHelper.getAppointmentById(ID)
    }

    fun getAllAppointments() : List<Appointment>{
        return databaseHelper.getAllAppointments()
    }

    fun deleteAppointment(ID: Long): Int{
        return databaseHelper.deleteAppointment(ID)
    }

    fun deleteAllAppointments(){
        databaseHelper.deleteAllAppointments()
    }
}
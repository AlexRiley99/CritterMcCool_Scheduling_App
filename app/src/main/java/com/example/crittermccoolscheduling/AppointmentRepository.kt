package com.example.crittermccoolscheduling

import android.content.Context

//Intermediary between DatabaseHelper and the UI
//Ensures other parts of the app don't directly interact with the database

class AppointmentRepository(private val dbHelper: DatabaseHelper) {

    //Insert appointment
    fun insertAppointment(Name: String, Phone:String, Email: String, Address: String, Insect_Type: String, Problem_Duration: String, Date: String, Time: String) : Long {
        return dbHelper.insertAppointment(Name, Phone, Email, Address, Insect_Type, Problem_Duration, Date, Time)
    }

    fun getAppointmentByID(ID: Long) : Appointment?{
        return dbHelper.getAppointmentById(ID)
    }

    fun getAllAppointments() : List<Appointment>{
        return dbHelper.getAllAppointments()
    }

    fun deleteAppointment(ID: Long): Int{
        return dbHelper.deleteAppointment(ID)
    }

    fun deleteAllAppointments(){
        dbHelper.deleteAllAppointments()
    }
}
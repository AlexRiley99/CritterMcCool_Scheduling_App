package com.example.crittermccoolscheduling

data class Appointment(
    val fullName: String,
    val phoneNumber: String,
    val emailAddress: String,
    val serviceAddress: String,
    val insectType: String,
    val problemDuration: String,
    val requestedTime: String,
    val requestedDate: String
)

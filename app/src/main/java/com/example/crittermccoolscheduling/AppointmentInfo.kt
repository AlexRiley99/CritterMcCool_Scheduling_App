package com.example.crittermccoolscheduling

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.EditText

class AppointmentInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_appointment_info)

            val insectType: Spinner = findViewById(R.id.insectType) //Defining spinner
            val items = listOf("*What Type of Stinging Insect Is It?", "Bees", "Wasps", "Yellow Jackets", "Hornets", "Other", "Not Sure") //List of items for the drop down list

            //Creating ArrayAdapter to bind the list to the spinner
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //Setting the adapter to the spinner
            insectType.adapter = adapter

            //Handler for the "Next" button
            val Next_Btn: Button = findViewById(R.id.Next_Btn)

            Next_Btn.setOnClickListener {
                val problemDuration_txt = findViewById<EditText>(R.id.problemDuration)//Text entered into the problemDuration field
                val fullName_txt = findViewById<EditText>(R.id.fullName)//Text entered into the fullName field
                val phoneNumber_txt = findViewById<EditText>(R.id.phoneNumber)//Information entered into the phoneNumber field
                val serviceAddress_txt = findViewById<EditText>(R.id.serviceAddress)//Text entered into the serviceAddress field
                val emailAddress_txt = findViewById<EditText>(R.id.emailAddress)//Text entered into the emailAddress field
                val selectedItem = insectType.selectedItem//Item selected in the insectType spinner

                val intent = Intent(this, DateTime::class.java)
                startActivity(intent)
                    intent.putExtra("fullName", fullName_txt.text.toString())
                    intent.putExtra("phoneNumber", phoneNumber_txt.text.toString())
                    intent.putExtra("emailAddress", emailAddress_txt.text.toString())
                    intent.putExtra("serviceAddress", serviceAddress_txt.text.toString())
                    intent.putExtra("insectType", selectedItem.toString())
                    intent.putExtra("problemDuration", problemDuration_txt.text.toString())
                    startActivity(intent)
                } //Start DateTime and pass information to it

                //Handler for the "Back" button
                val Back_Btn: Button = findViewById(R.id.appointmentInfo_back_btn)
                Back_Btn.setOnClickListener{
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }

        }


package com.example.crittermccoolscheduling

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

class AppointmentInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_appointment_info)

            val insectType: Spinner = findViewById(R.id.insectType) //Defining spinner
            val items = listOf("Bees", "Wasps", "Yellow Jackets", "Hornets", "Other", "Not Sure") //List of items for the drop down list

            //Creating ArrayAdapter to bind the list to the spinner
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //Setting the adapter to the spinner
            insectType.adapter = adapter

            //Item selected listener for the spinner
            insectType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parentView: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                    val selectedItem = parentView?.getItemAtPosition(position).toString()
                    Toast.makeText(
                        applicationContext,
                        "Selected: $selectedItem",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onNothingSelected(parentView: AdapterView<*>?){
                    //no item selected
                }
            }
        //Handler for the "Next" button
        val Next_Btn: Button = findViewById(R.id.Next_Btn)
        Next_Btn.setOnClickListener{
            val intent = Intent(this, DateTime::class.java)
            startActivity(intent)
        }
    }
}
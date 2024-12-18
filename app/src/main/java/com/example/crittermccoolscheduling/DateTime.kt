package com.example.crittermccoolscheduling

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DateTime : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Handler for the "Finish" button
        val Finish_Btn: Button = findViewById(R.id.Finish_Btn)
        Finish_Btn.setOnClickListener{
            //Ensure that information is entered before the user continues
            val time_txt = findViewById<EditText>(R.id.time).text.toString().trim()
            val date_txt = findViewById<EditText>(R.id.date).text.toString().trim()
            if(time_txt.isEmpty() || date_txt.isEmpty()){
                Toast.makeText(applicationContext, "*Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, InfoConfirmation::class.java)
                startActivity(intent)
            }
        }

        //Handler for the "Back" button
        val Back_Btn: Button = findViewById(R.id.dateTime_back_btn)
        Back_Btn.setOnClickListener{
            val intent = Intent(this, AppointmentInfo::class.java)
            startActivity(intent)
        }
    }

}
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
        enableEdgeToEdge()
        setContentView(R.layout.activity_date_time)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.problemDuration)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
    }

}
package com.example.crittermccoolscheduling

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InfoConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_info_confirmation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Handler for the "No" button
        val No_Btn: Button = findViewById(R.id.noBtn)
        No_Btn.setOnClickListener{
            val intent = Intent(this, AppointmentInfo::class.java)
            startActivity(intent)
        }

        //Handler for the "Yes" button
        val Yes_Btn: Button = findViewById(R.id.yesBtn)
        Yes_Btn.setOnClickListener{
            val intent = Intent(this, Confirmation::class.java)
            startActivity(intent)
        }
    }
}
package com.example.crittermccoolscheduling

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdminLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        val id_txt = findViewById<EditText>(R.id.id_txt) // id code input
        val password_txt = findViewById<EditText>(R.id.password_txt) // Password input

        // Handler for "Sign In" button
        val login_Btn: Button = findViewById(R.id.login_btn)
        login_Btn.setOnClickListener {
            // Get the text input when the button is clicked
            val id = id_txt.text.toString().trim()
            val password = password_txt.text.toString().trim()

            // Log the entered credentials for debugging
            Log.d("AdminLogin", "Entered credentials: id = $id, password = $password")

            if (id == "B7v&1rPz9@" && password == "BeeLegend100%") {
                // Log the successful login
                Log.d("AdminLogin", "Credentials correct, navigating to Admin activity")

                // If credentials match, start the Admin activity
                val intent = Intent(this, Admin::class.java)
                startActivity(intent)

            } else {
                // Log the failed attempt
                Log.d("AdminLogin", "Invalid credentials")

                // Display message and clear input fields if the credentials are incorrect
                Toast.makeText(applicationContext, "*Invalid credentials", Toast.LENGTH_SHORT).show()
                id_txt.text.clear()
                password_txt.text.clear()
            }
        }

        // Handler for "Back" button
        val back_Btn: Button = findViewById(R.id.adminLogin_back_btn)
        back_Btn.setOnClickListener {
            // Take the user back to the main page
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}

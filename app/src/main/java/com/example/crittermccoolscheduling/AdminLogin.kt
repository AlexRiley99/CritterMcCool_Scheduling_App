package com.example.crittermccoolscheduling

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdminLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        val username_txt = findViewById<EditText>(R.id.username_txt) //Username input
        val password_txt = findViewById<EditText>(R.id.password_txt)//Password input

        //Handler for "Sign In" button
        val login_Btn: Button = findViewById(R.id.login_btn)
        login_Btn.setOnClickListener{
                //Get the text input when the button is clicked
                val username = username_txt.text.toString().trim()
                val password = password_txt.text.toString().trim()

            if(username == "EricCritterMcCool74$" && password == "BeeLegend100%"){
                val intent = Intent(this, Admin::class.java)
                startActivity(intent)
            }
            else{
                //Display message and clear input fields if the credentials are incorrect
                Toast.makeText(applicationContext, "*Invalid credentials", Toast.LENGTH_SHORT).show()
                username_txt.text.clear()
                password_txt.text.clear()
            }

        //Handler for "Back" button
        val back_Btn: Button = findViewById(R.id.adminLogin_back_btn)
        back_Btn.setOnClickListener{
                //Take the user back to the main page
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
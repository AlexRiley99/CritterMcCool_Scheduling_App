package com.example.crittermccoolscheduling

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Appointment data class
data class Appointment(
    val ID: Long,
    val Name: String,
    val Phone: String,
    val Email: String,
    val Address: String,
    val Insect_Type: String,
    val Problem_Duration: String,
    val Date: String,
    val Time: String
)

// Handles database functions
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "Appointments.db"
        const val DATABASE_VERSION = 1

        // Table Structure
        const val TABLE_APPOINTMENTS = "Appointments"
        const val COLUMN_ID = "ID"
        const val COLUMN_NAME = "Name"
        const val COLUMN_PHONE = "Phone"
        const val COLUMN_EMAIL = "Email"
        const val COLUMN_ADDRESS = "Address"
        const val COLUMN_INSECT_TYPE = "Insect_Type"
        const val COLUMN_PROBLEM_DURATION = "Problem_Duration"
        const val COLUMN_DATE = "Date"
        const val COLUMN_TIME = "Time"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
        CREATE TABLE $TABLE_APPOINTMENTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_PHONE TEXT,
                $COLUMN_EMAIL TEXT, 
                $COLUMN_ADDRESS TEXT, 
                $COLUMN_INSECT_TYPE TEXT, 
                $COLUMN_PROBLEM_DURATION TEXT,
                $COLUMN_DATE TEXT,
                $COLUMN_TIME TEXT
                )
            """
        db?.execSQL(createTableQuery) // Execute SQL query to create the table
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_APPOINTMENTS")
        onCreate(db) // Recreate table when upgrading database
    }

    // Insert a new appointment into the database
    fun insertAppointment(
        Name: String, Phone: String, Email: String, Address: String,
        Insect_Type: String, Problem_Duration: String, Date: String, Time: String
    ): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, Name)
            put(COLUMN_PHONE, Phone)
            put(COLUMN_EMAIL, Email)
            put(COLUMN_ADDRESS, Address)
            put(COLUMN_INSECT_TYPE, Insect_Type)
            put(COLUMN_PROBLEM_DURATION, Problem_Duration)
            put(COLUMN_DATE, Date)
            put(COLUMN_TIME, Time)
        }

        val id = db.insert(TABLE_APPOINTMENTS, null, contentValues)  // Insert and return the new row ID
        db.close()
        return id
    }

    // Get all appointments from the database
    fun getAllAppointments(): List<Appointment> {
        val db = readableDatabase // Get a readable database instance
        val cursor = db.query(
            TABLE_APPOINTMENTS, null, null, null, null, null, null
        )

        val appointments = mutableListOf<Appointment>()

        // Ensure columns exist before accessing them
        val idIndex = cursor.getColumnIndex(COLUMN_ID)
        val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
        val phoneIndex = cursor.getColumnIndex(COLUMN_PHONE)
        val emailIndex = cursor.getColumnIndex(COLUMN_EMAIL)
        val addressIndex = cursor.getColumnIndex(COLUMN_ADDRESS)
        val insectTypeIndex = cursor.getColumnIndex(COLUMN_INSECT_TYPE)
        val problemDurationIndex = cursor.getColumnIndex(COLUMN_PROBLEM_DURATION)
        val dateIndex = cursor.getColumnIndex(COLUMN_DATE)
        val timeIndex = cursor.getColumnIndex(COLUMN_TIME)

        while (cursor.moveToNext()) {
            if (nameIndex != -1 && phoneIndex != -1 && emailIndex != -1 && addressIndex != -1 &&
                insectTypeIndex != -1 && problemDurationIndex != -1 && dateIndex != -1 && timeIndex != -1) {
                val ID = cursor.getLong(idIndex)
                val Name = cursor.getString(nameIndex)
                val Phone = cursor.getString(phoneIndex)
                val Email = cursor.getString(emailIndex)
                val Address = cursor.getString(addressIndex)
                val Insect_Type = cursor.getString(insectTypeIndex)
                val Problem_Duration = cursor.getString(problemDurationIndex)
                val Date = cursor.getString(dateIndex)
                val Time = cursor.getString(timeIndex)

                appointments.add(Appointment(ID, Name, Phone, Email, Address, Insect_Type, Problem_Duration, Date, Time))
            }
        }
        cursor.close() // Close cursor
        db.close() // Close database
        return appointments
    }

    // Get an appointment by ID
    fun getAppointmentById(ID: Long): Appointment? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_APPOINTMENTS, null, "$COLUMN_ID = ?", arrayOf(ID.toString()), null, null, null
        )

        // Ensure columns exist before accessing them
        val idIndex = cursor.getColumnIndex(COLUMN_ID)
        val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
        val phoneIndex = cursor.getColumnIndex(COLUMN_PHONE)
        val emailIndex = cursor.getColumnIndex(COLUMN_EMAIL)
        val addressIndex = cursor.getColumnIndex(COLUMN_ADDRESS)
        val insectTypeIndex = cursor.getColumnIndex(COLUMN_INSECT_TYPE)
        val problemDurationIndex = cursor.getColumnIndex(COLUMN_PROBLEM_DURATION)
        val dateIndex = cursor.getColumnIndex(COLUMN_DATE)
        val timeIndex = cursor.getColumnIndex(COLUMN_TIME)

        if (cursor.moveToFirst() && nameIndex != -1 && phoneIndex != -1 && emailIndex != -1 &&
            addressIndex != -1 && insectTypeIndex != -1 && problemDurationIndex != -1 &&
            dateIndex != -1 && timeIndex != -1) {
            val ID = cursor.getLong(idIndex)
            val Name = cursor.getString(nameIndex)
            val Phone = cursor.getString(phoneIndex)
            val Email = cursor.getString(emailIndex)
            val Address = cursor.getString(addressIndex)
            val Insect_Type = cursor.getString(insectTypeIndex)
            val Problem_Duration = cursor.getString(problemDurationIndex)
            val Date = cursor.getString(dateIndex)
            val Time = cursor.getString(timeIndex)

            cursor.close()
            db.close()
            return Appointment(ID, Name, Phone, Email, Address, Insect_Type, Problem_Duration, Date, Time)
        }

        cursor.close()
        db.close()
        return null
    }

    // Delete an appointment by ID
    fun deleteAppointment(ID: Long): Int {
        val db = writableDatabase
        val rowsAffected = db.delete(
            TABLE_APPOINTMENTS,
            "$COLUMN_ID = ?", // WHERE clause
            arrayOf(ID.toString()) // argument for WHERE clause
        )
        db.close()
        return rowsAffected
    }

    // Delete all appointments
    fun deleteAllAppointments() {
        val db = writableDatabase
        db.delete(TABLE_APPOINTMENTS, null, null)
        db.close()
    }
}

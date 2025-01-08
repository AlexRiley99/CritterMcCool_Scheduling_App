package com.example.crittermccoolscheduling

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Appointment data class
data class Appointment(
    val ID: Long,
    val Name: String,
    val Phone: String,
    val Email: String,
    val Address: String,
    val Insect_Type: String,
    val Other: String,
    val Problem_Duration: String
)

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        const val DATABASE_NAME = "Appointments.db"
        const val DATABASE_VERSION = 1

        //Table Structure
        const val TABLE_APPOINTMENTS = "Appointments"
        const val COLUMN_ID = "ID"
        const val COLUMN_NAME = "Name"
        const val COLUMN_PHONE = "Phone"
        const val COLUMN_EMAIL = "Email"
        const val COLUMN_ADDRESS = "Address"
        const val COLUMN_INSECT_TYPE = "Insect_Type"
        const val COLUMN_OTHER = "Other"
        const val COLUMN_PROBLEM_DURATION = "Problem_Duration"
    }

    override fun onCreate(db: SQLiteDatabase?){
        val createTableQuery = """
        CREATE TABLE $TABLE_APPOINTMENTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_PHONE TEXT,
                $COLUMN_EMAIL TEXT, 
                $COLUMN_ADDRESS TEXT, 
                $COLUMN_INSECT_TYPE TEXT, 
                $COLUMN_OTHER TEXT, 
                $COLUMN_PROBLEM_DURATION TEXT
                )
            """
        db?.execSQL(createTableQuery) //Execute SQL query to create the table
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_APPOINTMENTS")
        onCreate(db) //Recreate table when upgrading database
    }

    fun insertAppointment(Name: String, Phone: Int, Email: String, Address: String, Insect_Type: String, Other: String, Problem_Duration: String){
        val db = writableDatabase //get a writable SQLiteDatabase instance
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, Name)
        contentValues.put(COLUMN_PHONE, Phone)
        contentValues.put(COLUMN_EMAIL, Email)
        contentValues.put(COLUMN_ADDRESS, Address)
        contentValues.put(COLUMN_INSECT_TYPE, Insect_Type)
        contentValues.put(COLUMN_OTHER, Other)
        contentValues.put(COLUMN_PROBLEM_DURATION, Problem_Duration)

        //Insert data into the table
        db.insert(TABLE_APPOINTMENTS, null, contentValues)
        db.close() //close database after operations
    }

    fun getAllAppointments(): List<Appointment>{
        val db = readableDatabase //Get a readable database instance
        val cursor = db.query(
            TABLE_APPOINTMENTS, null, null, null, null, null, null
        )

        val Appointments = mutableListOf<Appointment>()

        //Ensure columns exist before accessing them
        val idIndex = cursor.getColumnIndex(COLUMN_ID)
        val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
        val phoneIndex = cursor.getColumnIndex(COLUMN_PHONE)
        val emailIndex = cursor.getColumnIndex(COLUMN_EMAIL)
        val addressIndex = cursor.getColumnIndex(COLUMN_ADDRESS)
        val insectTypeIndex = cursor.getColumnIndex(COLUMN_INSECT_TYPE)
        val otherIndex = cursor.getColumnIndex(COLUMN_OTHER)
        val problemDurationIndex = cursor.getColumnIndex(COLUMN_PROBLEM_DURATION)

        while(cursor.moveToNext()){
            if(nameIndex != -1 && phoneIndex != -1 && emailIndex != -1 && addressIndex != -1 && insectTypeIndex != -1 && otherIndex != -1 && problemDurationIndex != -1){
                val ID = cursor.getLong(idIndex)
                val Name = cursor.getString(nameIndex)
                val Phone = cursor.getString(phoneIndex)
                val Email = cursor.getString(emailIndex)
                val Address = cursor.getString(addressIndex)
                val Insect_Type = cursor.getString(insectTypeIndex)
                val Other = cursor.getString(otherIndex)
                val Problem_Duration = cursor.getString(problemDurationIndex)

                Appointments.add(Appointment(ID, Name, Phone, Email, Address, Insect_Type, Other, Problem_Duration))
            }
        }
        cursor.close() //close cursor
        db.close() //close database
        return Appointments
    }

    fun getAppointmentById(ID: Long): Appointment? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_APPOINTMENTS, null, "$COLUMN_ID = ?", arrayOf(ID.toString()), null, null, null
        )

        //Ensure columns exist before accessing them
        val idIndex = cursor.getColumnIndex(COLUMN_ID)
        val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
        val phoneIndex = cursor.getColumnIndex(COLUMN_PHONE)
        val emailIndex = cursor.getColumnIndex(COLUMN_EMAIL)
        val addressIndex = cursor.getColumnIndex(COLUMN_ADDRESS)
        val insectTypeIndex = cursor.getColumnIndex(COLUMN_INSECT_TYPE)
        val otherIndex = cursor.getColumnIndex(COLUMN_OTHER)
        val problemDurationIndex = cursor.getColumnIndex(COLUMN_PROBLEM_DURATION)

        if(cursor.moveToFirst() && nameIndex != -1 && phoneIndex != -1 && emailIndex != -1 && addressIndex != -1 && insectTypeIndex != -1 && otherIndex != -1 && problemDurationIndex != -1){
                val ID = cursor.getLong(idIndex)
                val Name = cursor.getString(nameIndex)
                val Phone = cursor.getString(phoneIndex)
                val Email = cursor.getString(emailIndex)
                val Address = cursor.getString(addressIndex)
                val Insect_Type = cursor.getString(insectTypeIndex)
                val Other = cursor.getString(otherIndex)
                val Problem_Duration = cursor.getString(problemDurationIndex)

            cursor.close()
            db.close()
            return Appointment(ID, Name, Phone, Email, Address, Insect_Type, Other, Problem_Duration)
        }
        cursor.close()
        db.close()
        return null
    }

    fun deleteAppointment(ID: Long): Int{
        val db = writableDatabase
        val rowsAffected = db.delete(
            TABLE_APPOINTMENTS,
            "$COLUMN_ID = ?",//WHERE clause
            arrayOf(ID.toString()) //argument for WHERE clause
        )
        db.close()
        return rowsAffected
    }

    fun deleteAllAppointments(){
        val db = writableDatabase
        db.delete(TABLE_APPOINTMENTS, null, null)
        db.close()
    }
}



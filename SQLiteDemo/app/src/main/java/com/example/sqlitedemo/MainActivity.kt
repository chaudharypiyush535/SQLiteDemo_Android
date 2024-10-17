package com.example.sqlitedemo

import android.annotation.SuppressLint
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    lateinit var dbHelper : DatabaseHelper
    lateinit var etName : EditText
    lateinit var etEmail : EditText
    lateinit var btnInsert : Button
    lateinit var btnView : Button
    lateinit var tvResult : TextView

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        btnInsert = findViewById(R.id.btnInsert)
        btnView = findViewById(R.id.btnView)
        tvResult = findViewById(R.id.tvResult)

        dbHelper = DatabaseHelper(this)

        btnInsert.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                val id = dbHelper.insertUser(name, email)
                if (id > 0) {
                    Toast.makeText(this, "User inserted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Insertion failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            }
        }

        btnView.setOnClickListener {
            val cursor: Cursor = dbHelper.getAllUsers()
            if (cursor.moveToFirst()) {
                val result = StringBuilder()
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val email = cursor.getString(cursor.getColumnIndex("email"))
                    result.append("ID: $id, Name: $name, Email: $email\n")
                } while (cursor.moveToNext())
                tvResult.text = result.toString()
            } else {
                tvResult.text = "No users found."
            }
            cursor.close()
        }
    }
}
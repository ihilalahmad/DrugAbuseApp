package com.example.drugabuseapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.drugabuseapp.R

class MainActivity : AppCompatActivity() {

    lateinit var btnInsert: Button
    lateinit var btnFetch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInsert = findViewById(R.id.btnInsertData)
        btnFetch = findViewById(R.id.btnFetchData)

        btnInsert.setOnClickListener {
            val intent = Intent(this, DataInsertionActivity::class.java)
            startActivity(intent)
        }

        btnFetch.setOnClickListener {
            val intent = Intent(this, DataFetchingActivity::class.java)
            startActivity(intent)
        }
    }
}
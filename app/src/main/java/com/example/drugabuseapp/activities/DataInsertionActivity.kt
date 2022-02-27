package com.example.drugabuseapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.drugabuseapp.R
import com.example.drugabuseapp.model.DrugInfo
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DataInsertionActivity : AppCompatActivity() {

    lateinit var etDrugName: EditText
    lateinit var etDrugDesc: EditText
    lateinit var etSideEffect: EditText
    lateinit var etPrevention: EditText
    lateinit var etTreatment: EditText
    lateinit var btnSaveData: Button

    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_insetion)

        etDrugName = findViewById(R.id.etDrugName)
        etDrugDesc = findViewById(R.id.etDrugDesc)
        etSideEffect = findViewById(R.id.etSideEffect)
        etPrevention = findViewById(R.id.etPrevention)
        etTreatment = findViewById(R.id.etTreatment)
        btnSaveData = findViewById(R.id.btnSaveData)

        databaseReference = FirebaseDatabase.getInstance().getReference("drugInfo")

        btnSaveData.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {

        val drugName = etDrugName.text.toString()
        val drugDesc = etDrugDesc.text.toString()
        val sideEffect = etSideEffect.text.toString()
        val prevention = etPrevention.text.toString()
        val treatment = etTreatment.text.toString()

        if (drugName.isEmpty()) {
            etDrugName.error = "Please enter drug name"
            return
        }
        if (drugDesc.isEmpty()) {
            etDrugDesc.error = "Please enter drug desc"
            return
        }
        if (sideEffect.isEmpty()) {
            etSideEffect.error = "Please enter side effect"
            return
        }
        if (prevention.isEmpty()) {
            etPrevention.error = "Please enter prevention"
            return
        }
        if (treatment.isEmpty()) {
            etTreatment.error = "Please enter treatment"
            return
        }

        val drugId = databaseReference.push().key!!

        val drugInfo = DrugInfo(drugId,drugName, drugDesc, sideEffect, prevention, treatment)

        databaseReference.child(drugId).setValue(drugInfo)
            .addOnCompleteListener {
                Toast.makeText(applicationContext, "Data inserted Successfully", Toast.LENGTH_LONG).show()

                etDrugName.text.clear()
                etDrugDesc.text.clear()
                etSideEffect.text.clear()
                etPrevention.text.clear()
                etTreatment.text.clear()
            }
            .addOnFailureListener { ex ->
                Toast.makeText(applicationContext, "Insertion Error ${ex.message}", Toast.LENGTH_LONG).show()
            }

    }
}
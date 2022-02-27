package com.example.drugabuseapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.drugabuseapp.R
import com.example.drugabuseapp.model.DrugInfo
import com.google.firebase.database.FirebaseDatabase

class DrugDetailsActivity : AppCompatActivity() {

    private lateinit var tvDrugId: TextView
    private lateinit var tvDrugName: TextView
    private lateinit var tvDrugDesc: TextView
    private lateinit var tvSideEffect: TextView
    private lateinit var tvPrevention: TextView
    private lateinit var tvTreatment: TextView

    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drug_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("drugId").toString(),
                intent.getStringExtra("drugName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(intent.getStringExtra("drugId").toString())
        }

    }

    private fun initView() {
        tvDrugId = findViewById(R.id.tvDrugId)
        tvDrugName = findViewById(R.id.tvDrugName)
        tvDrugDesc = findViewById(R.id.tvDrugDescription)
        tvSideEffect = findViewById(R.id.tvSideEffect)
        tvPrevention = findViewById(R.id.tvPrevention)
        tvTreatment = findViewById(R.id.tvTreatment)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {

        tvDrugId.text = intent.getStringExtra("drugId")
        tvDrugName.text = intent.getStringExtra("drugName")
        tvDrugDesc.text = intent.getStringExtra("drugDesc")
        tvSideEffect.text = intent.getStringExtra("sideEffect")
        tvPrevention.text = intent.getStringExtra("prevention")
        tvTreatment.text = intent.getStringExtra("treatment")

    }

    private fun deleteRecord(id: String) {
        //create reference to database
        val DbRef = FirebaseDatabase.getInstance().getReference("drugInfo").child(id)
        //we referencing child here because we will be delete one record not whole data data in database
        //we will use generic Task here so lets do it..
        val mTask = DbRef.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(
                applicationContext,
                "Record deleted",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(this, DataFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }
            .addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Error deleting ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun openUpdateDialog(
        drugId: String,
        drugName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        //create views refernces

        //create views refernces
        val etDrugName = mDialogView.findViewById<EditText>(R.id.etDrugName)
        val etDrugDesc = mDialogView.findViewById<EditText>(R.id.etDrugDesc)
        val etSideEffect = mDialogView.findViewById<EditText>(R.id.etSideEffect)
        val etPrevention = mDialogView.findViewById<EditText>(R.id.etPrevention)
        val etTreatment = mDialogView.findViewById<EditText>(R.id.etTreatment)
        val btnUpdate = mDialogView.findViewById<Button>(R.id.btnUpdate)

        //setting current data to edit text for update
        etDrugName.setText(intent.getStringExtra("drugName").toString())
        etDrugDesc.setText(intent.getStringExtra("drugDesc").toString())
        etSideEffect.setText(intent.getStringExtra("sideEffect").toString())
        etPrevention.setText(intent.getStringExtra("prevention").toString())
        etTreatment.setText(intent.getStringExtra("treatment").toString())

        mDialog.setTitle("Updating $drugName record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener { //here we will update data in database
            //now get values from view
            updateData(
                drugId,
                etDrugName.text.toString(),
                etDrugDesc.text.toString(),
                etSideEffect.text.toString(),
                etPrevention.text.toString(),
                etTreatment.text.toString()
            )
            Toast.makeText(applicationContext, "Record Updated", Toast.LENGTH_SHORT).show()

            //set new data to text views
            tvDrugName.text = etDrugName.text.toString()
            tvDrugDesc.text = etDrugDesc.text.toString()
            tvSideEffect.text = etSideEffect.text.toString()
            tvPrevention.text = etPrevention.text.toString()
            tvTreatment.text = etTreatment.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateData(
        id: String,
        name: String,
        desc: String,
        sideEffect: String,
        prevention: String,
        treatment: String
    ) {
        //creating database reference
        val DbRef = FirebaseDatabase.getInstance().getReference("drugInfo").child(id)
        val drugInfo = DrugInfo(id, name, desc, sideEffect, prevention, treatment)
        DbRef.setValue(drugInfo)
    }
}
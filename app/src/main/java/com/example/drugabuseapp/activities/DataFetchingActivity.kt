package com.example.drugabuseapp.activities

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drugabuseapp.R
import com.example.drugabuseapp.adapter.DrugAdapter
import com.example.drugabuseapp.model.DrugModel
import com.google.firebase.database.*

class DataFetchingActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var drugRecyclerView : RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var drugList: ArrayList<DrugModel>

    lateinit var mIntent: Intent



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_fetching)

        drugRecyclerView = findViewById(R.id.rvDrugs)
        tvLoadingData = findViewById(R.id.tvLoadingData)
        drugRecyclerView.layoutManager = LinearLayoutManager(this)
        drugRecyclerView.setHasFixedSize(true)

        drugList = arrayListOf<DrugModel>()

        mIntent = Intent(this, DrugDetailsActivity::class.java)

        getDrugs()
    }

    private fun getDrugs() {

        drugRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        databaseReference = FirebaseDatabase.getInstance().getReference("drugInfo")

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                drugList.clear()

                if (snapshot.exists()) {

                    for (drugSnapShot in snapshot.children) {
                        val drug = drugSnapShot.getValue(DrugModel::class.java)
                        drugList.add(drug!!)
                        Log.i("DataBase", "Drug id: ${drugList[0].drugId} ${drugList[0].drugName} " )
                    }
                    val mAdapter = DrugAdapter(drugList)
                    drugRecyclerView.adapter = mAdapter


                    mAdapter.setOnItemClickListener(object : DrugAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            mIntent.putExtra("drugId", drugList[position].drugId)
                            mIntent.putExtra("drugName", drugList[position].drugName)
                            mIntent.putExtra("drugDesc", drugList[position].drugDesc)
                            mIntent.putExtra("sideEffect", drugList[position].sideEffect)
                            mIntent.putExtra("prevention", drugList[position].prevention)
                            mIntent.putExtra("treatment", drugList[position].treatment)
                            finish()
                            startActivity(mIntent)
                        }
                    })

                    drugRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}
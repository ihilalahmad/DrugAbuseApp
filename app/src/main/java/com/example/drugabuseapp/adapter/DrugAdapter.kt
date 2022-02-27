package com.example.drugabuseapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drugabuseapp.R
import com.example.drugabuseapp.model.DrugModel

class DrugAdapter(
    private val drugList: ArrayList<DrugModel>
) : RecyclerView.Adapter<DrugAdapter.ViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener : onItemClickListener) {
        mListener = clickListener
    }

//    private var onItemClickListener: ((DrugModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrugAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.drug_list_item,
                parent,
                false
            )
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: DrugAdapter.ViewHolder, position: Int) {

        val currentDrug = drugList[position]
        holder.drugName.text = currentDrug.drugName

//        holder.itemView.setOnClickListener {
//            onItemClickListener?.let { it(currentDrug) }
//        }
    }

    override fun getItemCount(): Int {
        return drugList.size
    }

    class ViewHolder(view: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        val drugName : TextView = view.findViewById(R.id.tvDrugName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

//    fun setOnItemClickListener(listener: (DrugModel) -> Unit) {
//        onItemClickListener = listener
//    }
}
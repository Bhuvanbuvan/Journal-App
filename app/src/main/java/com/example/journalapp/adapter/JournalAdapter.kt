package com.example.journalapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.journalapp.Journal
import com.example.journalapp.databinding.JournalItemBinding

class JournalAdapter(val context: Context,val journalList:List<Journal>)
    :RecyclerView.Adapter<JournalAdapter.MyViewHolder>() {

        private lateinit var binding: JournalItemBinding


     class MyViewHolder(private var inbinding: JournalItemBinding):RecyclerView.ViewHolder(inbinding.root){
        fun bind(currentItem: Journal) {
            inbinding.journal=currentItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        binding= JournalItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=journalList[position]
        holder.bind(currentItem)

    }


    override fun getItemCount(): Int {
        return journalList.size
    }
}
package com.example.notpad

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notpad.databinding.RecyclerHiddenNotesViewBinding

class HiddenNotes_RecyclerView_Adapter(private  val context: Context,private  val noteList: ArrayList<Note>):
    RecyclerView.Adapter<HiddenNotes_RecyclerView_Adapter.ViewHolder>() {

    class ViewHolder(binding: RecyclerHiddenNotesViewBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(RecyclerHiddenNotesViewBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("DO WHAT YOU WANT TO DO ")
    }
}
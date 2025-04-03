package com.example.notpad

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.notpad.databinding.RecyclerNotesViewBinding

class RecyclerViewAdapter(private val context: Context,private val noteList: ArrayList<Note>):RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

//    var onItemClick:((Note)->Unit)?=null

    class ViewHolder(binding: RecyclerNotesViewBinding):RecyclerView.ViewHolder(binding.root){
        val card = binding.cardRecyclerNotes
        val edtTitle = binding.edtTitleRecycleNotes
        val edtNote = binding.edtNoteRecycleNotes
        val btnDelete = binding.imgBtnCloseRecycleNotes
        val btnHide = binding.imgBtnHideRecycleNotes
        val rootView = binding.recyclerMainViewLayout

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerNotesViewBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int {
       return noteList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.card.setCardBackgroundColor(Color.parseColor(noteList[position].color))
        holder.edtTitle.setText(noteList[position].title)
        holder.edtNote.setText(noteList[position].note)
        if(noteList[position].hidden == 1){
            holder.btnHide.rotation = 180f
        }


        holder.rootView.setOnClickListener(){
            val intent = Intent(context,NotesEdit::class.java)
            intent.putExtra("Flag",true)
            intent.putExtra("NOTE",noteList[position])
            context.startActivity(intent)
        }

    // Hide Note Code Implementation
        holder.btnHide.setOnClickListener(){
            val dbNotes = DbNotes(context)
            val hidden = if(noteList[position].hidden==1){
                0
            } else {
                1
            }
            val flag = dbNotes.updateNote(Note(
                noteList[position].id,
                noteList[position].title,
                noteList[position].note,
                noteList[position].color,
                hidden
            ))

            if(hidden == 1){
                Toast.makeText(context,"Note Hidden ",Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(context,"Note Unhidden ",Toast.LENGTH_LONG).show()

            dbNotes.close()
            noteList.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()

        }

        holder.btnDelete.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setMessage("Do you realy want to delete this \"Note\" ")

            builder.setPositiveButton("Yes",{dil ,id ->
                val db = DbNotes(context)
                db.deleteNote(noteList[position].id)
                noteList.removeAt(position)
                notifyItemRemoved(position)
                notifyDataSetChanged()
                Toast.makeText(context,"Notes Deleted Successfully",Toast.LENGTH_LONG).show()
                dil.dismiss();
            })
            builder.setNegativeButton("No", {dil,id->
                dil.dismiss()
            })

            builder.show()
        }

    }
}
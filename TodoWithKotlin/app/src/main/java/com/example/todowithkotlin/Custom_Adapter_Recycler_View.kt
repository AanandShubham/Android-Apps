package com.example.todowithkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class Custom_Adapter_Recycler_View (private val context: Context, private val todoList: ArrayList<Todo>): RecyclerView.Adapter<Custom_Adapter_Recycler_View.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view : View = LayoutInflater.from(context).inflate(R.layout.todo_view,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {  return todoList.size     }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        
        holder.edt_todo_view.setText(todoList[position].todo)

        if(todoList[position].flag == 1) {
            holder.chkBoxTodoView.isChecked = true;
            holder.edt_todo_view.isEnabled = false
            holder.img_btn_todo_view.isEnabled = false
        }



        holder.img_btn_todo_view.setOnClickListener {

            val flag  = holder.edt_todo_view.isEnabled

            if(!flag)
                holder.img_btn_todo_view.setImageResource(R.drawable.edit__btn_save)
            else
                holder.img_btn_todo_view.setImageResource(R.drawable.edit_btn_img)

            holder.edt_todo_view.isEnabled = !(holder.edt_todo_view.isEnabled)
            holder.edt_todo_view.requestFocus()

            val db : Db_Class = Db_Class(context)
            val todo = Todo(todoList[position].id,holder.edt_todo_view.text.toString(),1)
            db.editTodo(todo)
            todoList[position].todo = todo.todo;
            notifyItemChanged(position)
            db.close()

        }
                                    //------------------------------------------------- Delete btn ------------------------

        holder.delete_btn_todo_view.setOnClickListener {
            val db : Db_Class = Db_Class(context)
            val id = todoList[position].id
            db.deleteTodo(id)
            todoList.removeAt(position)
            notifyItemRemoved(position)
            db.close()
        }
                                             // onclick on  check box

        holder.chkBoxTodoView.setOnClickListener {
            val db = Db_Class(context)
            val todo = todoList[position]
            if(holder.chkBoxTodoView.isChecked)
                todo.flag = 1
            else
                todo.flag = 0

            db.editTodo(todo)

            todoList[position] = todo
            notifyItemChanged(position)

            db.close()
        }
    }

    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val chkBoxTodoView : CheckBox = itemView.findViewById(R.id.chk_box_todo_view)
        val edt_todo_view: TextInputEditText = itemView.findViewById<TextInputEditText>(R.id.edt_todo_view)
        val img_btn_todo_view = itemView.findViewById<ImageButton>(R.id.img_btn_todo_view)
        val delete_btn_todo_view = itemView.findViewById<AppCompatButton>(R.id.delete_btn_todo_view)
    }
}

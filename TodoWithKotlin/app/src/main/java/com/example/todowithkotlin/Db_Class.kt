package com.example.todowithkotlin
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Db_Class(private val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object{
        private const val DB_NAME : String ="todo.db"
        private const val DB_VERSION : Int = 1
        private const val TABLE : String = "TODOS"
        private const val COL_ID : String = "ID"
        private const val COL_NAME : String = "MESSAGE"
        private const val COL_FLAG : String = "CHECKED"
    }

    override fun onCreate(sqLite : SQLiteDatabase) {

        sqLite.execSQL("CREATE TABLE $TABLE ( $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT ,$COL_NAME TEXT ,$COL_FLAG INTEGER )")
        Log.d("SQLITE_MESSAGE","onCreate of sqlite has run");

    }

    override fun onUpgrade(sqLite : SQLiteDatabase, p1: Int, p2: Int) {

        sqLite.execSQL("DROP TABLE IF EXISTS $TABLE")
        Log.d("SQLITE_MESSAGE","onUpgrade of sqlite has run")

        this.onCreate(sqLite)
    }

    fun addTodo(todo:Todo):Long{
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME,todo.todo)
        contentValues.put(COL_FLAG,todo.flag)
        val count = db.insert(TABLE ,null,contentValues)
        db.close()

        return count
    }

    fun updateFlag(flag :Int,id: Int){
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_FLAG,flag)
        db.update(TABLE,contentValues, "$COL_ID = ?", arrayOf(""+id))
        db.close()

    }

    fun editTodo(todo: Todo){
        val db = writableDatabase
        val value = ContentValues()
        value.put(COL_NAME,todo.todo)
        value.put(COL_FLAG,todo.flag)
        db.update(TABLE,value, "$COL_ID = ? ", arrayOf(""+todo.id))
        db.close()
    }

    fun clearAll(){
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE")
        db.close()
    }
    fun deleteTodo(id:Int){
        val db = writableDatabase
        db.delete(TABLE, "$COL_ID = ?", arrayOf(""+id))
        db.close()
    }

    @SuppressLint("Recycle")
    fun showTodos():ArrayList<Todo>{
        val db = readableDatabase
        Log.d("SQLITE_MESSAGE","sqlite created  successfully")
        val cursor = db.rawQuery("SELECT * FROM $TABLE",null)
        Log.d("SQLITE_MESSAGE","data read from sqlite  successfully")

        val todoList : ArrayList<Todo> = ArrayList()

        while (cursor.moveToNext())
            todoList.add(Todo(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)))
        cursor.close()
        db.close()

        return todoList
    }
}
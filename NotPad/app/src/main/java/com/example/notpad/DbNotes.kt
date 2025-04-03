package com.example.notpad

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DbNotes(val context: Context): SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {
    companion object{
        private final val DB_NAME = "Notes"
        private final val TABLE_NAME = "notes"
        private final val DB_VERSION = 1
        private final val COL_ID = "id"
        private final val COL_TITLE = "title"
        private final val COL_NOTES = "notes"
        private final val COL_COLOR = "color_code"
        private final val COL_HIDDEN = "hidden"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME ( $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "$COL_TITLE VARCHAR(30) NOT NULL," +"$COL_NOTES VARCHAR(200) NOT NULL , $COL_COLOR VARCHAR(8), $COL_HIDDEN INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
       db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
       onCreate(db);

    }

    @SuppressLint("Recycle")
    fun getHiddenNotes():ArrayList<Note>{
        val noteList = ArrayList<Note>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME where $COL_HIDDEN = 1",null)

        setNoteList(noteList,cursor)

        return noteList;
    }

    @SuppressLint("Recycle")
    fun getNotes():ArrayList<Note>{
        val noteList = ArrayList<Note>()
        val db = readableDatabase

        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME where $COL_HIDDEN = 0",null)

        setNoteList(noteList,cursor)

        return noteList
    }

    fun addNote(note: Note){
        val db = writableDatabase
        val contentValues = setContentForAddUpdate(note)

        db.insert(TABLE_NAME,null,contentValues)
        db.close()
    }
    fun updateNote(note:Note){
        val db = writableDatabase
        val contentValues = setContentForAddUpdate(note)

        db.update(TABLE_NAME,contentValues,"$COL_ID = ?", arrayOf(note.id.toString()))
        db.close()
    }
    fun deleteNote(id:String){
        val db = writableDatabase
        db.delete(TABLE_NAME,"$COL_ID = ?", arrayOf(id))
    }

    fun deleteAllNotes(){
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
    }

    private fun setContentForAddUpdate(note:Note):ContentValues{
        val contentValues = ContentValues()
        contentValues.put(COL_TITLE,note.title)
        contentValues.put(COL_NOTES,note.note)
        contentValues.put(COL_COLOR,note.color)
        contentValues.put(COL_HIDDEN,note.hidden)
        return contentValues
    }

    private fun setNoteList(noteList:ArrayList<Note>, cursor: Cursor){
        while (cursor.moveToNext())
            noteList.add(Note(
                cursor.getInt(0).toString(),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4))
            )
    }
}


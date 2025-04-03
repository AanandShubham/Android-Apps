package com.example.notpad

import java.io.Serializable

data class Note(val id:String, val title:String, val note:String, val color:String, val hidden: Int):Serializable{
}
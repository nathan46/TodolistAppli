package com.example.nathan.todolist


data class Line(val id:Int, val text:String, var coche: String ){
    fun getCoche(): Boolean = coche=="true"
    fun setCoche(a:Boolean) = if(a) coche="true" else coche = "false"
}
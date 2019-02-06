package com.example.nathan.todolist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class HistoryActivity : AppCompatActivity() {

    var deleteMode: Boolean = true
    var delete: Boolean = false
    var id:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        RequestHTTP(null, this).execute("GET")

    }



    fun affichage(liste: ArrayList<Line>) {
        val affichage = listeAffiche(this, liste, true)
        line_liste.adapter = affichage

    }
}
package com.example.nathan.todolist

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatTextView
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.CompoundButton
import java.util.ArrayList


class listeAffiche(val context: Context, val liste: ArrayList<Line>, val histo: Boolean) : BaseAdapter() {

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false)

        val lineText: AppCompatTextView = view.findViewById(R.id.line_text)
        val line = getItem(pos) as Line
        val box: CheckBox = view.findViewById<CheckBox>(R.id.box)


        if (!histo) {
            val main = context as MainActivity
            box.isChecked = line.getCoche()

            if (line.getCoche()) {
                lineText.setTextColor(Color.GRAY)
                lineText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.toFloat())
            }

            box.setOnClickListener {
                main.textLine = line.text
                main.isCoche = box.isChecked
                main.id = line.id
                RequestHTTP(main, null).execute("PUT")

                if (box.isChecked) {
                    lineText.setTextColor(Color.GRAY)
                    lineText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.toFloat())

                } else {
                    lineText.setTextColor(Color.BLACK)
                    lineText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26.toFloat())
                }
            }
        } else {
            box.visibility = View.INVISIBLE

        }

        lineText.text = liste[pos].text

        return view

    }

    override fun getItem(pos: Int): Any {
        return liste[pos]
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getCount(): Int {
        return liste.size
    }

}
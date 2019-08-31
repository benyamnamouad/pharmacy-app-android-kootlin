package com.example.pharmacy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class PharmaciesListAdapter (private val context: Context,
                             private val dataSource: ArrayList<Pharmacy>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    //1
    override fun getCount(): Int {
        return dataSource.size
    }

    //2
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.pharmacy_list_layout, parent, false)

        val nomTextView = rowView.findViewById(R.id.nom) as TextView

// Get subtitle element
        val telTextView = rowView.findViewById(R.id.tel) as TextView

// Get thumbnail element


        val pharm = getItem(position) as Pharmacy

        nomTextView.text=pharm.nom
        telTextView.text=" + "+pharm.telephone.toString()



        return rowView
    }
}
package com.example.androidtraining_permission

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class ContactAdapter(val context: Context, val contactList: List<Contact>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, container: ViewGroup?): View {
        val rowView = LayoutInflater.from(context).inflate(R.layout.contact_row, container, false)

        return rowView
    }

    override fun getItem(position: Int): Contact {
        return contactList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getCount(): Int {
        return contactList.size
    }

}
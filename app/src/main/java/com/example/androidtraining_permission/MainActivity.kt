package com.example.androidtraining_permission

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val dbHelper = DBHelper(this)
    val contactList = ArrayList<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lw_main_contact_list.adapter = ContactAdapter(this, contactList)
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}

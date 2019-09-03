package com.example.androidtraining_permission

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DBHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "ContactReader.db"
    }

    private val SQL_CREATE_ENTRIES = """
        CREATE TABLE ${ContactReader.ContactEntry.TABLE_NAME} (
        ${BaseColumns._ID} INTEGER PRIMARY KEY,
        ${ContactReader.ContactEntry.COLUMN_NAME_CONTACT_NAME} TEXT,
        ${ContactReader.ContactEntry.COLUMN_NAME_CONTACT_PHONE_NUMBER} TEXT)
    """.trimIndent()

    private val SQL_DELETE_ENTRIES = """
        DROP TABLE IF EXISTS ${ContactReader.ContactEntry.TABLE_NAME}
    """.trimIndent()

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insert(contactName: String,
               contactPhoneNumber: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ContactReader.ContactEntry.COLUMN_NAME_CONTACT_NAME, contactName)
            put(ContactReader.ContactEntry.COLUMN_NAME_CONTACT_PHONE_NUMBER, contactPhoneNumber)
        }
        return db.insert(ContactReader.ContactEntry.TABLE_NAME, null, values)
    }

    fun read() : MutableList<Contact>{
        val db = this.readableDatabase
        val projection = arrayOf(BaseColumns._ID,
                                ContactReader.ContactEntry.COLUMN_NAME_CONTACT_NAME,
                                ContactReader.ContactEntry.COLUMN_NAME_CONTACT_PHONE_NUMBER)
        val selection = null
        val selectionArgs = null
        val sortOrder = "${ContactReader.ContactEntry.COLUMN_NAME_CONTACT_NAME} DESC"
        val cursor = db.query(
            ContactReader.ContactEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
        val contacts = mutableListOf<Contact>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val contactName = getString(getColumnIndexOrThrow(ContactReader.ContactEntry.COLUMN_NAME_CONTACT_NAME))
                val contactPhoneNumber = getString(getColumnIndexOrThrow(ContactReader.ContactEntry.COLUMN_NAME_CONTACT_PHONE_NUMBER))
                contacts.add(Contact(id, contactName, contactPhoneNumber))
            }
        }
        return contacts
    }

    fun delete(selection: String, selectionArgs: Array<String>) : Long {
        return this.delete(selection, selectionArgs)
    }

    fun update(contact: Contact,
               selection: String,
               selectionArgs: Array<String>): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ContactReader.ContactEntry.COLUMN_NAME_CONTACT_NAME, contact.name)
            put(ContactReader.ContactEntry.COLUMN_NAME_CONTACT_PHONE_NUMBER, contact.phoneNumber)
        }
        return db.update(
            ContactReader.ContactEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )
    }
}
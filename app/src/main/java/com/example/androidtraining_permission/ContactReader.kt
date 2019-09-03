package com.example.androidtraining_permission

import android.provider.BaseColumns

object ContactReader {
    object ContactEntry: BaseColumns {
        const val TABLE_NAME = "contact"
        const val COLUMN_NAME_CONTACT_NAME = "contact_name"
        const val COLUMN_NAME_CONTACT_PHONE_NUMBER = "contact_phone_number"
    }
}
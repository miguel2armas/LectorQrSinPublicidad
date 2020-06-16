package com.miguel.armas.developer.lectorqrsinpublicidad

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLiteOpenHelper(context: Context?, name: String?, factory: CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(BaseDeDatos: SQLiteDatabase) {
        BaseDeDatos.execSQL("create table history(id_history text primary key, save_string txt, date INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists history")
        db.execSQL("create table history(id_history text primary key, save_string txt, date INTEGER)")
    }
}
package com.example.produtos.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.produtos.db.ConstantDb.PRODUCTS_DB_NAME
import com.example.produtos.db.ConstantDb.PRODUCTS_DB_TABLE
import org.jetbrains.anko.db.*

class BancoDadosHelper(context: Context) :ManagedSQLiteOpenHelper(ctx = context, name = PRODUCTS_DB_NAME,  version = 1) {
    private val scriptSQLCreate = arrayOf(
        "INSERT INTO $PRODUCTS_DB_TABLE VALUES(1,'Skol lata',800200300);",
        "INSERT INTO $PRODUCTS_DB_TABLE VALUES(2,'Coca 2l',800200300);"
    )

    //singleton da classe
    companion object {
        private var instance: BancoDadosHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): BancoDadosHelper {
            if (instance == null) instance = BancoDadosHelper(ctx.getApplicationContext())
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(PRODUCTS_DB_TABLE, true,
            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
            "name" to TEXT,
            "expiration" to TEXT
        )

        scriptSQLCreate.forEach {sql -> db.execSQL(sql)}
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(PRODUCTS_DB_TABLE, true)
        onCreate(db)
    }
}

val Context.database: BancoDadosHelper
get() = BancoDadosHelper.getInstance(getApplicationContext())

package com.example.produtos.db

import android.content.Context
import com.example.produtos.db.ConstantDb.PRODUCTS_DB_TABLE
import org.jetbrains.anko.db.*
import timber.log.Timber

class ProductRepository (val context: Context) {

    fun findAll() : ArrayList<Product> = context.database.use {
        val products = ArrayList<Product>()

        select(PRODUCTS_DB_TABLE, "id", "name", "expiration")
            .parseList(object: MapRowParser<List<Product>> {
                override fun parseRow(columns: Map<String, Any?>): List<Product> {
                    val id = columns.getValue("id")
                    val name = columns.getValue("name")
                    val expiration = columns.getValue("expiration")

                    val contato = Product(
                        id.toString()?.toLong(),
                        name?.toString(),
                        expiration?.toString()
                    )
                    products.add(contato)
                    return products
                }
            })

        products
    }



    fun create(product: Product) = context.database.use {
        insert(
            ConstantDb.PRODUCTS_DB_TABLE,
            "name" to product.name,
            "expiration" to product.expiration)
    }


    fun update(product: Product) = context.database.use {
        val updateResult = update(
            PRODUCTS_DB_TABLE,
            "name" to product.name)
            .whereArgs("id = {id}","id" to product.id).exec()

        Timber.d("Update result code is $updateResult")
    }


    fun delete(id: Long) = context.database.use {
        delete(PRODUCTS_DB_TABLE, "id = {productId}", *kotlin.arrayOf("productId" to id))
    }


}
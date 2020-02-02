package com.example.produtos.db

import java.io.Serializable

class Product (
    var id: Long = 0,
    var name: String? = null,
    var expiration: String? = null ) : Serializable {

    override fun toString(): String {
        return name.toString()
    }

}
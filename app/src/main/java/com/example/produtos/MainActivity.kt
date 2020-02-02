package com.example.produtos

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.produtos.db.Product
import com.example.produtos.db.ProductRepository
import kotlinx.android.synthetic.main.activity_list_products.*

class MainActivity : AppCompatActivity() {
    private var products:ArrayList<Product>? = null
    private var productsSelecionado: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_products)

        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.listar -> {
                Toast.makeText(this, "Listagem!", Toast.LENGTH_LONG).show()
                return false
            }

            R.id.adicionar -> {
                val intent = Intent(this, FormActivity::class.java)
                startActivity(intent)
                return false
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        val products = ProductRepository(this).findAll()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, products)
        listViewProducts?.adapter = adapter
        adapter.notifyDataSetChanged()

        listViewProducts.setOnItemClickListener { _, _, position, id ->
            val intent = Intent(this, FormActivity::class.java)
            intent.putExtra("product", products?.get(position))
            startActivity(intent)
        }

    }


}
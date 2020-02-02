package com.example.produtos

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.produtos.db.Product
import com.example.produtos.db.ProductRepository
import kotlinx.android.synthetic.main.activity_form_product.*
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : AppCompatActivity() {
    var cal = Calendar.getInstance()
    var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_product)

        setSupportActionBar(toolbar_child)

        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        if (intent?.getSerializableExtra("produto") != null) {
            product = intent?.getSerializableExtra("produto") as Product
            nameField?.setText(product?.name)
        }else{
            product = Product()
        }

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        expirationField.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@FormActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        btnCadastro?.setOnClickListener {
            product?.name = nameField?.text.toString()
            product?.expiration = expirationField?.text.toString()

            if(product?.id?.toInt() == 0){
                ProductRepository(this).create(product!!)
            }else{
                ProductRepository(this).update(product!!)
            }
            finish()
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        expirationField.text = sdf.format(cal.getTime())
    }

    override fun onResume() {
        super.onResume()
        val intent = intent
        if (intent != null) {
            if (intent.getSerializableExtra("product") != null) {
                product = intent.getSerializableExtra("product") as Product

                nameField?.setText(product?.name)
                expirationField?.setText(product?.expiration)
            } else {
                product = Product()
            }
        }

    }
}
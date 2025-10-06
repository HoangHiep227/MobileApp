package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtName = findViewById<EditText>(R.id.edtName)
        val edtAge = findViewById<EditText>(R.id.edtAge)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val btnCheck = findViewById<Button>(R.id.btnCheck)

        btnCheck.setOnClickListener {
            val name = edtName.text.toString().trim()
            val ageText = edtAge.text.toString().trim()

            if (name.isEmpty() || ageText.isEmpty()) {
                tvResult.text = "Vui lòng nhập đầy đủ họ tên và tuổi!"
                tvResult.setTextColor(getColor(android.R.color.holo_red_dark))
                return@setOnClickListener
            }

            val age = ageText.toInt()
            val result = when {
                age < 2 -> "Em bé"
                age in 2..6 -> "Trẻ em"
                age in 7..65 -> "Người lớn"
                else -> "Người già"
            }

            tvResult.text = "$name là $result"
            tvResult.setTextColor(getColor(android.R.color.holo_blue_dark))
        }
    }
}
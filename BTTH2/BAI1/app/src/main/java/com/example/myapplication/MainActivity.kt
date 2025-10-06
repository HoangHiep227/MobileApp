package com.example.myapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // dùng layout XML

        val edtNumber = findViewById<EditText>(R.id.edtNumber)
        val btnCreate = findViewById<Button>(R.id.btnCreate)
        val tvError = findViewById<TextView>(R.id.tvError)
        val listContainer = findViewById<LinearLayout>(R.id.listContainer)

        btnCreate.setOnClickListener {
            listContainer.removeAllViews()
            tvError.visibility = TextView.GONE

            val input = edtNumber.text.toString().trim()

            if (input.isEmpty()) {
                tvError.text = "Vui lòng nhập dữ liệu"
                tvError.visibility = TextView.VISIBLE
                return@setOnClickListener
            }

            try {
                val num = input.toInt()

                for (i in 1..num) {
                    val tv = TextView(this)
                    tv.text = i.toString()
                    tv.textSize = 18f
                    tv.setPadding(0, 16, 0, 16)
                    tv.setBackgroundResource(R.drawable.item_bg)
                    tv.setTextColor(resources.getColor(android.R.color.white))
                    tv.textAlignment = TextView.TEXT_ALIGNMENT_CENTER

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 8, 0, 8)
                    tv.layoutParams = params

                    listContainer.addView(tv)
                }

            } catch (e: NumberFormatException) {
                tvError.text = "Dữ liệu bạn nhập không hợp lệ"
                tvError.visibility = TextView.VISIBLE
            }
        }
    }
}

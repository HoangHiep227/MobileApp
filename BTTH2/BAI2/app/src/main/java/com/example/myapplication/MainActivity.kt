package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var tvThongBao: TextView
    private lateinit var btnKiemTra: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtEmail = findViewById(R.id.edtEmail)
        tvThongBao = findViewById(R.id.tvThongBao)
        btnKiemTra = findViewById(R.id.btnKiemTra)

        btnKiemTra.setOnClickListener {
            val email = edtEmail.text.toString().trim()

            when {
                email.isEmpty() -> tvThongBao.text = "Email không hợp lệ"
                !email.contains("@") -> tvThongBao.text = "Email không đúng định dạng"
                else -> {
                    tvThongBao.setTextColor(getColor(android.R.color.holo_green_dark))
                    tvThongBao.text = "Bạn đã nhập email hợp lệ"
                }
            }
        }
    }
}
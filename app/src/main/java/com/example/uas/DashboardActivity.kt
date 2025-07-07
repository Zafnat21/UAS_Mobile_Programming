package com.example.uas

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.util.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val moodBtn = findViewById<CardView>(R.id.moodTrackerCard)
        val flushBtn = findViewById<CardView>(R.id.flushPikiranCard)
        val riwayatBtn = findViewById<CardView>(R.id.riwayatCard)

        // Ambil nama user dari intent
        val username = intent.getStringExtra("username") ?: "User"

        // Tentukan waktu sekarang
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greeting = when (hour) {
            in 4..10 -> "Selamat Pagi"
            in 11..14 -> "Selamat Siang"
            in 15..17 -> "Selamat Sore"
            else -> "Selamat Malam"
        }

        // Tampilkan salam di TextView
        val greetingText = findViewById<TextView>(R.id.greetingText)
        greetingText.text = "$greeting, $username"

        moodBtn.setOnClickListener {
            val intent = Intent(this, MoodActivity::class.java)
            startActivity(intent)
        }

        flushBtn.setOnClickListener {
            val intent = Intent(this, flush_pikiran::class.java)
            startActivity(intent)
        }

        riwayatBtn.setOnClickListener {
            val intent = Intent(this, RiwayatMood::class.java)
            startActivity(intent)
        }
    }
}

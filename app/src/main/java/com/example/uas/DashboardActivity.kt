package com.example.uas

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.util.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Setup variabel tombol
        val moodBtn = findViewById<CardView>(R.id.moodTrackerCard)
        val flushBtn = findViewById<CardView>(R.id.flushPikiranCard)
        val riwayatBtn = findViewById<CardView>(R.id.riwayatCard)
        val meditationBtn = findViewById<CardView>(R.id.meditationCard)
        val btnNextQuotes = findViewById<ImageView>(R.id.BtnNextQuotes)

        // Dapatkan array quotes
        val quotes = resources.getStringArray(R.array.motivation_quotes)

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

        // Tampilkan quote awal
        val quoteTextView = findViewById<TextView>(R.id.TvQuotes)
        if (quotes.isNotEmpty()) {
            val initialQuote = quotes[(0 until quotes.size).random()]
            quoteTextView.text = initialQuote
        }

        // Tambahkan click listener untuk tombol-tombol
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

        meditationBtn.setOnClickListener {
            val intent = Intent(this, MeditationActivity::class.java)
            startActivity(intent)
        }

        btnNextQuotes.setOnClickListener {
            val randomIndex = (0 until quotes.size).random()
            val randomQuote = quotes[randomIndex]
            val quoteTextView = findViewById<TextView>(R.id.TvQuotes)
            quoteTextView.text = randomQuote
        }
    }
}
package com.example.uas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        val moodBtn = findViewById<CardView>(R.id.moodTrackerCard)
        val flushBtn = findViewById<CardView>(R.id.flushPikiranCard)
        val riwayatBtn = findViewById<CardView>(R.id.riwayatCard)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dashboard)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        moodBtn.setOnClickListener {
            // Intent pindah ke halaman mood
            val intent = Intent(this, MoodActivity::class.java)
            startActivity(intent)
        }

        flushBtn.setOnClickListener {
            // Intent pindah ke halaman flush pikiran
            val intent = Intent(this, flush_pikiran::class.java)
            startActivity(intent)
        }

        riwayatBtn.setOnClickListener {
            // Intent pindah ke halaman mood
            val intent = Intent(this, RiwayatMood::class.java)
            startActivity(intent)
        }
    }
}
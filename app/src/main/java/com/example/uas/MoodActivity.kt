package com.example.uas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MoodActivity : AppCompatActivity() {

    private lateinit var btnNext: Button
    private var moodDipilih: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood)

        btnNext = findViewById(R.id.btnNext)

        // Setup listener untuk setiap card
        setupCardClick(R.id.cardSenang, "Senang")
        setupCardClick(R.id.cardBiasa, "Biasa")
        setupCardClick(R.id.cardMarah, "Marah")
        setupCardClick(R.id.cardGembira, "Gembira")
        setupCardClick(R.id.cardBahagia, "Bahagia")
        setupCardClick(R.id.cardSedih, "Sedih")

        btnNext.setOnClickListener {
            // Optional: tampilkan toast mood
            Toast.makeText(this, "Mood kamu: $moodDipilih", Toast.LENGTH_SHORT).show()

            // Intent ke flush_pikiran untuk testing
            val intent = Intent(this, flush_pikiran::class.java)
            startActivity(intent)
        }
    }

    private fun setupCardClick(cardId: Int, mood: String) {
        val card = findViewById<LinearLayout>(cardId)
        card.setOnClickListener {
            moodDipilih = mood
            btnNext.visibility = View.VISIBLE
            highlightSelectedCard(cardId)
        }
    }

    private fun highlightSelectedCard(selectedCardId: Int) {
        val cards = listOf(
            R.id.cardSenang, R.id.cardBiasa, R.id.cardMarah,
            R.id.cardGembira, R.id.cardBahagia, R.id.cardSedih
        )
        for (id in cards) {
            val card = findViewById<LinearLayout>(id)
            card.alpha = if (id == selectedCardId) 1.0f else 0.5f
        }
    }
}

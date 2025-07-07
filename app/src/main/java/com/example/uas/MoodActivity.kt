package com.example.uas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.uas.entity.MoodEntity
import kotlinx.coroutines.launch

class MoodActivity : AppCompatActivity() {

    private lateinit var btnNext: Button
    private var moodDipilih: String? = null

    private lateinit var moodRepository: com.example.uas.repository.MoodRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood)

        btnNext = findViewById(R.id.btnNext)

        val app = application as NoteApplication
        moodRepository = app.moodRepository

        setupCardClick(R.id.cardSenang, "Senang")
        setupCardClick(R.id.cardBiasa, "Biasa")
        setupCardClick(R.id.cardMarah, "Marah")
        setupCardClick(R.id.cardGembira, "Gembira")
        setupCardClick(R.id.cardBahagia, "Bahagia")
        setupCardClick(R.id.cardSedih, "Sedih")

        btnNext.setOnClickListener {
            if (moodDipilih != null) {
                val today = getTodayDate()

                lifecycleScope.launch {
                    val existingMood = moodRepository.getMoodByDate(today)

                    if (existingMood != null) {
                        runOnUiThread {
                            Toast.makeText(this@MoodActivity, "Mood hari ini sudah dipilih. Hapus dulu jika ingin ganti.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val (iconRes, colorRes) = getMoodResources(moodDipilih!!)

                        val moodEntity = MoodEntity(
                            mood = moodDipilih!!,
                            date = today,
                            iconResId = iconRes,
                            colorResId = colorRes
                        )

                        moodRepository.insert(moodEntity)

                        runOnUiThread {
                            Toast.makeText(this@MoodActivity, "Mood kamu: $moodDipilih berhasil disimpan", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@MoodActivity, DashboardActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Pilih mood dulu", Toast.LENGTH_SHORT).show()
            }
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

    private fun getTodayDate(): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        return sdf.format(java.util.Date())
    }

    private fun getMoodResources(mood: String): Pair<Int, Int> {
        return when (mood) {
            "Senang" -> Pair(R.drawable.ic_smile, R.color.senang_bg)
            "Biasa" -> Pair(R.drawable.ic_neutral, R.color.biasa_bg)
            "Marah" -> Pair(R.drawable.ic_angry, R.color.marah_bg)
            "Gembira" -> Pair(R.drawable.ic_laugh, R.color.gembira_bg)
            "Bahagia" -> Pair(R.drawable.ic_heart, R.color.bahagia_bg)
            "Sedih" -> Pair(R.drawable.ic_sad, R.color.sedih_bg)
            else -> Pair(R.drawable.ic_neutral, R.color.biasa_bg)
        }
    }
}

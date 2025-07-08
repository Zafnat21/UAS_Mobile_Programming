package com.example.uas

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MeditationActivity : AppCompatActivity() {

    private val meditationItems = mutableListOf<MeditationItem>()
    private lateinit var meditationAdapter: MeditationAdapter
    private lateinit var recyclerView: RecyclerView

    // MediaPlayer untuk memutar audio
    private var mediaPlayer: MediaPlayer? = null
    private var currentPlayingPosition: Int = -1
    private var isPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mengatur fullscreen dengan status bar transparan
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                )

        // Mengatur status bar menjadi transparan
        window.statusBarColor = android.graphics.Color.TRANSPARENT

        // Mengatur agar layout menggunakan seluruh layar
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(R.layout.activity_meditation)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        // Inisialisasi adapter dengan callback untuk handle klik tombol play
        meditationAdapter = MeditationAdapter(this, meditationItems) { position ->
            handlePlayButtonClick(position)
        }
        recyclerView.adapter = meditationAdapter

        loadMeditationData()
    }

    private fun handlePlayButtonClick(position: Int) {
        try {
            if (currentPlayingPosition == position && isPlaying) {
                // Pause audio jika sedang diputar di posisi yang sama
                pauseAudio()
            } else {
                // Stop audio sebelumnya jika ada
                stopAudio()
                // Mulai audio baru
                startAudio(position)
            }
        } catch (e: Exception) {
            Log.e("MeditationActivity", "Error handling play button click", e)
        }
    }

    private fun startAudio(position: Int) {
        try {
            // Ambil audio resource dari item yang diklik
            val audioResource = meditationItems[position].audioResource

            mediaPlayer = MediaPlayer.create(this, audioResource)
            mediaPlayer?.let { player ->
                player.setOnCompletionListener {
                    // Audio selesai diputar
                    stopAudio()
                }

                player.setOnErrorListener { _, what, extra ->
                    Log.e("MeditationActivity", "MediaPlayer error: what=$what, extra=$extra")
                    stopAudio()
                    true
                }

                player.start()
                currentPlayingPosition = position
                isPlaying = true

                // Update UI adapter
                meditationAdapter.updatePlayingState(position, true)
            }
        } catch (e: Exception) {
            Log.e("MeditationActivity", "Error starting audio", e)
        }
    }

    private fun pauseAudio() {
        try {
            mediaPlayer?.let { player ->
                if (player.isPlaying) {
                    player.pause()
                    isPlaying = false

                    // Update UI adapter
                    meditationAdapter.updatePlayingState(currentPlayingPosition, false)
                }
            }
        } catch (e: Exception) {
            Log.e("MeditationActivity", "Error pausing audio", e)
        }
    }

    private fun stopAudio() {
        try {
            mediaPlayer?.let { player ->
                if (player.isPlaying) {
                    player.stop()
                }
                player.release()
            }
            mediaPlayer = null

            val previousPosition = currentPlayingPosition
            currentPlayingPosition = -1
            isPlaying = false

            // Update UI adapter
            if (previousPosition != -1) {
                meditationAdapter.updatePlayingState(previousPosition, false)
            }
        } catch (e: Exception) {
            Log.e("MeditationActivity", "Error stopping audio", e)
        }
    }

    private fun loadMeditationData() {
        meditationItems.clear()

        meditationItems.addAll(
            listOf(
                MeditationItem(
                    title = "Ketenangan Harian",
                    subtitle = "MULAI LATIHAN",
                    type = "daily",
                    backgroundImage = R.drawable.meditation_daily,
                    isFullWidth = true,
                    audioResource = R.raw.meditate_daily // Audio khusus untuk ketenangan harian
                ),
                MeditationItem(
                    title = "Minggu Tenang",
                    subtitle = "",
                    type = "calm",
                    backgroundImage = R.drawable.meditation_calm,
                    audioResource = R.raw.meditate_calm // Audio khusus untuk minggu tenang
                ),
                MeditationItem(
                    title = "Lepaskan Cemas",
                    subtitle = "",
                    type = "stress",
                    backgroundImage = R.drawable.meditation_stress,
                    audioResource = R.raw.meditate_stress // Audio khusus untuk lepaskan cemas
                ),
                MeditationItem(
                    title = "Suara Alam",
                    subtitle = "",
                    type = "nature1",
                    backgroundImage = R.drawable.meditation_nature1,
                    audioResource = R.raw.meditate_nature1 // Audio khusus untuk suara alam
                ),
                MeditationItem(
                    title = "Hutan Hijau",
                    subtitle = "",
                    type = "nature2",
                    backgroundImage = R.drawable.meditation_nature2,
                    audioResource = R.raw.meditate_nature2 // Audio khusus untuk hutan hijau
                )
            )
        )

        meditationAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Pastikan untuk melepaskan MediaPlayer saat Activity dihancurkan
        stopAudio()
    }

    override fun onPause() {
        super.onPause()
        // Pause audio saat Activity tidak terlihat
        if (isPlaying) {
            pauseAudio()
        }
    }
}
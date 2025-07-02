package com.example.uas

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uas.adapter.RiwayatMoodAdapter
import com.example.uas.databinding.ActivityRiwayatMoodBinding
import com.example.uas.viewmodel.MoodViewModel
import com.example.uas.viewmodel.MoodViewModelFactory
import kotlinx.coroutines.launch

class RiwayatMood : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatMoodBinding
    private lateinit var adapter: RiwayatMoodAdapter

    private val moodViewModel: MoodViewModel by viewModels {
        MoodViewModelFactory((application as NoteApplication).moodRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatMoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RiwayatMoodAdapter(emptyList()) { mood ->
            // Hapus mood saat tombol hapus ditekan
            moodViewModel.delete(mood)
        }

        binding.recyclerViewRiwayat.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRiwayat.adapter = adapter

        moodViewModel.allMoods.observe(this) { moods ->
            adapter.setMoods(moods)
        }
    }
}

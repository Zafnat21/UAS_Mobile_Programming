package com.example.uas

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.uas.NoteApplication
import com.example.uas.data.NoteAdapter
import com.example.uas.databinding.ActivityFlushPikiranBinding
import com.example.uas.viewmodel.NoteViewModel
import com.example.uas.viewmodel.NoteViewModelFactory

class flush_pikiran : AppCompatActivity() {

    private lateinit var binding: ActivityFlushPikiranBinding
    private lateinit var adapter: NoteAdapter

    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NoteApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlushPikiranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView as Grid
        adapter = NoteAdapter(emptyList()) { note ->
            // 🔥 Saat card diklik → buka flush_pikiran_detail dengan note id
            val intent = Intent(this, flush_pikiran_detail::class.java)
            intent.putExtra("note_id", note.id)
            startActivity(intent)
        }

        binding.recyclerViewNotes.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewNotes.adapter = adapter

        // Observe LiveData (otomatis update)
        noteViewModel.notes.observe(this) { notes ->
            adapter.setNotes(notes)
        }

        // FloatingActionButton to add note
        binding.fabAddNote.setOnClickListener {
            startActivity(Intent(this, flush_pikiran_detail::class.java))
        }
    }
}

package com.example.uas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.uas.data.NoteEntity
import com.example.uas.databinding.ActivityFlushPikiranDetailBinding
import com.example.uas.viewmodel.NoteViewModel
import com.example.uas.viewmodel.NoteViewModelFactory

class flush_pikiran_detail : AppCompatActivity() {

    private lateinit var binding: ActivityFlushPikiranDetailBinding
    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NoteApplication).repository)
    }

    private var noteId: Int? = null
    private var currentNote: NoteEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlushPikiranDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil note_id jika ada (edit)
        noteId = intent.getIntExtra("note_id", -1)
        if (noteId != -1) {
            noteViewModel.getNoteById(noteId!!).observe(this) { note ->
                if (note != null) {
                    currentNote = note
                    binding.editTextContent.setText(note.content)
                }
            }
        }

        // Tombol Save
        binding.buttonSave.setOnClickListener {
            val content = binding.editTextContent.text.toString()
            if (content.isNotEmpty()) {
                if (currentNote != null) {
                    // Update note
                    val updatedNote = currentNote!!.copy(content = content)
                    noteViewModel.updateNote(updatedNote)
                    Toast.makeText(this, "Catatan diperbarui", Toast.LENGTH_SHORT).show()
                } else {
                    // Insert new note
                    val note = NoteEntity(content = content)
                    noteViewModel.insertNote(note)
                    Toast.makeText(this, "Catatan disimpan", Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }

        // Tombol Delete (jika edit)
        binding.btnDelete.setOnClickListener {
            if (currentNote != null) {
                noteViewModel.deleteNote(currentNote!!)
                Toast.makeText(this, "Catatan dihapus", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}

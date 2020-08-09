package com.geekbrains.shoplist.ui.note

import androidx.lifecycle.ViewModel
import com.geekbrains.shoplist.data.entity.Note
import com.geekbrains.shoplist.data.model.Repository

class NoteViewModel : ViewModel() {
    lateinit var note: Note

    fun addOrReplace(note: Note) {
        this.note = note
        Repository.saveNote(note)
    }
    fun deleteNote(note: Note) {
        this.note = note
        Repository.deleteNote(note)
    }
}
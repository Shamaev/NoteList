package com.geekbrains.shoplist.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekbrains.shoplist.data.entity.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private const val USERS_COLLECTION = "users"
private const val NOTES_COLLECTION = "notes"

object Repository  {
    val database: DatabaseReference = Firebase.database.reference
    private val db = FirebaseFirestore.getInstance()

    var emailUser : String = ""
        set(value) {
            field = value
        }

    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser


    private fun getUserNotes() =
        db.collection(
            USERS_COLLECTION
        ).document(emailUser).collection(
            NOTES_COLLECTION
        )

    fun saveNote(note: Note): LiveData<Note> =
        MutableLiveData<Note>().apply {
            try {
                getUserNotes()?.document(note.id)
                    ?.set(note)?.addOnSuccessListener {
                        value = note
                    }?.addOnFailureListener {
                        throw it
                    }
            } catch (e: Throwable) {

            }
        }

    fun getNotesLiveData(): LiveData<List<Note>> {
        return readNotes()
    }

    fun deleteNote(note: Note) {
        database.child("notes").child(note.id).removeValue()
    }

    private fun readNotes() : LiveData<List<Note>> {
        return MutableLiveData<List<Note>>().apply {
            getUserNotes()
                ?.addSnapshotListener { snapshot, error ->
                val notes = snapshot?.documents?.map { it.toObject(Note::class.java)!! }
                value = notes
            }
        }
    }
}
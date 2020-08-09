package com.geekbrains.shoplist.ui.note

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.geekbrains.shoplist.DATE_TIME_FORMAT
import com.geekbrains.shoplist.DATE_TIME_FORMAT2
import com.geekbrains.shoplist.data.entity.Note
import com.geekbrains.shoplist.R
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_note.*
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {
    lateinit var viewModel: NoteViewModel
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setSupportActionBar(toolbar)

        note = intent.getParcelableExtra("note123")
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        note?.let {
            appBar.toolbar.title = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(
                it.lastChanged)
        }

        initView(note)
    }

    private fun initView(note: Note?) {
        editTitle.setText(note?.title ?: "")
        editText.setText(note?.text ?: "")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_add ->
            {
                note = note?.let {
                    replaceNote(it)
                } ?: createNewNote()

                note?.let {
                    viewModel.addOrReplace(it)
                }
                onBackPressed()
                return true
            }

            R.id.action_delete ->
            {
                note = note?.let {
                    replaceNote(it)
                }

                note?.let {
                    viewModel.deleteNote(it)
                }
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createNewNote() : Note =
        Note(
            id = SimpleDateFormat(DATE_TIME_FORMAT2, Locale.getDefault()).format(
                Date()
            ).toString(),
            title = editTitle.text.toString(),
            text = editText.text.toString()
        )

    private fun replaceNote(note: Note) : Note? =
        Note(
            id = note.id,
            title = editTitle.text.toString(),
            text = editText.text.toString()
        )
}

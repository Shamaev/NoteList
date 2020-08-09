package com.geekbrains.shoplist.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.geekbrains.shoplist.data.entity.Note
import com.geekbrains.shoplist.R
import com.geekbrains.shoplist.ui.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
     lateinit var adapter: MainAdapter
     lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            startNoteActivity(null)
        }

        initView()
        initAdapter()
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private fun initAdapter() {
        adapter = MainAdapter(object :
            MainAdapter.OnItemClickListener {
            override fun onItemClickL(note: Note) {
                startNoteActivity(note)
            }
        })
        viewModel.getData().observe(this, Observer {
            it?.let {
                adapter.list = it
            }
        })

        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(this, 2)
    }

    private fun startNoteActivity(note: Note?) {
        val intent = Intent(this, NoteActivity::class.java)
        intent.putExtra("note123", note)
        startActivity(intent)
    }


}
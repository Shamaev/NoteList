package com.geekbrains.shoplist.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.shoplist.data.entity.Note
import com.geekbrains.shoplist.R
import kotlinx.android.synthetic.main.item.view.*

class MainAdapter(private val onClickListener: OnItemClickListener) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClickL(note: Note)
    }

    var list : List<Note> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(note: Note) = with(itemView) {
            noteTitle.text = note.title
            noteText.text = note.text

            setOnClickListener {
                onClickListener.onItemClickL(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}
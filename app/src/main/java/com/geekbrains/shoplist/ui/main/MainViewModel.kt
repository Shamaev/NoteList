package com.geekbrains.shoplist.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.shoplist.data.entity.Note
import com.geekbrains.shoplist.data.model.Repository

class MainViewModel : ViewModel() {
    private val data: MutableLiveData<List<Note>> = MutableLiveData()

    init {
        Repository.getNotesLiveData().observeForever{
            data.value = it
        }
    }

    fun getData() : LiveData<List<Note>> {
        return data
    }
}
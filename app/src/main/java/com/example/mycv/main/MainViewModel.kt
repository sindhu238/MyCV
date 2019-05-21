package com.example.mycv.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mycv.api.ServerAPI
import com.example.mycv.models.CVInfo


class MyViewModelFactory(private val serverAPI: ServerAPI) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(serverAPI) as T
    }
}

class MainViewModel(
        private val serverAPI: ServerAPI
) : ViewModel() {

    fun getCvInfo(gistId: String): LiveData<CVInfo?> = serverAPI.getCVDetails(gistId)
}
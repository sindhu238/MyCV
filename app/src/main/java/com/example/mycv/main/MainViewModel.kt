package com.example.mycv.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mycv.api.ServerAPIImpl
import com.example.mycv.models.CVInfo


class MainViewModel : ViewModel() {

    fun getCvInfo(gistId: String): LiveData<CVInfo?> = ServerAPIImpl.getCVDetails(gistId)
}
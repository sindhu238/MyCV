package com.example.mycv.fragments.briefCV

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mycv.api.ServerAPI
import com.example.mycv.models.CVInfo
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

interface BriefCVModel {
    fun getCvInfo(gistId: String): LiveData<CVInfo?>
    val nextButtonStream: PublishSubject<Unit>
}

class BriefCVViewModel @Inject constructor(
        private val serverAPI: ServerAPI
) : BriefCVModel, ViewModel() {

    override fun getCvInfo(gistId: String): LiveData<CVInfo?> = serverAPI.getCVDetails(gistId)

    override val nextButtonStream: PublishSubject<Unit> = PublishSubject.create()
}
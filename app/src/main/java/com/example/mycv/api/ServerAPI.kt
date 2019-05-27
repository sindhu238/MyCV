package com.example.mycv.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mycv.fragments.briefCV.GIST_ID
import com.example.mycv.models.CVInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface ServerAPI {
    fun getCVDetails(): LiveData<CVInfo?>
}

object ServerAPIImpl : ServerAPI {
    override fun getCVDetails(): LiveData<CVInfo?> =
            MutableLiveData<CVInfo>().let { data ->
                CoroutineScope(Dispatchers.Default).launch {
                    RetrofitClient.retrofit.create(GistService::class.java)
                            .getCVInfoAsync(GIST_ID)
                            .await().let {
                                it.body()?.let { cvInfo ->
                                    data.postValue(cvInfo)
                                }
                            }
                }
                data
            }
}
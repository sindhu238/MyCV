package com.example.mycv.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.mycv.api.ServerAPI
import com.example.mycv.models.CVInfo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var serverAPI: ServerAPI

    @Mock
    lateinit var observer: Observer<CVInfo?>

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(serverAPI)
    }

    @Test
    fun `test if changes in getCVDetails is getting reflected in observer`() {
        // arrange
        val gistId = "sampleId"
        val cvInfo = CVInfo(
                name = "Name",
                address = "sample address",
                summary = "test summary",
                skills = listOf("sample1", "sample2"),
                experience = listOf()
        )
        val liveData = MutableLiveData<CVInfo?>()
        `when`(serverAPI.getCVDetails(gistId)).thenReturn(liveData)

        // act
        liveData.value = cvInfo
        serverAPI.getCVDetails(gistId).observeForever(observer)

        // assert
        verify(observer).onChanged(cvInfo)
    }
}
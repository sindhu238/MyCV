package com.example.mycv.fragments.briefCV

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.mycv.api.ServerAPI
import com.example.mycv.models.CVInfo
import io.reactivex.observers.TestObserver
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations




class BriefCVFragmentViewModelTest {

    lateinit var viewModelImpl: BriefCVViewModel

    @JvmField
    @Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var serverAPI: ServerAPI

    private val sampleCVInfo = CVInfo(
            name = "test Name",
            address = "Address",
            summary = "sample summary",
            skills = listOf("Skill1", "Skill2"),
            experience = listOf()
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModelImpl = BriefCVViewModel(serverAPI)
    }

    @Test
    fun `test next button clicks stream`() {
        // arrange
        val testObserver = TestObserver<Unit>()
        viewModelImpl.nextButtonStream.subscribe(testObserver)

        // act
        viewModelImpl.nextButtonStream.onNext(Unit)

        // assert
        testObserver.assertValue(Unit)
    }

    @Test
    fun `test cv info response`() {
        // arrange
        val data = MutableLiveData<CVInfo>()
        `when`(serverAPI.getCVDetails("sample id")).thenReturn(data)

        // act
        data.postValue(sampleCVInfo)

        // assert
        assertEquals(sampleCVInfo, data.value)
    }
}
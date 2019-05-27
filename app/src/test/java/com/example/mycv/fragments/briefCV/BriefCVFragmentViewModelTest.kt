package com.example.mycv.fragments.briefCV

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.mycv.api.ServerAPI
import com.example.mycv.enums.DescriptionType
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

    lateinit var viewModel: BriefCVViewModel

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
        viewModel = BriefCVViewModel(serverAPI)
    }

    @Test
    fun `test next button name stream`() {
        // arrange
        val testObserver = TestObserver<String>()
        viewModel.nextButtonTitleStream.subscribe(testObserver)

        // act
        viewModel.titleObserver.onNext(DescriptionType.Summary.name)
        viewModel.titleObserver.onNext(DescriptionType.Experience.name)
        viewModel.nextButtonObserver.onNext(Unit)

        // assert
        testObserver.assertValues("Next", "Done")
    }

    @Test
    fun `test info stream`() {
        // arrange
        val data = MutableLiveData<CVInfo>()
        `when`(serverAPI.getCVDetails()).thenReturn(data)
        data.postValue(sampleCVInfo)

        val testObserver = TestObserver<Info>()
        viewModel.infoStream.subscribe(testObserver)

        // act
        viewModel.titleObserver.onNext("")
        viewModel.titleObserver.onNext(DescriptionType.Summary.name)
        viewModel.nextButtonObserver.onNext(Unit)

        // assert
        testObserver.assertValues(
                Info(DescriptionType.Summary.name, "sample summary"),
                Info(DescriptionType.Skills.name, "Skill1\nSkill2")
        )
    }

    @Test
    fun `test cv info response`() {
        // arrange
        val data = MutableLiveData<CVInfo>()
        `when`(serverAPI.getCVDetails()).thenReturn(data)

        // act
        data.postValue(sampleCVInfo)

        // assert
        assertEquals(sampleCVInfo, data.value)
    }
}
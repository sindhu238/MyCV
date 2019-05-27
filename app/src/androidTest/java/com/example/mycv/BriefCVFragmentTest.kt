package com.example.mycv


import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.mycv.activities.main.MainActivity
import com.example.mycv.fragments.briefCV.BriefCVFragment
import com.example.mycv.fragments.briefCV.BriefCVModel
import com.example.mycv.fragments.briefCV.Info
import com.example.mycv.models.CVInfo
import com.example.mycv.models.ExperienceInfo
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class BriefCVFragmentTest {

    @Rule
    @JvmField
    var activityRule = object : ActivityTestRule<MainActivity>(MainActivity::class.java, false, false) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            val myApp = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MyApplication
            myApp.dispatchingActivityInjector = createFakeFragmentInjector<BriefCVFragment> {
                viewModel = cvViewModel
                cvInfo = sampleCVInfo
            }
        }
    }

    @Mock
    lateinit var cvViewModel: BriefCVModel

    private val sampleCVInfo = CVInfo(
            name = "test Name",
            address = "Address",
            summary = "sample summary",
            skills = listOf("Skill1", "Skill2"),
            experience = listOf()
    )

    private val nextButtonSubject = BehaviorSubject.createDefault(Unit)
    private val cvInfoLiveData = MutableLiveData<CVInfo>()
    private val infoStream = PublishSubject.create<Info>()
    private val titleObserver = BehaviorSubject.createDefault("")
    private val nextButtonTitleStream = PublishSubject.create<String>()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(cvViewModel.nextButtonObserver).thenReturn(nextButtonSubject)
        `when`(cvViewModel.cvInfo).thenReturn(cvInfoLiveData)
        `when`(cvViewModel.infoStream).thenReturn(infoStream)
        `when`(cvViewModel.titleObserver).thenReturn(titleObserver)
        `when`(cvViewModel.nextButtonTitleStream).thenReturn(nextButtonTitleStream)


        activityRule.launchActivity(null)
    }

    @After
    fun tearDown() {
        activityRule.finishActivity()
    }

    @Test
    fun testDisplayOfRecyclerView() {
        // arrange
        val recyclerView = onView(withId(R.id.experienceRecyclerView))
        recyclerView.check(isGone())

        // act
        infoStream.onNext(Info("", ExperienceInfo(
                companyName = "sample",
                imageUrl = "",
                role = "Mobile Developer",
                date = "",
                responsibilities = listOf()
        )))

        // assert
        recyclerView.check(isVisible())
    }

    @Test
    fun testDisplayOfCorrectTitle() {
        // arrange
        val titleTV = onView(withId(R.id.titleTV))
        val detailsTV = onView(withId(R.id.detailsTV))

        // act
        infoStream.onNext(Info("sample title", "sample description"))

        // assert
        titleTV.check(matches(withText("sample title")))
        detailsTV.check(matches(withText("sample description")))
    }

    @Test
    fun testDetailsTvText() {
        // arrange
        val detailsTV = onView(withId(R.id.detailsTV))

        // act
        cvInfoLiveData.postValue(sampleCVInfo)

        // assert
        detailsTV.check(matches(withText("sample summary")))
    }

    @Test
    fun testDisablingOfNextButton() {
        // arrange
        val nextButton = onView(withId(R.id.nextButton))

        //act
        nextButtonTitleStream.onNext("Done")

        // assert
        nextButton.check(matches(not(isEnabled())))
    }

    private fun isVisible() = getViewAssertion(Visibility.VISIBLE)

    private fun isGone() = getViewAssertion(Visibility.GONE)

    private fun getViewAssertion(visibility: Visibility): ViewAssertion? =
            matches(withEffectiveVisibility(visibility))

}

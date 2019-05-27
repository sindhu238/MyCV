package com.example.mycv.fragments.briefCV

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mycv.api.ServerAPI
import com.example.mycv.enums.DescriptionType
import com.example.mycv.extensions.newLineSeparatedString
import com.example.mycv.models.CVInfo
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

interface BriefCVModel {
    val cvInfo: LiveData<CVInfo?>
    val infoStream: Observable<Info>
    val nextButtonObserver: BehaviorSubject<Unit>
    val titleObserver: BehaviorSubject<String>
    val nextButtonTitleStream: Observable<String>
}

data class Info(
        val title: String,
        val description: Any
)


class BriefCVViewModel @Inject constructor(
        private val serverAPI: ServerAPI
) : BriefCVModel, ViewModel() {

    override val nextButtonObserver: BehaviorSubject<Unit> = BehaviorSubject.createDefault(Unit)

    override val titleObserver: BehaviorSubject<String> = BehaviorSubject.createDefault("")

    override val cvInfo: LiveData<CVInfo?> by lazy {
        serverAPI.getCVDetails()
    }

    override val infoStream: Observable<Info>
        get() = nextButtonObserver.flatMap {
            Observable.just(
                    titleObserver.value?.let { name ->
                        when (name) {
                            "" -> Info(DescriptionType.Summary.name, cvInfo.value?.summary ?: "")
                            DescriptionType.Summary.name -> Info(DescriptionType.Skills.name, cvInfo.value?.skills?.newLineSeparatedString
                                    ?: "")
                            else -> cvInfo.value?.let {
                                Info(DescriptionType.Experience.name, it.experience)
                            } ?: Info("", "")
                        }
                    })
        }

    override val nextButtonTitleStream: Observable<String>
        get() = nextButtonObserver.flatMap {
            Observable.just(
                    titleObserver.value?.let { name ->
                        when (name) {
                            "", DescriptionType.Summary.name -> "Next"
                            else -> "Done"

                        }
                    }
            )
        }

}
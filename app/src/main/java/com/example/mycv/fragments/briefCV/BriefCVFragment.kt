package com.example.mycv.fragments.briefCV

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycv.R
import com.example.mycv.adapters.ExperienceAdapter
import com.example.mycv.core.rx.AutoDisposable
import com.example.mycv.enums.DescriptionType
import com.example.mycv.extensions.newLineSeparatedString
import com.example.mycv.models.CVInfo
import com.jakewharton.rxbinding3.view.clicks
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_brief_cv.*
import javax.inject.Inject

const val GIST_ID = "62c1a22f8eddc033905bff4481c4a681"

class BriefCVFragment : Fragment() {

    private val disposable = AutoDisposable()
    lateinit var cvInfo: CVInfo

    @Inject
    lateinit var viewModel: BriefCVModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        disposable.bindTo(this.lifecycle)
        return inflater.inflate(R.layout.fragment_brief_cv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTV.text = DescriptionType.Summary.name

        nextButton.clicks().subscribe(viewModel.nextButtonStream)

        viewModel.getCvInfo(GIST_ID).observe(this, Observer {
            it?.let { cvInfo ->
                this.cvInfo = cvInfo
                detailsTV.text = cvInfo.summary
            }
        })

        viewModel.nextButtonStream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    when (titleTV.text) {
                        DescriptionType.Summary.name -> {
                            titleTV.text = DescriptionType.Skills.name
                            detailsTV.text = cvInfo.skills.newLineSeparatedString
                        }
                        DescriptionType.Skills.name -> {
                            titleTV.text = DescriptionType.Experience.name
                            detailsTV.visibility = View.GONE
                            experienceRecyclerView?.apply {
                                visibility = View.VISIBLE
                                layoutManager = LinearLayoutManager(context)
                                adapter = ExperienceAdapter(cvInfo.experience)
                            }
                            nextButton.isEnabled = false
                        }

                    }
                }.let {
                    disposable.add(it)
                }
    }
}
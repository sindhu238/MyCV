package com.example.mycv.fragments.briefCV

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycv.R
import com.example.mycv.adapters.ExperienceAdapter
import com.example.mycv.core.rx.AutoDisposable
import com.example.mycv.models.CVInfo
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
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

        nextButton.clicks().subscribe(viewModel.nextButtonObserver)

        titleTV.textChanges().map {
            it.toString()
        }.subscribe(viewModel.titleObserver)

        viewModel.cvInfo.observe(this, Observer {
            it?.let { cvInfo ->
                this.cvInfo = cvInfo
                detailsTV.text = cvInfo.summary
            }
        })

        viewModel.infoStream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
            titleTV.text = it.title
            (it.description as? String)?.let { description ->
                detailsTV.text = description
            } ?: let {
                detailsTV.visibility = View.GONE
                experienceRecyclerView?.apply {
                    visibility = View.VISIBLE
                    layoutManager = LinearLayoutManager(context)
                    adapter = ExperienceAdapter(cvInfo.experience)
                }
            }
        }.let {
            disposable.add(it)
        }

        viewModel.nextButtonTitleStream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
            nextButton.text = it
            if (it == getString(R.string.done)) {
                nextButton.isEnabled = false
                nextButton.setTextColor(ContextCompat.getColor(context!!, R.color.black))
            }
        }.let {
            disposable.add(it)
        }
    }
}
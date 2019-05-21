package com.example.mycv.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycv.R
import com.example.mycv.api.ServerAPIImpl
import com.example.mycv.extensions.newLineSeparatedString
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        experienceRecyclerView?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ExperienceAdapter(listOf())
        }

        viewModel = ViewModelProviders.of(this, MyViewModelFactory(ServerAPIImpl)).get(MainViewModel::class.java)

        viewModel.getCvInfo(GIST_ID).observe(this, Observer {
            it?.let { cvInfo ->
                containerLayout.visibility = View.VISIBLE
                (experienceRecyclerView.adapter as? ExperienceAdapter)?.apply {
                    data = cvInfo.experience
                    notifyDataSetChanged()
                }
                nameTV.text = it.name
                addressTV.text = it.address
                summaryTV.text = it.summary
                skillsTV.text = it.skills.newLineSeparatedString
            }
        })
    }

    companion object {
        const val GIST_ID = "62c1a22f8eddc033905bff4481c4a681"
    }
}

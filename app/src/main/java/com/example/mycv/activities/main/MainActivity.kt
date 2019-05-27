package com.example.mycv.activities.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mycv.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingFragmentInjector
}

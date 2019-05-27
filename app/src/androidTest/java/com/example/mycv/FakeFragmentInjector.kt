package com.example.mycv

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.test.platform.app.InstrumentationRegistry
import com.example.mycv.activities.main.MainActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import javax.inject.Provider

inline fun <reified F : Fragment> createFakeFragmentInjector(crossinline block : F.() -> Unit)
        : DispatchingAndroidInjector<Activity> {
    var originalFragmentInjector: AndroidInjector<Fragment>? = null

    val myApp = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MyApplication
    val originalDispatchingActivityInjector = myApp.dispatchingActivityInjector

    val fragmentInjector = AndroidInjector<Fragment> { fragment ->
        originalFragmentInjector?.inject(fragment)
        if (fragment is F) {
            fragment.block()
        }
    }
    val fragmentFactory = AndroidInjector.Factory<Fragment> { fragmentInjector }
    val fragmentMap = mapOf(Pair<Class <*>, Provider<AndroidInjector.Factory<*>>>(F::class.java, Provider { fragmentFactory }))

    val activityInjector = AndroidInjector<Activity> { activity ->
        originalDispatchingActivityInjector.inject(activity)
        if (activity is MainActivity) {
            originalFragmentInjector = activity.supportFragmentInjector()
            activity.dispatchingFragmentInjector = DispatchingAndroidInjector_Factory.newInstance(fragmentMap, emptyMap())
        }
    }
    val activityFactory = AndroidInjector.Factory<Activity> { activityInjector }
    val activityMap = mapOf(Pair<Class <*>, Provider<AndroidInjector.Factory<*>>>(MainActivity::class.java, Provider { activityFactory }))
    return DispatchingAndroidInjector_Factory.newInstance(activityMap, emptyMap())
}

inline fun <reified F : Fragment> createFakeFragmentInjector1(crossinline block : F.() -> Unit)
        : DispatchingAndroidInjector<Fragment> {
    var originalFragmentInjector: AndroidInjector<Fragment>? = null

    val myApp = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MyApplication
    val originalDispatchingActivityInjector = myApp.dispatchingActivityInjector

    val fragmentInjector = AndroidInjector<Fragment> { fragment ->
        originalFragmentInjector?.inject(fragment)
        if (fragment is F) {
            fragment.block()
        }
    }
    val fragmentFactory = AndroidInjector.Factory<Fragment> { fragmentInjector }
    val fragmentMap = mapOf(Pair<Class <*>, Provider<AndroidInjector.Factory<*>>>(F::class.java, Provider { fragmentFactory }))

    val activityInjector = AndroidInjector<Activity> { activity ->
        originalDispatchingActivityInjector.inject(activity)
        if (activity is MainActivity) {
            originalFragmentInjector = activity.supportFragmentInjector()
            activity.dispatchingFragmentInjector = DispatchingAndroidInjector_Factory.newInstance(fragmentMap, emptyMap())
        }
    }
    val activityFactory = AndroidInjector.Factory<Activity> { activityInjector }
    val activityMap = mapOf(Pair<Class <*>, Provider<AndroidInjector.Factory<*>>>(MainActivity::class.java, Provider { activityFactory }))
    return DispatchingAndroidInjector_Factory.newInstance(activityMap, emptyMap())
}
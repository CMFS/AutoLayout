package com.cmfs.autolayout

import android.app.Activity
import android.app.Application
import android.os.Bundle

internal open class SimpleActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }
}

internal fun <A : Annotation> Any.withAnnotation(annotationClass: Class<A>, with: A.() -> Boolean = { false }): Boolean {
    val annotation = javaClass.getAnnotation(annotationClass)
    return if (annotation != null) {
        with(annotation)
    } else {
        false
    }
}
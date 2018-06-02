package com.cmfs.autolayout

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 布局注入注解
 *
 * [Activity]布局自动注入：
 * 1. 在[Application]中调用[Application.registerAutoLayout]
 * 2. 为[Activity]添加[AutoLayout]注解，[AutoLayout.value]配置布局文件ID，[AutoLayout.autoInject]配置为true
 *
 * [Activity]布局手动注入：
 * 为[Activity]添加[AutoLayout]注解，[AutoLayout.value]配置布局文件ID。若此时已经启用了自动注入，
 * [AutoLayout.autoInject]需配置为false，如未启用自动注入，可任意配置。
 *
 * [Fragment]布局自动注入：
 * [Fragment]必须继承自[AutoLayoutFragment]，或在您的[Fragment]基类的[Fragment.onCreateView]方法处返回
 * [Fragment.onAutoCreateView]。[AutoLayout.value]配置布局文件ID，[AutoLayout.autoInject]可任意配置。
 *
 * [Fragment]布局手动注入：
 * [Fragment]的[Fragment.onCreateView]方法处返回[Fragment.onAutoCreateView]。[AutoLayout.value]配置布
 * 局文件ID，[AutoLayout.autoInject]可任意配置。
 *
 * @author CMFS
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AutoLayout(@IdRes val value: Int,
                            val autoInject: Boolean = true)

private const val TAG = "AutoLayout"

/**
 * 启动布局自动注入
 */
fun Application.registerAutoLayout() = AutoLayoutManager.register(this)

/**
 * 手动注入布局
 */
fun Activity.injectAutoLayout(): Boolean = withAnnotation(AutoLayout::class.java) {
    @LayoutRes val layoutId = this.value
    checkLayoutId(layoutId)

    val autoInject = this.autoInject
    if (AutoLayoutManager.isRegistered) {
        if (!autoInject) {
            // 已注册自动注入，但标记为手动注入
            setContentView(layoutId)
        } else {
            // 已注册自动注入，自动注入
            Log.w(TAG, "Inject by application, no need to call activity.injectAutoLayout()")
            return@withAnnotation false
        }
    } else {
        // 未注册自动注入
        setContentView(layoutId)
    }
    return@withAnnotation true
}

/**
 * 注入布局
 */
fun Fragment.onAutoCreateView(inflater: LayoutInflater, container: ViewGroup?): View? {
    @LayoutRes val layoutId = getInjectLayoutId()
    checkLayoutId(layoutId)
    return inflater.inflate(layoutId, container, false)
}

/**
 * 获取[Fragment]配置的布局
 */
@LayoutRes
fun Fragment.getInjectLayoutId(): Int = javaClass.getAnnotation(AutoLayout::class.java).let {
    if (it == null) {
        // 没有配置
        return@let 0
    }
    @LayoutRes val layoutId = it.value
    checkLayoutId(layoutId)
    return@let layoutId
}

private fun checkLayoutId(layoutId: Int) {
    if (layoutId <= 0) {
        throw UnknownLayoutException("Unknown Layout with ID[$layoutId]")
    }
}

/**
 * 自动注入管理
 */
internal object AutoLayoutManager {
    var isRegistered: Boolean = false
    fun register(app: Application): Boolean {
        if (isRegistered) {
            return false
        }
        isRegistered = true
        app.registerActivityLifecycleCallbacks(AutoLayoutActivityLifecycleCallbacks())
        return true
    }
}

/**
 * 通过监听Activity的生命周期实现布局自动注入
 */
internal class AutoLayoutActivityLifecycleCallbacks : SimpleActivityLifecycleCallbacks() {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        // 自动注入
        autoInject(activity)
    }
}

/**
 * 自动注入
 *
 * 通过[android.app.Application.ActivityLifecycleCallbacks]自动注入
 */
private fun autoInject(@NonNull activity: Activity) = activity.withAnnotation(AutoLayout::class.java) {
    @LayoutRes val layoutId = this.value
    if (layoutId <= 0) {
        // Exception
        return@withAnnotation false
    }
    val autoInject = this.autoInject
    if (autoInject) {
        // 配置为自动注入
        activity.setContentView(layoutId)
        return@withAnnotation true
    } else {
        return@withAnnotation false
    }
}

/**
 * 支持自动注入的[Fragment]
 */
open class AutoLayoutFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) = onAutoCreateView(inflater, container)
}

/**
 * AutoLayout运行时异常
 */
open class AutoLayoutRuntimeException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}

/**
 * 未知布局异常
 */
class UnknownLayoutException : AutoLayoutRuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}
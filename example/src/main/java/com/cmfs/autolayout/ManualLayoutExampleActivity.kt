package com.cmfs.autolayout

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author CMFS
 */

@AutoLayout(
        value = R.layout.activity_manual_layout,
        autoInject = false
)
class ManualLayoutExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectAutoLayout()
    }
}
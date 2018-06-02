package com.cmfs.autolayout

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup

@AutoLayout(R.layout.fragment_auto_layout)
class AutoLayoutExampleFragment : AutoLayoutFragment()

@AutoLayout(R.layout.fragment_auto_layout2)
class AutoLayoutExampleFragment2 : AutoLayoutBaseFragment()

@AutoLayout(R.layout.fragment_auto_layout3)
class AutoLayoutExampleFragment3 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) = onAutoCreateView(inflater, container)
}

abstract class AutoLayoutBaseFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) = onAutoCreateView(inflater, container)
}
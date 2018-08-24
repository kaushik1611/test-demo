package com.inexture.test.demo.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.livinglifetechway.k4kotlin.setBindingView
import com.inexture.test.demo.R
import com.inexture.test.demo.databinding.ActivityDynamicLink2Binding

class DynamicLink2Activity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDynamicLink2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = setBindingView(R.layout.activity_dynamic_link2)


    }
}

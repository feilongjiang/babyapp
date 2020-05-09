package com.example.apps.baseProject.base

import androidx.fragment.app.Fragment
import com.example.apps.baseProject.R

abstract class SingleFragmentActivity : BaseActivity() {
    protected abstract fun createFragment(): Fragment
    override fun getLayoutId(): Int {
        return R.layout.activity_fragment
    }

    override fun afterCreate() {
        super.afterCreate()
        val manager = supportFragmentManager
        var fragment =
            manager.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            fragment = createFragment()
            manager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}
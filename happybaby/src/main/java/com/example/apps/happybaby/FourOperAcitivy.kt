package com.example.apps.happybaby

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.apps.baseProject.base.BaseActivity
import com.example.apps.baseProject.base.SingleFragmentActivity
import com.example.apps.happybaby.data.entity.Catergory
import com.example.apps.happybaby.ui.fourOper.FourOperFragment
import com.google.gson.Gson

class FourOperAcitivy : SingleFragmentActivity() {
    lateinit var mCatergory: Catergory
    override fun createFragment(): Fragment {
        return FourOperFragment.newInstance(mCatergory)
    }

    override fun beforeCreate(): Boolean {
        var i = intent.getStringExtra(CATEGORY)
        mCatergory = Gson().fromJson(i, Catergory::class.java)
        return super.beforeCreate()

    }

    companion object {
        private var CATEGORY = "category"
        fun newIntent(context: Context, category: Catergory): Intent {
            var i = Intent(context, FourOperAcitivy::class.java)
            i.putExtra(CATEGORY, Gson().toJson(category))
            return i
        }
    }
}
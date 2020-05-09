package com.example.apps.happybaby.views

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.marginEnd
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apps.happybaby.R
import com.example.apps.happybaby.databinding.SelelctSubNumBinding
import kotlinx.android.synthetic.main.selelct_sub_num.view.*

/**
 * nums 题目数量
 * mesh 题目目数
 */
class SelectSubDialog private constructor(val nums: IntArray, val mesh: IntArray) :
    DialogFragment() {
    private lateinit var mSelectSubNum: SelelctSubNumBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mSelectSubNum =
            SelelctSubNumBinding.inflate(LayoutInflater.from(context), null, false)
        setRadioButton(mSelectSubNum.root.num_radio_group, nums)
        setRadioButton(mSelectSubNum.root.mesh_radio_group, mesh)
        return AlertDialog.Builder(activity)
            .setView(mSelectSubNum.root)
            .setTitle(R.string.range_subject_title)
            .setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    var id = mSelectSubNum.root.num_radio_group.checkedRadioButtonId
                    var mesh = mSelectSubNum.root.mesh_radio_group.checkedRadioButtonId
                    mCallBack!!.setNumMesh(id, mesh)
                }

            })
            .create()
    }

    fun setRadioButton(radioGroup: RadioGroup, values: IntArray) {
        var checked = values[0]
        values.forEach {
            var radioButton = RadioButton(context)
            radioButton.text = it.toString()
            radioButton.id = it
            radioButton.isChecked = it == checked
            radioButton.gravity = Gravity.CENTER
            radioButton.setPadding(0, 0, 15, 0)
            radioGroup.addView(radioButton)
        }
    }

    interface CallBack {
        fun setNumMesh(num: Int, mesh: Int) // 回调点击的数字
    }

    private var mCallBack // 回调
            : CallBack? = null

    fun setOnCallBack(callBack: CallBack) {
        mCallBack = callBack
    }

    companion object {
        fun newInstance(nums: IntArray, mesh: IntArray): SelectSubDialog {
            return SelectSubDialog(nums, mesh)
        }
    }

}
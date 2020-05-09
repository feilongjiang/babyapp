package com.example.apps.happybaby.ui.happy

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR
import androidx.lifecycle.ViewModelProviders
import com.example.apps.baseProject.base.BaseFragment
import com.example.apps.happybaby.R
import com.example.apps.happybaby.databinding.FragmentHappyBinding
import com.example.apps.happybaby.views.SelectPanelDialog
import com.example.apps.happybaby.views.SelectSubDialog
import kotlinx.android.synthetic.main.fragment_happy.view.*
import kotlin.math.log


class HappyFragment : BaseFragment<FragmentHappyBinding>() {

    private lateinit var viewModel: HappyViewModel

    override fun afterCreate() {
        super.afterCreate()
        setHasOptionsMenu(true)
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
        viewModel =
            ViewModelProviders.of(this).get(HappyViewModel::class.java)
        initBtn()
    }

    fun initBtn() {
        contentView.colorImageBtn.setOnClickListener {
            initPaint()
            var manager = parentFragmentManager
            val COLORPLANEL = "COLORPLANEL"
            var sPDialog = SelectPanelDialog.newInStance(viewModel.colors)
            sPDialog.show(manager, COLORPLANEL)
            sPDialog.setOnCallBack(object : SelectPanelDialog.CallBack {
                override fun selectColor(@ColorInt color: Int) {
                    contentView.drawView.paint!!.color = color //设置笔的颜色
                }

            })
        }
        contentView.widthSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    var dv = contentView.drawView
                    when (position) {
                        0 -> dv.paint!!.strokeWidth = 1f //设置笔触的宽度为1像素
                        1 -> dv.paint!!.strokeWidth = 5f //设置笔触的宽度为5像素
                        2 -> dv.paint!!.strokeWidth = 10f //设置笔触的宽度为10像素
                    }
                }

            }
        contentView.widthSpinner.setSelection(1, true);
        contentView.clearImageBtn.setOnClickListener {
            if (!viewModel.isClear) {
                viewModel.upColor = contentView.drawView.paint!!.color
                viewModel.upWidth = contentView.drawView.paint!!.strokeWidth
                contentView.drawView.clear() //擦除绘画
            } else {
                val dv = contentView.drawView
                dv.paint!!.xfermode = null //取消擦除效果
                viewModel.upColor?.let {
                    contentView.drawView.paint!!.color = it
                }
                viewModel.upWidth?.let {
                    contentView.drawView.paint!!.strokeWidth = it
                }
            }
            viewModel.isClear = !viewModel.isClear
        }
        contentView.saveImageBtn.setOnClickListener {
            requestPermissions(
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                SAVE
            )
        }
        contentView.shareImageBtn.setOnClickListener {
            requestPermissions(
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                SHARE
            )
        }
        contentView.clearAllImageBtn.setOnClickListener {
            contentView.drawView.clearAll()
        }
    }

    private fun initPaint() {
        //获取自定义的绘图视图
        viewModel.isClear = false
        val dv = contentView.drawView
        dv.paint!!.xfermode = null //取消擦除效果
        dv.paint!!.strokeWidth = 5f //初始化画笔的宽度
    }


    /*
  * 当菜单项被选择时，做出相应的处理
  * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val dv = contentView.drawView
        when (item.itemId) {
            R.id.red -> {
                dv.paint!!.color = Color.RED //设置笔的颜色为红色
                item.isChecked = true
            }
            R.id.green -> {
                dv.paint!!.color = Color.GREEN //设置笔的颜色为绿色
                item.isChecked = true
            }
            R.id.blue -> {
                dv.paint!!.color = Color.BLUE //设置笔的颜色为蓝色
                item.isChecked = true
            }
            R.id.width_1 -> dv.paint!!.strokeWidth = 1f //设置笔触的宽度为1像素
            R.id.width_2 -> dv.paint!!.strokeWidth = 5f //设置笔触的宽度为5像素
            R.id.width_3 -> dv.paint!!.strokeWidth = 10f //设置笔触的宽度为10像素

        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requestCode == SAVE) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //如果没有获取权限，那么可以提示用户去设置界面--->应用权限开启权限
                    Toast.makeText(context, "保存图片需要存储权限", Toast.LENGTH_SHORT).show();
                } else {
                    contentView.drawView.save(requireActivity())
                }
            } else if (requestCode == SHARE) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //如果没有获取权限，那么可以提示用户去设置界面--->应用权限开启权限
                    Toast.makeText(context, "分享图片需要存储权限", Toast.LENGTH_SHORT).show();
                } else {
                    contentView.drawView.share(requireActivity())
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_happy
    }

    companion object {
        private val SHARE = 10001
        private val SAVE = 10002
    }
}

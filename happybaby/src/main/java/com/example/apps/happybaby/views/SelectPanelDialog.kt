package com.example.apps.happybaby.views

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.annotation.ColorInt
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apps.baseProject.irecyclerview.MyBaseAdapter
import com.example.apps.happybaby.databinding.ColorItemBinding
import com.example.apps.happybaby.databinding.ResultDialogItemBinding
import com.example.apps.happybaby.databinding.SelelctPanelDialogBinding
import com.example.apps.happybaby.ui.fourOper.Question
import com.example.apps.happybaby.utils.MyColor

class SelectPanelDialog private constructor(val mColors: List<MyColor>) : DialogFragment() {
    private lateinit var binding: SelelctPanelDialogBinding
    override fun onStart() {
        super.onStart()
        var win = dialog?.window;
        // 一定要设置Background，如果不设置，window属性设置无效
        win?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        var dm = DisplayMetrics();
        activity?.windowManager?.defaultDisplay?.getMetrics(dm);

        var params = win?.getAttributes();
        params?.gravity = Gravity.TOP;
        params?.y = 60
        params?.dimAmount = 0.0f
        win?.setAttributes(params);
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = SelelctPanelDialogBinding.inflate(LayoutInflater.from(context), null, false)
        addColorView()
        return AlertDialog.Builder(activity)
            .setView(binding.root)
            .create()
    }

    interface CallBack {
        fun selectColor(@ColorInt color: Int) // 回调点击的数字
    }

    private var mCallBack // 回调
            : CallBack? = null

    fun setOnCallBack(callBack: CallBack) {
        mCallBack = callBack
    }

    fun addColorView() {
        var adapter = object : MyBaseAdapter<MyColor>(mColors) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): BaseViewHolder<MyColor> {
                var view =
                    ColorItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return object : BaseViewHolder<MyColor>(view) {
                    override fun bind(item: MyColor) {
                        (binding as ColorItemBinding).setClickListener {
                            mCallBack?.selectColor(item.color)
                            this@SelectPanelDialog.dismiss()
                        }
                        (this.binding as ColorItemBinding).colorItem.setBackgroundColor(item.color)
                    }

                }
            }

        }
        var questionRecycler = binding.selectPanel
        var lm = GridLayoutManager(context, 10)
        questionRecycler.layoutManager = lm
        var dividerItemDecoration = DividerItemDecoration(
            context,
            lm.orientation
        )
        questionRecycler.addItemDecoration(dividerItemDecoration)
        questionRecycler.adapter = adapter

    }

    companion object {
        fun newInStance(colors: List<MyColor>): SelectPanelDialog {
            return SelectPanelDialog(colors)
        }
    }
}
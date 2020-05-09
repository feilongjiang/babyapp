package com.example.apps.happybaby.ui.fourOper

/**
 * 四则运算
 */

import android.os.SystemClock
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.apps.baseProject.base.BaseFragment

import com.example.apps.happybaby.R
import com.example.apps.happybaby.data.entity.Catergory
import com.example.apps.happybaby.data.InjectorUtils
import com.example.apps.happybaby.databinding.FourOperFragmentBinding
import com.example.apps.happybaby.views.CustomNumKeyView
import com.example.apps.happybaby.views.MyCallBack
import com.example.apps.happybaby.views.SelectSubDialog
import com.example.apps.happybaby.views.ShowResultDialog

class FourOperFragment(var category: Catergory) : BaseFragment<FourOperFragmentBinding>() {
    lateinit var mCustomNumKeyView: CustomNumKeyView
    private val viewModel: FourOperViewModel by viewModels<FourOperViewModel> {
        InjectorUtils.provideFourOperViewModelFactory(category)
    }

    override fun afterCreate() {
        super.afterCreate()
        viewModel.isReady.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.start()
            }
        })
        initNumkey()
        selectSubject()
    }

    /**
     * 开始做题
     */
    private fun start() {
        viewModel.currentIndex.observe(viewLifecycleOwner, Observer {
            setNumTextView(it)
            modifyShow(it)
        })
        viewModel.currentIndex.postValue(0)
        setTime()
    }

    /**
     * 显示问题
     */
    private fun modifyShow(index: Int) {
        var question = viewModel.getQuestions()[index]
        contentView.subjectTextView.text = question.mSubject
        contentView.answerText.text = ""
    }

    /**
     * 显示题数和总题数
     */
    private fun setNumTextView(index: Int) {
        var subjectNumTextView = contentView.subjectNumTextView
        subjectNumTextView.text = resources.getString(
            R.string.subject_num,
            (index + 1).toString(),
            viewModel.getQuestions().size.toString()
        )
    }

    /**
     * 计数
     */
    private fun setTime() {
        var timer = contentView.timer
        timer.base = SystemClock.elapsedRealtime()
        var hour = (SystemClock.elapsedRealtime() - timer.base) / 1000 / 60
        timer.format = "0$hour:%s"
        timer.start()
    }

    private fun selectSubject() {
        var manager = parentFragmentManager
        var selectSubDialog: SelectSubDialog
        if (category.pid!! < 5L) {
            selectSubDialog =
                SelectSubDialog.newInstance(intArrayOf(10, 20, 50), intArrayOf(2, 5, 10))
        } else {
            selectSubDialog = SelectSubDialog.newInstance(intArrayOf(10, 20), intArrayOf(2, 3, 5))
        }
        selectSubDialog.show(manager, DIALOG_SELCET_SUB)
        selectSubDialog.isCancelable = false
        selectSubDialog.setOnCallBack(object : SelectSubDialog.CallBack {
            override fun setNumMesh(num: Int, mesh: Int) {
                viewModel.setNumMesh(num, mesh)
            }

        })
    }

    private fun initNumkey() {
        mCustomNumKeyView = contentView.customNUmkeyView
        mCustomNumKeyView.setOnCallBack(object : CustomNumKeyView.CallBack {
            override fun clickNum(num: String) {
                var text = contentView.answerText.text.toString()
                if (num === "." && text.indexOf(".") != -1) {
                    Toast.makeText(context, "输入不合法", Toast.LENGTH_SHORT).show()
                    return
                }
                text += num
                if (text.length > 11) {
                    Toast.makeText(context, "答案太长", Toast.LENGTH_SHORT).show()
                    return
                }
                contentView.answerText.text = text
            }

            override fun deleteNum() {
                var text = contentView.answerText.text.toString()
                text = text.dropLast(1)
                contentView.answerText.text = text
            }

            override fun nextSubject() {
                if (!viewModel.isReady.value!! || !checkAnswer()) {
                    return
                }
                var currentIndex = viewModel.currentIndex.value!!
                var answerText = contentView.answerText.text.toString().toDouble()
                var answer = Question.format2(answerText)
                var question = viewModel.getQuestions()[currentIndex]
                question.isTrue = answer.equals(question.mMnswer)
                question.mUserAnswer = answer
                var index = currentIndex + 1
                if (index == viewModel.getQuestions().size) {
                    stop()
                    return
                }
                viewModel.currentIndex.postValue(index)
            }

        })
    }

    fun checkAnswer(): Boolean {
        var answerStr = contentView.answerText.text.toString()
        if (answerStr == "") {
            Toast.makeText(context, "请答题!", Toast.LENGTH_SHORT).show()
            return false
        }
        try {
            var answer = answerStr.toDouble()
        } catch (e: Exception) {
            Toast.makeText(context, "输入错误!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun stop() {
        contentView.timer.stop() //停止计时
        var manager = parentFragmentManager
        var showResultDialog: ShowResultDialog = ShowResultDialog.newInStance(
            viewModel.getQuestions(),
            contentView.timer.base,
            category.name!!
        )
        showResultDialog.show(manager, SHOWRESULTDIALOG)
        /*manager.executePendingTransactions()*/
        showResultDialog.setOnOkCallBack(object : MyCallBack {
            override fun onClick() {
                activity!!.finish()
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.four_oper_fragment
    }

    companion object {
        val DIALOG_SELCET_SUB = "select subject"
        val SHOWRESULTDIALOG = "showresultdialog"
        fun newInstance(category: Catergory) =
            FourOperFragment(category)
    }
}

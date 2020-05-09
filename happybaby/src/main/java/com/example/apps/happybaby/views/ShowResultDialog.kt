package com.example.apps.happybaby.views


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apps.baseProject.irecyclerview.MyBaseAdapter
import com.example.apps.happybaby.R
import com.example.apps.happybaby.databinding.ResultDialogItemBinding
import com.example.apps.happybaby.databinding.ShowResultDialogBinding
import com.example.apps.happybaby.ui.fourOper.Question
import com.example.apps.happybaby.ui.login.LoggedInUser
import com.example.apps.happybaby.utils.BitMapUtil
import com.example.apps.happybaby.utils.PermissionUtil
import com.example.apps.happybaby.utils.ShareUtil

class ShowResultDialog private constructor(
    val questions: List<Question>,
    val time: Long,
    val questionType: String
) :
    DialogFragment() {
    lateinit var mTrueBtton: Button
    lateinit var mShareBtn: ImageButton
    lateinit var binding: ShowResultDialogBinding
    lateinit var mErrQuestion: List<Question>
    private var mMyCallBack: MyCallBack? = null
    fun setOnOkCallBack(callBack: MyCallBack) {
        mMyCallBack = callBack
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setErrQuestion()
        binding = ShowResultDialogBinding.inflate(LayoutInflater.from(context), null, false)
        addQuestionView()
        return AlertDialog.Builder(activity)
            .setView(binding.root)
            .create()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mTrueBtton = binding.dialogBtnTrue
        mTrueBtton.setOnClickListener {

        }
        mTrueBtton.setOnClickListener {
            mMyCallBack!!.onClick()
        }
        var user = LoggedInUser.newInstance(null)
        var username = if (user != null) user.name else ""
        binding.diglogTitle.text = resources.getString(R.string.resultdialogTitle, username)
        binding.scopeTextView.text =
            resources.getString(R.string.scope, countScope().toString())
        binding.subjectNumResult.text =
            resources.getString(
                R.string.subject_num_result,
                questions.size.toString(),
                (questions.size - mErrQuestion.size).toString()
            )
        binding.dialogTimer.format = "用时 %s"
        binding.dialogTimer.base = time
        mShareBtn = binding.btnShare
        mShareBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PermissionUtil.WRITE_EXTERNAL_STORAGE
                )
            }

        })
        binding.questionType.text = resources.getString(R.string.question_type, questionType)
    }

    fun share() {
        var bitmap = BitMapUtil.screenshots(requireActivity(), binding.root)
        bitmap?.let {
            var path = BitMapUtil.savePhoto(requireContext(), it)
            path?.let { it1 ->
                ShareUtil.sharePhoto(requireContext(), it1)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionUtil.WRITE_EXTERNAL_STORAGE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //如果没有获取权限，那么可以提示用户去设置界面--->应用权限开启权限
                    Toast.makeText(context, "分享图片需要存储权限", Toast.LENGTH_SHORT).show();
                    requireActivity().finish()
                } else {
                    share()
                }
            }
        }
    }

    /**
     * 计算分数
     */
    fun countScope(): Int {
        var numTrue = 0
        numTrue = questions.count {
            it.isTrue
        }
        var sss = (100.0 / questions.size)
        var scope = sss * numTrue
        return scope.toInt()
    }

    fun setErrQuestion() {
        mErrQuestion = questions.filter {
            !it.isTrue
        }.toList()
        mErrQuestion = mErrQuestion.map {
            it.mMnswer = it.mMnswer.replace(".00", "")
            it.mUserAnswer = it.mUserAnswer.replace(".00", "")
            it
        }
    }

    fun addQuestionView() {
        var adapter = object : MyBaseAdapter<Question>(mErrQuestion) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): BaseViewHolder<Question> {
                var view =
                    ResultDialogItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return object : BaseViewHolder<Question>(view) {
                    override fun bind(item: Question) {
                        (this.binding as ResultDialogItemBinding).question = item
                    }

                }
            }

        }
        var questionRecycler = binding.questionRecycler
        var lm = questionRecycler.layoutManager as LinearLayoutManager
        var dividerItemDecoration = DividerItemDecoration(
            context,
            lm.orientation
        )
        questionRecycler.addItemDecoration(dividerItemDecoration)
        questionRecycler.adapter = adapter

    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        requireActivity().finish()
    }

    companion object {
        fun newInStance(
            questions: List<Question>,
            time: Long,
            questionType: String
        ): ShowResultDialog {
            return ShowResultDialog(questions, time, questionType)
        }
    }

}
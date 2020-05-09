package com.example.apps.happybaby.ui.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.apps.baseProject.base.BaseFragment
import com.example.apps.baseProject.irecyclerview.MyBaseAdapter
import com.example.apps.baseProject.net.HttpParams
import com.example.apps.baseProject.net.INetWork
import com.example.apps.happybaby.FourOperAcitivy
import com.example.apps.happybaby.R
import com.example.apps.happybaby.data.entity.Catergory
import com.example.apps.happybaby.data.InjectorUtils
import com.example.apps.happybaby.databinding.FragmentHomeBinding
import com.example.apps.happybaby.databinding.FragmentSubjectItemBinding
import com.example.apps.happybaby.utils.BASEURL
import com.example.apps.happybaby.utils.Helper
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels<HomeViewModel> {
        InjectorUtils.provideHomeViewModelFactory(requireContext())
    }

    override fun afterCreate() {
        super.afterCreate()
        homeViewModel.catergory.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                var httpParams = HttpParams.create(TAG, BASEURL + "category/read")
                INetWork.sendGet(httpParams, httpListener)
            } else {
                loadSubject(it);
            }
        })
    }

    override fun onSuccess(`object`: String?, taskId: String?) {
        `object`?.let {
            var apiData =
                Helper.fromJson<ArrayList<Catergory>>(
                    it,
                    object : TypeToken<ArrayList<Catergory>>() {}.type
                )
            if (apiData?.code == 200 && apiData.data != null) {
                homeViewModel.setCategory(apiData.data!!)
            }
        }
    }

    private fun loadSubject(categories: List<Catergory>) {
        if (categories.isEmpty()) {
            return
        }
        var spanCount = 3;
        val llm = GridLayoutManager(requireContext(), spanCount)
        contentView.root.subject_container.layoutManager = llm
        var adapter = object : MyBaseAdapter<Catergory>(categories) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): BaseViewHolder<Catergory> {
                val view: ViewDataBinding
                view = FragmentSubjectItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return object : BaseViewHolder<Catergory>(view) {
                    init {
                        (this.binding as FragmentSubjectItemBinding).setClickListener { view ->
                            var catergory =
                                (this.binding as FragmentSubjectItemBinding).category
                            if (catergory != null) {
                                var i = FourOperAcitivy.newIntent(
                                    parent.context,
                                    catergory
                                )
                                startActivity(i)
                            }
                        }
                    }

                    override fun bind(item: Catergory) {
                        (this.binding as FragmentSubjectItemBinding).category = item
                    }

                }
            }

        }
        contentView.root.subject_container.adapter = adapter
    }

    override fun onFailed(code: Int, msg: String?, taskId: String?) {
        super.onFailed(code, msg, taskId)
        showProcess("获取数据失败")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }
}

package com.example.apps.happybaby.ui.user

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.apps.baseProject.base.BaseFragment
import com.example.apps.happybaby.R
import com.example.apps.happybaby.data.InjectorUtils
import com.example.apps.happybaby.databinding.FragmentUserBinding
import com.example.apps.happybaby.ui.login.LoggedInUser
import com.example.apps.happybaby.ui.login.LoginActivity

class UserFragment : BaseFragment<FragmentUserBinding>() {
    private val args: UserFragmentArgs by navArgs()
    private val userViewModel: UserViewModel by viewModels<UserViewModel> {
        InjectorUtils.provideUserViewModelFactory(requireActivity(), args.userId)
    }

    override fun afterCreate() {
        userViewModel.user.observe(viewLifecycleOwner, Observer {
            contentView.viewModel = userViewModel;
            if (it == null) {
                contentView.usernameTextView.text = resources.getString(R.string.tourist)
            }
            if (it?.avatar == null) {
                contentView.avater.setImageDrawable(resources.getDrawable(R.drawable.ic_person_foreground))
            }
        })
        contentView.userSetting.setOnClickListener {
            checkLogin()
        }
    }

    fun checkLogin() {
        var loggedInUser = LoggedInUser.newInstance(null)
        if (loggedInUser == null) {
            var i = Intent(context, LoginActivity::class.java)
            newActivity(i)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_user
    }


}

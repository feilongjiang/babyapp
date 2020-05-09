package com.example.apps.happybaby


import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.apps.baseProject.base.BaseActivity
import com.example.apps.baseProject.net.HttpParams
import com.example.apps.baseProject.net.INetWork
import com.example.apps.happybaby.data.entity.User
import com.example.apps.happybaby.data.InjectorUtils
import com.example.apps.happybaby.ui.login.LoggedInUser
import com.example.apps.happybaby.ui.user.UserFragmentArgs
import com.example.apps.happybaby.utils.BASEURL
import com.example.apps.happybaby.utils.Helper
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BaseActivity() {
    private var user: LiveData<User> = MutableLiveData<User>();
    private var currentNavController: MutableLiveData<NavController> =
        MutableLiveData<NavController>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        afterCreate()
    }

    override fun afterCreate() {
        super.afterCreate()
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        var navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_happy, R.id.navigation_user
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        currentNavController.postValue(navController)
    }

    private val onNavigationItemSelectedListener = object :
        BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            if (item.itemId == R.id.navigation_user) {
                val userId = LoggedInUser.newInstance(null)?.id ?: 0
                var bundle = UserFragmentArgs.Builder().setUserId(userId).build().toBundle()
                currentNavController.value?.navigate(item.itemId, bundle)
                return true
            }
            currentNavController.value?.navigate(item.itemId)
            return true
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun doInit() {
        super.doInit()
        this.user = InjectorUtils.getUserRepository(this@MainActivity).getFirst();
        this.user.observe(this, Observer {
            if (it !== null) {
                HttpParams.addHeader("authorization", it.token)
                var httpParams = HttpParams.create(TAG, BASEURL + "verifyLogin")
                INetWork.sendGet(httpParams, httpListener)
            }
        })
    }

    override fun onSuccess(`object`: String?, taskId: String?) {
        if (`object` != null) {
            var apiData = Helper.fromJson<User>(`object`)
            if (apiData?.code == 200) {
                LoggedInUser.newInstance(apiData.data)
                return
            }
        }
        var userDao = InjectorUtils.getUserRepository(this);
        LoggedInUser.destory()
        Thread {
            Runnable {
                userDao.getAll().let { it.value?.let { it1 -> userDao.delete(it1) } };
            }.run()
        }.start()
    }

    override fun onFailed(code: Int, msg: String?, taskId: String?) {
        super.onFailed(code, msg, taskId)
    }
}


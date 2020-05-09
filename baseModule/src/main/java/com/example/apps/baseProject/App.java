package com.example.apps.baseProject;

import android.app.Application;

import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * Created by yangchun on 16/4/28.  全局的Application
 */
public class App extends Application {

    private static Application app;
    @Override
    public void onCreate() {
        super.onCreate();
   //     LeakCanary.install(this);
        app = this;
        AutoLayoutConifg.getInstance().useDeviceSize();
    }

    public static void init(Application application){

        app = application;
    }

    public static Application getInstance(){

        return app;
    }
}
package com.example.apps.baseProject.base;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class BaseService extends IntentService {

	private IntentFilter killSelf;
    public boolean kill = false;
    public Context mContext;

    public static final String KILL_SELF = "kill_self";

	@Override
	protected void onHandleIntent(Intent intent) {

	}

	public BaseService(String name) {
		super(name);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	private BroadcastReceiver killSelfReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			
			if(intent.getAction().equals(KILL_SELF)){
                kill = true;
                killAllService();
			}
		}
	};

    public void killAllService(){

        //  下面的代码用于不启用某个receiver，即使广播发出，也不会收到

//        String pkgName = getPackageName();
//        ComponentName callReceiver = new ComponentName(pkgName,
//                pkgName + ".broadcastReceiver.AlarmReceiver");
//
//        getPackageManager().setComponentEnabledSetting(callReceiver,
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP);

        Intent kill = new Intent(KILL_SELF);
        sendBroadcast(kill);

        this.stopSelf();
    }

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }
	
	@Override
	public void onCreate() {
		super.onCreate();

        killSelf = new IntentFilter(KILL_SELF);
        mContext = this;
        registerReceiver(killSelfReceiver, killSelf);
	}

	public void onDestroy() {
		if(killSelf!=null){
			unregisterReceiver(killSelfReceiver);
			killSelf = null;
		}
        super.onDestroy();
	}
}

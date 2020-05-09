package com.example.apps.baseProject.net;

/**
 * Created by yangchun on 16/4/5.
 */
public class ITransferListener {

    public void onProcess(long total, int current){};
    public void onError(){};
    public void onSuccess(String response){};
    public void onDownload(String file, String fileUri){};
}

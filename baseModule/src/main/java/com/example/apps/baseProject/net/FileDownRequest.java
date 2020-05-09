package com.example.apps.baseProject.net;

import android.net.Uri;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by yangchun on 16/5/30.
 */
public class FileDownRequest extends Request<byte[]> {

    private ITransferListener downListener;
    private String savePath;

    public FileDownRequest(String url, ITransferListener downListener, String savePath) {
        super(Method.GET, url, null);
        this.savePath = savePath;
        this.downListener = downListener;
        setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 20,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected void deliverResponse(byte[] response) {
        if (response == null) return;
        try {
            InputStream input = new ByteArrayInputStream(response);
            int total = response.length;
            int read = 0;
            int percent = 0;
            File file = new File(savePath);
            if (file.exists()) {
                savePath = file.getParent() + "/" + System.currentTimeMillis() + "_" + file.getName();
                file = new File(savePath);
            }
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
            byte data[] = new byte[1024];
            int count;
            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
                if (downListener == null) continue;
                read += data.length;
                int tmp = read * 100 / total;
                if (tmp > percent) {
                    percent = tmp;
                    downListener.onProcess(total, read);
                }
            }
            output.flush();
            output.close();
            input.close();
            if (downListener != null) {
                downListener.onDownload(savePath, Uri.fromFile(file).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}

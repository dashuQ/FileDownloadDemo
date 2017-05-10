package com.example.filedownloaddemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.filedownloaddemo.file.FileDownloadManager;

/**
 * Created by lenovo on 2017/5/10.
 */

public class FileDownloadPackageBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        FileDownloadManager.checkDownloadStatus(context, FileDownloadManager.fileName);//检查下载状态
    }
}

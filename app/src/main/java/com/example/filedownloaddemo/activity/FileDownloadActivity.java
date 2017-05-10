package com.example.filedownloaddemo.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.filedownloaddemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FileDownloadActivity extends AppCompatActivity {

    @BindView(R.id.btn_start_file_download)
    Button btnStartFileDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_download);

        ButterKnife.bind(this);

    }

    /**
     * 需要以下2个权限：
     * <uses-permission android:name="android.permission.INTERNET" />
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     *
     * @param context
     * @param downloadUrl
     * @param dirType
     * @param subPath
     */
    private void fileDownload(Context context, String downloadUrl, String dirType, String subPath) {
        //创建下载任务,downloadUrl就是下载链接
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        //指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir(dirType, subPath);
        //获取下载管理器
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载任务加入下载队列，否则不会进行下载
        downloadManager.enqueue(request);
    }

    @OnClick(R.id.btn_start_file_download)
    public void onViewClicked() {
        //微信apk应用宝下载地址
        String downloadUrl = "http://116.211.184.174/imtt.dd.qq.com/16891/A57EB927AA21AB73D521BE20DD0BF61B.apk?mkey=59100251fe3137ee&f=9512&c=0&fsname=com.tencent.mm_6.5.7_1041.apk&csr=1bbd&p=.apk";
        String sdcardPath = "/download/";
        String fileName = "a.apk";
        fileDownload(FileDownloadActivity.this, downloadUrl, sdcardPath, fileName);
    }
}

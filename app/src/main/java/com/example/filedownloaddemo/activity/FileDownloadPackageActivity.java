package com.example.filedownloaddemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.filedownloaddemo.R;
import com.example.filedownloaddemo.service.FileDownloadPackageService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件下载封装
 */
public class FileDownloadPackageActivity extends AppCompatActivity {

    @BindView(R.id.btn_start_file_download)
    Button btnStartFileDownload;
    @BindView(R.id.button4)
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_download_package);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_start_file_download)
    public void onViewClicked() {
        Intent intent=new Intent(FileDownloadPackageActivity.this, FileDownloadPackageService.class);
        intent.setAction(FileDownloadPackageService.ACTION_FOO);
        startService(intent);
    }
}

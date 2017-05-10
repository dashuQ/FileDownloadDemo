package com.example.filedownloaddemo.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;

import com.example.filedownloaddemo.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件下载高级用法
 */
public class FileDownloadAdvancedUsageActivity extends AppCompatActivity {

    @BindView(R.id.btn_start_file_download)
    Button btnStartFileDownload;
    @BindView(R.id.button4)
    Button button4;
    private DownloadManager downloadManager;
    private long mTaskId;
    private Context TAG;
    private String TAGStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_download_advanced_usage);

        ButterKnife.bind(this);

        TAG = FileDownloadAdvancedUsageActivity.this;
        TAGStr = FileDownloadAdvancedUsageActivity.this.getClass().toString();

    }

    String downloadUrl = "http://116.211.184.174/imtt.dd.qq.com/16891/A57EB927AA21AB73D521BE20DD0BF61B.apk?mkey=59100251fe3137ee&f=9512&c=0&fsname=com.tencent.mm_6.5.7_1041.apk&csr=1bbd&p=.apk";
    String sdcardPath = "/download/";
    String fileName = "a.apk";

    @OnClick({R.id.btn_start_file_download, R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start_file_download:

                downloadAPK(TAG, downloadUrl, sdcardPath, fileName);

                break;
            case R.id.button4:
                break;
        }
    }

    //使用系统下载器下载
    private void downloadAPK(Context context, String downloadUrl, String dirType, String subPath) {
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        request.setAllowedOverRoaming(false);//漫游网络是否可以下载

        //设置文件类型，可以在下载结束后自动打开该文件
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(downloadUrl));
        request.setMimeType(mimeString);

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);

        //sdcard的目录下的download文件夹，必须设置
        request.setDestinationInExternalPublicDir(dirType, subPath);
        //request.setDestinationInExternalFilesDir(),也可以自己制定下载路径

        //将下载请求加入下载队列
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        //加入下载队列后会给该任务返回一个long型的id，
        //通过该id可以取消任务，重启任务等等，看上面源码中框起来的方法
        mTaskId = downloadManager.enqueue(request);

        //注册广播接收者，监听下载状态
        context.registerReceiver(receiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //接下来是广播接收器
    //广播接受者，接收下载状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus(context);//检查下载状态
        }
    };

    //检查下载状态
    //检查下载状态
    private void checkDownloadStatus(Context context) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mTaskId);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.i(TAGStr, ">>>下载暂停");
                case DownloadManager.STATUS_PENDING:
                    Log.i(TAGStr, ">>>下载延迟");
                case DownloadManager.STATUS_RUNNING:
                    Log.i(TAGStr, ">>>正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Log.i(TAGStr, ">>>下载完成");
                    //下载完成安装APK
                    String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + fileName;
                    installAPK(context, new File(downloadPath));
                    break;
                case DownloadManager.STATUS_FAILED:
                    Log.i(TAGStr, ">>>下载失败");
                    break;
            }
        }
    }

    //安装APK
    //下载到本地后执行安装
    protected void installAPK(Context context, File file) {
        if (!file.exists()) return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file://" + file.toString());
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        //在服务中开启activity必须设置flag,后面解释
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}

package com.example.filedownloaddemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.example.filedownloaddemo.file.FileDownloadManager;
import com.example.filedownloaddemo.receiver.FileDownloadPackageBroadcastReceiver;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FileDownloadPackageService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "com.example.filedownloaddemo.service.action.FOO";
    private static final String ACTION_BAZ = "com.example.filedownloaddemo.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.filedownloaddemo.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.filedownloaddemo.service.extra.PARAM2";

    public FileDownloadPackageService() {
        super("FileDownloadPackageService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, FileDownloadPackageService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, FileDownloadPackageService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
//        throw new UnsupportedOperationException("Not yet implemented");

        String downloadUrl =FileDownloadManager.downloadUrl;// "http://116.211.184.174/imtt.dd.qq.com/16891/A57EB927AA21AB73D521BE20DD0BF61B.apk?mkey=59100251fe3137ee&f=9512&c=0&fsname=com.tencent.mm_6.5.7_1041.apk&csr=1bbd&p=.apk";
        String sdcardPath = FileDownloadManager.sdcardPath;//"/download/";
        String fileName = FileDownloadManager.fileName;//"a.apk";
        long taskId= FileDownloadManager.downloadAPK(getApplicationContext(), downloadUrl, sdcardPath, fileName,new FileDownloadPackageBroadcastReceiver());
        FileDownloadManager.taskId=taskId;
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

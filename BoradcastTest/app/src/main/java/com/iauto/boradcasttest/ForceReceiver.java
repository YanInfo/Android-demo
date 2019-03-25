package com.iauto.boradcasttest;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;

import com.iauto.boradcasttest.tools.ActivityCollector;

import static android.content.ContentValues.TAG;

/**
 * @author zhangyan
 * 在Manifest中绑定强制下线的广播
 */

public class ForceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        Log.d(TAG, "################# alertDialog #################");

        if (!intent.getAction().equals("Mybroadcast")) {
            //构建一个对话框
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setTitle("警告");
            dialogBuilder.setMessage("呵呵,你的账号被强制下线，请重新登录");
            //设置对话框不可被取消
            dialogBuilder.setCancelable(false);
            //给对话框注册确定按钮
            dialogBuilder.setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //调用finish(),销毁所有活动
                    ActivityCollector.finishAll();
                    Intent intent = new Intent(context, LoginActivity.class);
                    // 在广播接收器中启动活动 因此需要加上这个标志
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // 重新启动登录界面
                    context.startActivity(intent);
                }
            });

            //设置AlertDialog 的类型 保证广播接收器中可以正常的弹出
            AlertDialog alertDialog = dialogBuilder.create();

            //系统级别的对话框 因此需要在AndroidManifest中进行声明
            alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

            //设置对话框可见
            alertDialog.show();
        }
    }
}


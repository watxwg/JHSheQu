package com.jhcms.waimaiV3.sersice;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.jhcms.waimaiV3.activity.WaiMaiMainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;


public class JPushBroadcast extends BroadcastReceiver {
    private NotificationManager nm;
    String type = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        receivingNotification(context, intent);
    }

    private void receivingNotification(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if (!TextUtils.isEmpty(extras)) {
            try {
                JSONObject json = new JSONObject(extras);
                type = json.getString("type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Intent i = new Intent(context, WaiMaiMainActivity.class);  //自定义打开的界面
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            switch (type) {
                case "order":
                    intent.putExtra(WaiMaiMainActivity.TYPE, "GO_ORDER");
                    break;
                case "hongbao":
                    break;
                default:
                    break;
            }
            context.startActivity(i);
        }
    }
}
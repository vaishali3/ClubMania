package com.manan.dev.clubconnect.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.manan.dev.clubconnect.DashboardUserActivity;
import com.manan.dev.clubconnect.R;

import java.util.Calendar;

public class MyNotificationService extends Service{

    private int startId;
    private boolean isrunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String eventId = intent.getStringExtra("eventId");
        String eventName = intent.getStringExtra("eventName");
        Integer uniqueId = intent.getIntExtra("uniqueId", 0);

        NotificationManager notifyi = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent_i = new Intent(this.getApplicationContext(), DashboardUserActivity.class)
                .setAction(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        intent_i.putExtra("eventId", eventId);
        PendingIntent pending_intent_i = PendingIntent.getActivity(this, 0, intent_i, PendingIntent.FLAG_ONE_SHOT);
        Notification popup = new Notification.Builder(this)
                .setContentTitle("Eventesia")
                .setContentText(eventName + " is about to start. Please hurry up.")
                .setContentIntent(pending_intent_i)
                .setSmallIcon(R.drawable.temp_logo)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .build();

        notifyi.notify(uniqueId, popup);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Intent intentij = new Intent("Myjob.intent.action.Launch");
        startActivity(intentij);
        super.onDestroy();
        this.isrunning=false;

    }

}

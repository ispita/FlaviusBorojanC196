package com.example.flaviusborojanc196;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import static com.example.flaviusborojanc196.FlaviusBorojanC196.CHANNEL_1_ID;

public class DateBroadcast extends BroadcastReceiver {
    private NotificationManagerCompat notificationManager;
    final CourseAdapter adapter = new CourseAdapter();



    @Override
    public void onReceive(Context context, Intent intent) {
        notificationManager = NotificationManagerCompat.from(context);

                Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.ic_save)
                        .setContentTitle(intent.getStringExtra("title"))
                        .setContentText("Please log into the app to check")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                notificationManager.notify(1, notification);

    }

}

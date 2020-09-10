package com.yumtaufik.gitser.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.activity.SearchActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {

    final String EXTRA_TITLE = "com.yumtaufik.gitser.service.EXTRA_TITLE";
    final String EXTRA_MESSAGE = "com.yumtaufik.gitser.service.EXTRA_MESSAGE";

    String TITLE_REMINDER = "Reminder";
    String TITLE_MESSAGE = "Let's find popular user on Github";

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentMessage = intent.getStringExtra(EXTRA_MESSAGE);

        showAlarmNotification(context, intentMessage);
    }

    public void setRepeatingAlarmNotification(Context context, String time) {

        String TIME_FORMAT = "HH:mm";

        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TITLE, TITLE_REMINDER);
        intent.putExtra(EXTRA_MESSAGE, TITLE_MESSAGE);

        String[] alarmArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(alarmArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(alarmArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_HOUR,
                    pendingIntent
            );
        }
    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

    private void showAlarmNotification(Context context, String message) {
        String CHANNEL_ID = "Channel 1";
        String CHANNEL_NAME = "Alarm Channel 1";
        int requestId = (int) System.currentTimeMillis();
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri ringtoneManager = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent mIntent = new Intent(context, SearchActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestId, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.gitser_g)
                .setContentTitle(TITLE_REMINDER)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentText(message)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setSound(ringtoneManager)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.gitser));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.enableVibration(true);
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(101, notification);
        }
    }
}

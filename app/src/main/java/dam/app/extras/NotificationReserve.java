package dam.app.extras;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import dam.app.R;
import dam.app.activity.ActivityReserves;

public class NotificationReserve extends BroadcastReceiver {

    private Context _CONTEXT;

    private final static int NOTIFICATION_ID = 0;
    public static final String ID = "CONFIRM_RESERVE";
    public static final String idIntent = "intent_notification";

    private String CHANNEL_ID;

    @Override
    public void onReceive(Context context, Intent intent) {
        _CONTEXT = context;
        CHANNEL_ID = _CONTEXT.getResources().getString(R.string.channel_name);
        createNotificationChannel();
        createNotification();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(_CONTEXT.getResources().getString(R.string.channel_description));
            NotificationManager notificationManager = _CONTEXT.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotification(){
        Intent intent = new Intent(_CONTEXT, ActivityReserves.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(_CONTEXT, 0, intent, 0);

        NotificationCompat.Builder myBuilder = new NotificationCompat.Builder(_CONTEXT, CHANNEL_ID);
        myBuilder.setSmallIcon(R.mipmap.icon_reserve);
        myBuilder.setContentTitle(_CONTEXT.getResources().getString(R.string.title_notification_reserve));
        myBuilder.setContentText(_CONTEXT.getResources().getString(R.string.context_text_notification_reserve));
        myBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        myBuilder.setContentIntent(pendingIntent);
        myBuilder.setLights(Color.MAGENTA, 1000,1000);
        myBuilder.setVibrate(new long[]{1000,1000,1000});
        myBuilder.setDefaults(Notification.DEFAULT_SOUND);
        myBuilder.setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(_CONTEXT);
        notificationManagerCompat.notify(NOTIFICATION_ID, myBuilder.build());
    }
}

class NotificationPublisher extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String identificador = intent.getStringExtra(NotificationReserve.idIntent);
        Log.i("broadcast", "Ingreso al escuchador");
    }

}

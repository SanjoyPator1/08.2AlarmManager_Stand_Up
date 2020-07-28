package com.google.sanjoy.a082standup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    //VARIABLES
    //STEP 4 -
    private NotificationManager mNotificationManager;

    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //my code
        //STEP 1 -
        ToggleButton alarmToggle = findViewById(R.id.alarmToggle);

        // STEP 17.01 -
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);

        //STEP 22 -
        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null);

        //STEP 23 -
        //This ensures that the toggle is always on if the alarm is set, and off otherwise.
        alarmToggle.setChecked(alarmUp);

        // STEP 17.02 -
        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //STEP 18 -
        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //STEP 5 -
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);



        //STEP 2 -
        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //STEP 3 -
                String toastMessage;
                if(isChecked){
                    //Set the toast message for the "on" case.

                    //STEP 11-
                    // deliverNotification(MainActivity.this); - STEP 19 - remove this method

                    //STEP 20 -
                    //long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                    long repeatInterval = 30000;    //half minute to see the notification quickly
                    long triggerTime = SystemClock.elapsedRealtime()
                            + repeatInterval;

                    //If the Toggle is turned on, set the repeating alarm with a 15 minute interval
                    if (alarmManager != null) {
                        alarmManager.setInexactRepeating
                                (AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                        triggerTime, repeatInterval, notifyPendingIntent);
                    }

                    toastMessage = "Stand Up Alarm On!";
                } else {
                    //Set the toast message for the "off" case.
                    //STEP 12 -
                    mNotificationManager.cancelAll();
                    toastMessage = "Stand Up Alarm Off!";

                    //STEP 21 -
                    if (alarmManager != null) {
                        alarmManager.cancel(notifyPendingIntent);
                    }
                }

                //Show a toast to say the alarm is turned on or off.
                Toast.makeText(MainActivity.this, toastMessage,Toast.LENGTH_SHORT)
                        .show();
            }
        });

        //STEP 7-
        //call the createNotificationChannelmethod
        createNotificationChannel();



    }

    //STEP 6 -
    /**
            * Creates a Notification channel, for OREO and higher.
    */
    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifies every 15 minutes to stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    //

    /**
    //STEP 8 -
    private void deliverNotification(Context context) {

        Intent contentIntent = new Intent(context, MainActivity.class);

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        //STEP 9 -
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle("Stand Up Alert")
                .setContentText("You should stand up and walk around now!")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        //STEP 10 -
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

    }
     **/


}
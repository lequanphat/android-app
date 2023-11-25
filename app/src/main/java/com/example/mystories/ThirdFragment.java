package com.example.mystories;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.List;


public class ThirdFragment extends Fragment {

    Button button;
    TextView textView;
    int alarm_hours = 7;
    int alarm_minutes = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChanel();
    }

    public void initViews(View root) {
        textView = root.findViewById(R.id.time);
        button = root.findViewById(R.id.button);
        button.setOnClickListener(v -> {
            showTimePickerDialog();
        });
        getAlarmPreferences();
        textView.setText(alarm_hours+":"+alarm_minutes);
    }

    public void showTimePickerDialog(){
        int hour, minute;
        Calendar currentTime = Calendar.getInstance();
        hour = currentTime.get(Calendar.HOUR_OF_DAY);
        minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                (view, selectedHour, selectedMinute) -> {
                    alarm_hours = selectedHour;
                    alarm_minutes = selectedMinute;
                    textView.setText(alarm_hours+ ":"+alarm_minutes);
                    saveAlarmPreferences();
                    sendNotification();
                },
                hour,
                minute,
                true
        );
        timePickerDialog.show();
    }
    public void sendNotification(){
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarm_hours);
        calendar.set(Calendar.MINUTE, alarm_minutes);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(getContext(), ReminderBroadCast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0 );
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
    public void createNotificationChanel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "MY_CHANEL";
            String description = "MY_CHANEL";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("MY_CHANEL", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void saveAlarmPreferences() {
        SharedPreferences.Editor editor = getContext().getSharedPreferences("AlarmPrefs", Context.MODE_PRIVATE).edit();
        editor.putInt("alarm_hours", alarm_hours);
        editor.putInt("alarm_minutes", alarm_minutes);
        editor.apply();
    }
    public void getAlarmPreferences(){
        SharedPreferences prefs = getContext().getSharedPreferences("AlarmPrefs", Context.MODE_PRIVATE);
        alarm_hours = prefs.getInt("alarm_hours", alarm_hours);
        alarm_minutes = prefs.getInt("alarm_minutes", alarm_minutes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.third_fragment, container, false);
        initViews(root);
        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        sendNotification();
    }
}
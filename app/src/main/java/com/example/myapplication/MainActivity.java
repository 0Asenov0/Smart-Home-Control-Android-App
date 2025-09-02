package com.example.myapplication;

import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private TextView textViewElement;
    Switch switchLights, switchFan,switchCurtains,switchDoor,switchAC;
    private final String MICROCONTROLLER_IP = "http://192.168.0.100";  // FIXED IP ADDRESS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewElement = findViewById(R.id.textView);
        switchLights = findViewById(R.id.switchLights);
        switchFan = findViewById(R.id.switchFan);
        switchCurtains = findViewById(R.id.switchCurtains);
        switchDoor = findViewById(R.id.switchDoor);
        switchAC = findViewById(R.id.switchAC);



        setupSwitch(switchLights, "lights", "ON", "OFF");
        setupSwitch(switchFan, "fan", "ON", "OFF");
        setupSwitch(switchCurtains, "curtains", "DOWN", "UP");
        setupSwitch(switchDoor, "door", "LOCKED", "UNLOCKED");
        setupSwitch(switchAC, "ac", "ON", "OFF");

        /*

        switchFan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // This is where you handle the state change
                if (isChecked) {
                    // Switch is ON
                    //   makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                    //    sendRequest(MICROCONTROLLER_IP + "/fan/on");
                //    makeText(MainActivity.this, "FAN HAS BEEN TURNED ON", Toast.LENGTH_SHORT).show();
                    switchFan.setText("ON");
                } else {
                    // Switch is OFF
                    //    sendRequest(MICROCONTROLLER_IP + "/fan/off");
                //    makeText(MainActivity.this, "FAN HAS BEEN TURNED OFF", Toast.LENGTH_SHORT).show();
                    switchFan.setText("OFF");
                }
            }
        });

         */
    }

    private void setupSwitch(Switch deviceSwitch, String deviceName, String labelOn, String labelOff) {
        deviceSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String url = MICROCONTROLLER_IP + "/" + deviceName + (isChecked ? "/on" : "/off");
            sendRequest(url);
            deviceSwitch.setText(isChecked ? labelOn : labelOff);
        });
    }

    // Method to send HTTP request
    private void sendRequest(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(30,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .build();


        new Thread(() -> {
            try {
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();

                if (!response.isSuccessful()) {
                    Log.e("HTTP_ERROR", "Unexpected code " + response);
                    return;
                }

                String responseBody = response.body().string();
                Log.d("HTTP_SUCCESS", "Response: " + responseBody);

                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "ESP says: " + responseBody, Toast.LENGTH_SHORT).show();
                });

            } catch (IOException e) {
                Log.e("HTTP_ERROR", "Request failed: " + e.getMessage());
            }
        }).start();
    }
}
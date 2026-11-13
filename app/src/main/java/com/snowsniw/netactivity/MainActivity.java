package com.snowsniw.netactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {

    private EditText etHost;
    private EditText etCount;
    private Button btnPing;
    private TextView tvOutput;
    private ScrollView scrollLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etHost = findViewById(R.id.etHost);
        etCount = findViewById(R.id.etCount);
        btnPing = findViewById(R.id.btnPing);
        tvOutput = findViewById(R.id.tvOutput);
        scrollLog = findViewById(R.id.scrollLog);

        btnPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPing();
            }
        });
    }

    private void startPing() {
        String rawHost = etHost.getText().toString().trim();
        if (rawHost.isEmpty()) {
            tvOutput.setText("Ошибка: введи IP или домен.");
            return;
        }

        String cleaned = rawHost.replace("https://", "").replace("http://", "");
        int slashIndex = cleaned.indexOf('/');
        if (slashIndex != -1) {
            cleaned = cleaned.substring(0, slashIndex);
        }
        final String host = cleaned.replaceAll("[^a-zA-Z0-9.\\-:]", "");

        int parsedCount = 4;
        try {
            parsedCount = Integer.parseInt(etCount.getText().toString().trim());
            if (parsedCount < 1) parsedCount = 1;
            if (parsedCount > 50) parsedCount = 50;
        } catch (Exception e) {
            parsedCount = 4;
        }
        final int count = parsedCount;

        btnPing.setEnabled(false);
        btnPing.setText("Идет проверка...");
        tvOutput.setText("> Резолв DNS для " + host + "...\n");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress address = InetAddress.getByName(host);
                    appendLog("> IP: " + address.getHostAddress() + "\n");

                    Process process = Runtime.getRuntime().exec("/system/bin/ping -c " + count + " " + host);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        appendLog(line);
                    }
                    while ((line = errReader.readLine()) != null) {
                        appendLog("[ERR] " + line);
                    }

                    int exitCode = process.waitFor();
                    appendLog("\n> Завершено (код: " + exitCode + ")");
                } catch (Exception e) {
                    appendLog("\n[Ошибка]: " + e.getMessage());
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnPing.setEnabled(true);
                            btnPing.setText("Пинговать");
                        }
                    });
                }
            }
        }).start();
    }

    private void appendLog(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvOutput.append(text + "\n");
                scrollLog.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollLog.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });
    }
}

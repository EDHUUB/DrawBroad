package com.xpw.drawbroad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xpw.drawbroad.view.DrawView;

public class MainActivity extends AppCompatActivity {

    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawView = findViewById(R.id.surface);
    }
}
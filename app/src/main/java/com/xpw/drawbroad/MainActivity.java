package com.xpw.drawbroad;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xpw.drawbroad.view.DrawView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private DrawView drawView;
    private Button revokeButton;
    private Button forwardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawView = findViewById(R.id.surface);

        revokeButton = findViewById(R.id.revoke_btn);
        forwardButton = findViewById(R.id.forward_btn);

        revokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.revoke();
            }
        });
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.forwardDoublePath();
            }
        });
    }
}
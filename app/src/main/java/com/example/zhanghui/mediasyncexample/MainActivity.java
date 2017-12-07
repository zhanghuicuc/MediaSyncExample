package com.example.zhanghui.mediasyncexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button mPlayButton;
    private EditText mUrlEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUrlEditText = (EditText) findViewById(R.id.input_url_editText);
        mPlayButton = (Button) findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String fileUrl = mUrlEditText.getText().toString().trim();
                fileUrl = "/sdcard/H264_EAC3_24fps_1920x1080.mp4";
                if (fileUrl == null) {
                    Toast.makeText(MainActivity.this, "file url wrong", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}

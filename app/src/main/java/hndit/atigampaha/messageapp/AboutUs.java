package hndit.atigampaha.messageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    ImageButton backBtn;
    TextView info,infoUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        backBtn = findViewById(R.id.btnBackAbout);
        info = findViewById(R.id.info);
        infoUs = findViewById(R.id.infous);

        info.setText("Developed by Mobile Dev Team:");
        infoUs.setText(" \u2605 Avishka Deshan \n \u2605 Harsha Mithum \n \u2605 Kasun Chathuranga");


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutUs.this, ChatActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
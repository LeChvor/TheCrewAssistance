package com.example.thecrewassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int playersCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    public void startAssistActivity() {
        Intent intent = new Intent(this, AssistActivity.class);
        intent.putExtra("count", playersCount);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        RadioGroup rg = findViewById(R.id.rg);
        RadioButton rb = findViewById(rg.getCheckedRadioButtonId());
        playersCount = Integer.parseInt(rb.getText().toString());

        startAssistActivity();
    }
}
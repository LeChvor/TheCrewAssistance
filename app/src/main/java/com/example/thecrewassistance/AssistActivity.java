package com.example.thecrewassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class AssistActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assist);

        int count = getIntent().getIntExtra("count", 4);

        LinearLayout viewLayout = findViewById(R.id.view_layout);
        RadioGroup playersRadio = findViewById(R.id.player);
        for (int i = playersRadio.getChildCount() - count; i > 0; i--)
            playersRadio.removeViewAt(playersRadio.getChildCount() - 1);

        for (int i = 0; i < count; i++) {

            RelativeLayout player = new RelativeLayout(this);

            TextView tv = new TextView(this);
            if (count == 3 && i == 2) tv.setText("" + (40 / count + 1));
            else tv.setText("" + 40 / count);
            tv.setId(1000 + i);
            tv.setTextSize(30);

            RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            tvParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            tvParams.alignWithParent = true;
            tvParams.leftMargin = 30;

            player.addView(tv, tvParams);

            for (int j = 0; j < 10; j++) {

                TextView numMark = new TextView(this);
                if (j < 9) numMark.setText(Integer.toString(j + 1));
                else numMark.setText("R");

                RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                textParams.alignWithParent = true;
                textParams.leftMargin = 140 + j * 80;
                textParams.topMargin = 20;

                player.addView(numMark, textParams);

                for (int k = 0; k < 4; k++) {
                    CheckBox cardMark = new CheckBox(this);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.alignWithParent = true;
                    params.leftMargin = 110 + j * 80;
                    params.topMargin = 60 + k * 60;

                    cardMark.setClickable(false);
                    cardMark.setId(i * 100 + j * 10 + k);
                    //System.out.println(cardMark.getId());

                    int color = Color.BLACK;
                    if (j < 9) {
                        switch (k) {
                            case 0:
                                color = Color.GREEN;
                                break;
                            case 1:
                                color = Color.MAGENTA;
                                break;
                            case 2:
                                color = Color.BLUE;
                                break;
                            case 3:
                                color = Color.YELLOW;
                                break;
                        }
                    }

                    int[][] states = new int[][] {
                            new int[] { android.R.attr.state_enabled}, // enabled
                            new int[] {-android.R.attr.state_enabled}, // disabled
                    };

                    int[] colors = new int[] {
                            color,
                            color,
                    };

                    cardMark.setButtonTintList(new ColorStateList(states, colors));

                    player.addView(cardMark, params);
                }
            }

            viewLayout.addView(player);
        }

        RadioGroup colors = findViewById(R.id.color);
        colors.setOnCheckedChangeListener(this);

        SeekBar number = findViewById(R.id.number);
        number.setOnSeekBarChangeListener(this);

        Button add = findViewById(R.id.add);
        add.setOnClickListener(this);

        CheckBox max = findViewById(R.id.max);
        max.setOnCheckedChangeListener(this);

        CheckBox min = findViewById(R.id.min);
        min.setOnCheckedChangeListener(this);

        CheckBox only = findViewById(R.id.only);
        only.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        SeekBar number = findViewById(R.id.number);
        CheckBox max = findViewById(R.id.max);
        CheckBox min = findViewById(R.id.min);
        CheckBox only = findViewById(R.id.only);
        if (i == R.id.rocket) {
            number.setMax(3);
            max.setChecked(false);
            min.setChecked(false);
            only.setChecked(false);
        } else {
            number.setMax(8);
        }
        System.out.println(number.getMax());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        TextView num_view = findViewById(R.id.num_view);
        num_view.setText("" + (i + 1));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View view) {
        RadioGroup playerGroup = findViewById(R.id.player);
        RadioButton playerButton = findViewById(playerGroup.getCheckedRadioButtonId());
        int playerNumber = Integer.parseInt(playerButton.getText().toString()) - 1;

        SeekBar number = findViewById(R.id.number);
        int cardNumber = number.getProgress() - 1;

        RadioGroup colorGroup = findViewById(R.id.color);
        int colorNumber = 0;
        switch (colorGroup.getCheckedRadioButtonId()) {
            case R.id.green:
                colorNumber = 0;
                break;
            case R.id.magenta:
                colorNumber = 1;
                break;
            case R.id.blue:
                colorNumber = 2;
                break;
            case R.id.yellow:
                colorNumber = 3;
                break;
            case R.id.rocket:
                colorNumber = cardNumber;
                cardNumber = 9;
                break;
        }

        int id = playerNumber * 100 + cardNumber * 10 + colorNumber;
        CheckBox card = findViewById(id);
        if (card.getVisibility() == View.INVISIBLE) {
            Toast deleted = Toast.makeText(this, "A player cannot have this card", Toast.LENGTH_LONG);
            deleted.show();
        } else if (card.isChecked() == true) {
            Toast deleted = Toast.makeText(this, "This player has already used this card", Toast.LENGTH_LONG);
            deleted.show();
        } else {
            card.setChecked(true);
            TextView playerCounter = findViewById(1000 + playerNumber);
            playerCounter.setText("" + (Integer.parseInt(playerCounter.getText().toString()) - 1) );

            for (int i = id + 100; i < 400; i += 100) {
                //System.out.println(i);
                CheckBox anotherPlayerCard = findViewById(i);
                anotherPlayerCard.setVisibility(View.INVISIBLE);
            }

            for (int i = id - 100; i > 0; i -= 100) {
                //System.out.println(i);
                CheckBox anotherPlayerCard = findViewById(i);
                anotherPlayerCard.setVisibility(View.INVISIBLE);
            }

            CheckBox min = findViewById(R.id.min);
            CheckBox max = findViewById(R.id.max);
            CheckBox only = findViewById(R.id.only);

            if (min.isChecked() && id % 100 < 90) {
                int i = id - 10;
                CheckBox anotherCard = findViewById(i);
                while (i % 100 < 90 && i >= 0) {
                    anotherCard.setVisibility(View.INVISIBLE);
                    i -= 10;
                    anotherCard = findViewById(i);
                }
            }

            if (max.isChecked() && id % 100 < 90) {
                int i = id + 10;
                CheckBox anotherCard = findViewById(i);
                while (i % 100 < 90) {
                    anotherCard.setVisibility(View.INVISIBLE);
                    i += 10;
                    anotherCard = findViewById(i);
                }
            }

            if (only.isChecked() && id % 100 < 90) {
                int i = id + 10;
                CheckBox anotherCard = findViewById(i);
                while (i % 100 < 90) {
                    System.out.println(id);
                    anotherCard.setVisibility(View.INVISIBLE);
                    i += 10;
                    anotherCard = findViewById(i);
                }

                i = id - 10;
                anotherCard = findViewById(i);
                while (i % 100 < 90 && i >= 0) {
                    anotherCard.setVisibility(View.INVISIBLE);
                    i -= 10;
                    anotherCard = findViewById(i);
                }
            }
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            switch (compoundButton.getId()) {
                case R.id.max: {
                    ((CheckBox) findViewById(R.id.min)).setChecked(false);
                    ((CheckBox) findViewById(R.id.only)).setChecked(false);
                    break;
                }
                case R.id.min: {
                    ((CheckBox) findViewById(R.id.max)).setChecked(false);
                    ((CheckBox) findViewById(R.id.only)).setChecked(false);
                    break;
                }
                case R.id.only: {
                    ((CheckBox) findViewById(R.id.min)).setChecked(false);
                    ((CheckBox) findViewById(R.id.max)).setChecked(false);
                    break;
                }
            }
        }
    }
}
package com.view.middle.nulpproject;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private boolean isShowing;
    private int speed;//speed of play

    int[] letterId;
    private static final int REQUEST_CODE_SPEECH = 1000;
    EditText textView;
    TextView textViewSpeed;
    Button rec;
    Button display;
    String currentText;
    ImageView imageView;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.editText);
        rec = findViewById(R.id.button);
        display = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView);
        seekBar = findViewById(R.id.seekBar);
        textViewSpeed = findViewById(R.id.textView2);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                speed = seekBar.getProgress();
                textViewSpeed.setText(String.valueOf((speed * 1.0) / 1000));
                if (speed < 400) {
                    speed = 400;
                seekBar.setProgress(400);
                }

            }
        });

        fill();
        textViewSpeed.setText("0.4");

        seekBar.setProgress(400);
        speed = 400;


        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentText = textView.getText().toString().trim().toLowerCase();
                goClicked();
            }
        });
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

    }

    void fill() {
        letterId = new int[]{R.drawable.let1, R.drawable.let2, R.drawable.let3, R.drawable.let4, R.drawable.let4, R.drawable.let5, R.drawable.let6, R.drawable.let7, R.drawable.let8, R.drawable.let9, R.drawable.let10, R.drawable.let11, R.drawable.let12, R.drawable.let13, R.drawable.let14, R.drawable.let15, R.drawable.let16, R.drawable.let17, R.drawable.let18, R.drawable.let19, R.drawable.let20, R.drawable.let20, R.drawable.let21, R.drawable.let22, R.drawable.let23, R.drawable.let24, R.drawable.let25, R.drawable.let26, R.drawable.let27, R.drawable.let28, R.drawable.let29, R.drawable.let30, R.drawable.let31, R.drawable.back};
    }

    void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi, let`s speak");
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    void goClicked() {
        if (!isShowing) {

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                int currentCharIndex = 0;
                int size = currentText.length();
                char[] string = currentText.toCharArray();

                @Override
                public void run() {
                    // As timer is not a Main/UI thread need to do all UI task on runOnUiThread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (currentCharIndex < size) {
                                isShowing = true;
//                        textView.setText(String.valueOf(findIndex(string[currentCharIndex++])));
                                imageView.setImageResource(letterId[findIndex(string[currentCharIndex++])]);
                            } else {
                                cancel();
                                isShowing = false;
                            }

                        }
                    });
                }
            }, 0, speed);
            /////////
        }
    }

    int findIndex(char symbol) {

        char[] alphabet = new char[]{'а', 'б', 'в', 'г', 'г', 'д', 'е', 'є', 'ж', 'з', 'и', 'і', 'ї', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ю', 'я'};
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == symbol) {
                return i;
            }
        }
        return 33;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SPEECH:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    currentText = result.get(0).trim().toLowerCase();
                    textView.setText(currentText);
                }
                break;
        }
    }


}


package com.example.laurence.braintrain;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class TimerActivity extends AppCompatActivity {
    private TextView scoreText, timerText;
    private Button buttons[];
    private int goedeAntwoord, foutAntwoord, aantalBeurten, aantalGoed;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        scoreText = (TextView) findViewById(R.id.ScoreTextView);
        timerText = (TextView) findViewById(R.id.TimerTextView);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        aantalBeurten = 0;
        aantalGoed = 0;
        buttons = new Button[4];
        for(int i = 0; i < buttons.length; i++){
            buttons[i] = (Button) gridLayout.getChildAt(i);
        }
        startTimer();
        maakSom();
    }

    private void startTimer(){
        timer = new CountDownTimer(30000 + 100, 1000){

            @Override
            public void onTick(long l) {
                int seconden = (int) l/1000;
                timerText.setText(String.valueOf(seconden)+"S");
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("score", aantalGoed);
                startActivity(intent);
                finish();
            }
        };
        timer.start();
    }

    private void maakSom(){
        Random randomizer = new Random();
        int getal1 = randomizer.nextInt(50)+1;
        int getal2 = randomizer.nextInt(50)+1;
        TextView somText = (TextView) findViewById(R.id.SomTextView);
        somText.setText(String.valueOf(getal1)+" + "+String.valueOf(getal2));
        goedeAntwoord = getal1+getal2;

        int goedeAntwoordKnop = randomizer.nextInt(4);
        buttons[goedeAntwoordKnop].setText(String.valueOf(goedeAntwoord));
        for(int i = 0; i < buttons.length; i++){
            if(i != goedeAntwoordKnop){
                foutAntwoord = randomizer.nextInt(100)+1;
                while(foutAntwoord == goedeAntwoord){
                    foutAntwoord = randomizer.nextInt(100)+1;
                }
                buttons[i].setText(String.valueOf(foutAntwoord));
            }
        }
    }

    public void checkUitkomst(View v){
        aantalBeurten++;
        Button button = (Button) v;
        int antwoord = Integer.parseInt(button.getText().toString());
        if(antwoord == goedeAntwoord){
            aantalGoed++;
        }
        scoreText.setText(aantalGoed+"/"+aantalBeurten);
        maakSom();
    }


    @Override
    public void onBackPressed() {
        timer.cancel();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
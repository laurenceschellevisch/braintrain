package com.example.laurence.braintrain;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int highscore;
    TextView highscoreText, newHighscore;
    boolean doubleBackToExitPressedOnce;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        doubleBackToExitPressedOnce = false;

        prefs = this.getPreferences(Context.MODE_PRIVATE);
        highscore = prefs.getInt("highscore", 0);

        highscoreText = (TextView) findViewById(R.id.HighscoreTextView);
        newHighscore = (TextView) findViewById(R.id.newHighscoreView);
        newHighscore.setText("");

        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);

        if(score > highscore) {
            highscore = score;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", highscore);
            editor.commit();
            newHighscore.setText("Je nieuwe highscore = "+String.valueOf(highscore));
        }else if(score <= highscore && score > 0){
            newHighscore.setText("Je score = "+String.valueOf(score));
        }
        highscoreText.setText("High score: " + highscore);
    }

    public void start(View v){
        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);
        finish();
    }




    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.reset_highscore) {
            highscore = 0;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", highscore);
            editor.commit();
            highscoreText.setText("High score: 0");
            newHighscore.setText("");
        }

        return super.onOptionsItemSelected(item);
    }
}
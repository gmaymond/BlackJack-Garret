package com.example.garret_m_aymond.garretsblackjack;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Garret-M-Aymond on 1/20/16.
 */
public class Scoreboard extends AppCompatActivity {

    private ArrayList<String[]> AllScores = new ArrayList<String[]>();
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        listview = (ListView) findViewById(R.id.listView);
        getDbScores();

        Log.i("Scoreboard", "number of Scores : " + AllScores.size());

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        android.support.v7.app.ActionBar.LayoutParams params = new android.support.v7.app.ActionBar.LayoutParams(//Center the textview in the ActionBar !
                android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT,
                android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        View customNav = LayoutInflater.from(this).inflate(R.layout.actionbar_format, null); // layout which contains Title
        TextView TitleTextView = (TextView) customNav.findViewById(R.id.title_text);
        TitleTextView.setText("ScoreBoard");
        getSupportActionBar().setCustomView(customNav, params);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onStart(){
        super.onStart();
        if (!AllScores.isEmpty()) {
            ScoreListAdapter adapter = new ScoreListAdapter(AllScores, Scoreboard.this, Scoreboard.this);
            listview.setAdapter(adapter);
        }
    }

    private void getDbScores(){
        SQLiteDatabase db =ScoreDbHelper.openDatabase(Scoreboard.this,"Scoreboard","getDbScores");
        synchronized (db) {
            Cursor c = db.rawQuery("SELECT * FROM scoreboard", null);
            Log.i("Scoreboard", "Database size : "+c.getCount());
            if (c != null && c.moveToFirst()) {
                String[] Score = new String[3];
                Log.i("Scoreboard", "Winner : "+c.getString(c.getColumnIndex("winner")) +" Dealer Score : "+c.getString(c.getColumnIndex("dealerscore")) + " Player Score : "+c.getString(c.getColumnIndex("playerscore")));
                Score[0] = c.getString(c.getColumnIndex("winner"));
                Score[1] = c.getString(c.getColumnIndex("dealerscore"));
                Score[2] = c.getString(c.getColumnIndex("playerscore"));

                AllScores.add(Score);

                while (c.moveToNext()) {
                   Score = new String[3];
                    Log.i("Scoreboard", "Winner : "+c.getString(c.getColumnIndex("winner")) +" Dealer Score : "+c.getString(c.getColumnIndex("dealerscore")) + " Player Score : "+c.getString(c.getColumnIndex("playerscore")));

                    Score[0] = c.getString(c.getColumnIndex("winner"));
                    Score[1] = c.getString(c.getColumnIndex("dealerscore"));
                    Score[2] = c.getString(c.getColumnIndex("playerscore"));

                    AllScores.add(Score);
                }
            }
        }
    }

    private void clearScoreboard(){
        SQLiteDatabase db =ScoreDbHelper.openDatabase(Scoreboard.this,"Scoreboard","clearScoreboard");
        db.delete("scoreboard", null, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scoreboard_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Back button pressed
                this.finish();
                return true;
            case R.id.clear_scoreboard:
                clearScoreboard();
                Intent intent = new Intent(Scoreboard.this,
                        Scoreboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.return_to_game:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}



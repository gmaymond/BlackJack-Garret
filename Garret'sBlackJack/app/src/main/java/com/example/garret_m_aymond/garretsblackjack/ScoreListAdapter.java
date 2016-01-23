package com.example.garret_m_aymond.garretsblackjack;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreListAdapter extends BaseAdapter implements ListAdapter {
    private static ArrayList<String[]> ScoreRecords = new ArrayList<String[]>();
    private static Context context;
    private String[] ScoreRecord = null;
    private static Activity activity;

    private TextView players_score;
    private TextView dealers_score;
    private TextView winner;

    public ScoreListAdapter(ArrayList<String[]> ResultsArray,
                         Context baseContext, Activity activity) {
        this.ScoreRecords = ResultsArray;
        this.context = baseContext;
        this.activity = activity;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.scoreboard_item, null);

            ScoreRecord = ScoreRecords.get(position);

            players_score = (TextView) convertView.findViewById(R.id.score_board_player_results);
            dealers_score = (TextView) convertView.findViewById(R.id.score_board_dealer_results);
            winner = (TextView) convertView.findViewById(R.id.score_board_winner_results);

            winner.setText(ScoreRecord[0]);
            dealers_score.setText(ScoreRecord[1]);
            players_score.setText(ScoreRecord[2]);

        if (ScoreRecord[0].equals("DEALER")){
            players_score.setTextColor(Color.RED);
            dealers_score.setTextColor(Color.RED);
            winner.setTextColor(Color.RED);
        } else if (ScoreRecord[0].equals("YOU")) {
            players_score.setTextColor(Color.parseColor("#126204"));
            dealers_score.setTextColor(Color.parseColor("#126204"));
            winner.setTextColor(Color.parseColor("#126204"));
        }

        return convertView;
    }


    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return ScoreRecords.size();
    }

    @Override
    public Object getItem(int pos) {
        return ScoreRecords.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }
} 

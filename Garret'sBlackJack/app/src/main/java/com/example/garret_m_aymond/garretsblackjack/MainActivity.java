package com.example.garret_m_aymond.garretsblackjack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button Deal;
    Button Hit;
    Button Stay;
    ImageView firstCard;
    ImageView secondCard;
    ImageView thirdCard;
    ImageView fourthCard;
    ImageView fifthCard;

    ImageView dealerfirstCard;
    ImageView dealersecondCard;
    ImageView dealerthirdCard;
    ImageView dealerfourthCard;
    ImageView dealerfifthCard;

    ArrayList<String[]> PlayersCards = new ArrayList<String[]>();
    ArrayList<String[]> DealersCards = new ArrayList<String[]>();
    public static Context appContext;
    private static Boolean PlayersTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        android.support.v7.app.ActionBar.LayoutParams params = new android.support.v7.app.ActionBar.LayoutParams(//Center the textview in the ActionBar !
                android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT,
                android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        View customNav = LayoutInflater.from(this).inflate(R.layout.actionbar_format, null); // layout which contains Title
        TextView TitleTextView = (TextView) customNav.findViewById(R.id.title_text);
        TitleTextView.setText("Black Jack");
        getSupportActionBar().setCustomView(customNav, params);

        Deal = (Button) findViewById(R.id.dealBtn);
        Hit = (Button) findViewById(R.id.hitBtn);
        Stay = (Button) findViewById(R.id.stayBtn);
        firstCard = (ImageView) findViewById(R.id.firstcardImg);
        secondCard = (ImageView) findViewById(R.id.secondcardImg);
        thirdCard = (ImageView) findViewById(R.id.thirdcardImg);
        fourthCard = (ImageView) findViewById(R.id.forthcardImg);
        fifthCard = (ImageView) findViewById(R.id.fifthcardImg);
        dealerfirstCard = (ImageView) findViewById(R.id.dealerfirstcardImg);
        dealersecondCard = (ImageView) findViewById(R.id.dealersecondcardImg);
        dealerthirdCard = (ImageView) findViewById(R.id.dealerthirdcardImg);
        dealerfourthCard = (ImageView) findViewById(R.id.dealerforthcardImg);
        dealerfifthCard = (ImageView) findViewById(R.id.dealerfifthcardImg);
        appContext = getApplicationContext();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Deck.isEmpty()) {
                    Deck.CreateDeck();
                    Deck.SuffleDeck();
                }
                Deal.setVisibility(View.GONE);
                Hit.setVisibility(View.VISIBLE);
                Stay.setVisibility(View.VISIBLE);
                drawNCards(4);
            }
        });

        Hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayersTurn = true;
                drawNCards(1);
                Integer PlayersScore = calculateScore(PlayersCards, true);
                Integer DealerScore = calculateScore(DealersCards, false);
                if (DealerScore <= 15) {
                    PlayersTurn = false;
                    drawNCards(1);
                    DealerScore = calculateScore(DealersCards, false);
                }
                if (DealerScore > 21 || PlayersScore > 21) {
                    DetermineWinner(DealerScore, PlayersScore);
                }
            }
        });

        Stay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayersTurn = false;
                if (calculateScore(DealersCards, false) <= 15) {
                    do {
                        drawNCards(1);
                    } while (calculateScore(DealersCards, false) <= 15);
                }
                int playerScore = calculateScore(PlayersCards, true);
                int dealerScore = calculateScore(DealersCards, false);
                DetermineWinner(dealerScore, playerScore);
            }
        });

    }

    public static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public void drawNCards(int cardsToDraw) {
        Log.i("GameBoard", "DeckSize : " + Deck.getSize());
        if (cardsToDraw <= Deck.getSize()) {
            for (int i = 0; i < cardsToDraw; i++) {
            String[] DrawnCard = Deck.DrawCard();
            int resourceId;
            if (isNumeric(DrawnCard[0])) {
                Log.i("GameBoard", "Player Drew Card :" + "card_" + DrawnCard[0] + "_of_" + DrawnCard[1]);
                resourceId = appContext.getResources().getIdentifier("card_" + DrawnCard[0] + "_of_" + DrawnCard[1], "drawable", getPackageName());
                //firstCard.setBackgroundResource(resourceId);
            } else {
                Log.i("GameBoard", "Player Drew Card :" + DrawnCard[0] + "_of_" + DrawnCard[1]);
                resourceId = appContext.getResources().getIdentifier(DrawnCard[0] + "_of_" + DrawnCard[1], "drawable", getPackageName());
                //firstCard.setBackgroundResource(resourceId);
            }

                if (PlayersTurn) {
                    PlayersCards.add(DrawnCard);
                    
                    Log.i("GameBoard", "Player's Cards size :" + PlayersCards.size());
                    switch (PlayersCards.size()) {
                        case 1:
                            firstCard.setBackgroundResource(resourceId);
                            break;
                        case 2:
                            secondCard.setBackgroundResource(resourceId);
                            break;
                        case 3:
                            thirdCard.setVisibility(View.VISIBLE);
                            thirdCard.setBackgroundResource(resourceId);
                            break;
                        case 4:
                            fourthCard.setVisibility(View.VISIBLE);
                            fourthCard.setBackgroundResource(resourceId);
                            break;
                        case 5:
                            fifthCard.setVisibility(View.VISIBLE);
                            fifthCard.setBackgroundResource(resourceId);
                            Hit.setEnabled(false);
                            break;
                    }
                    PlayersTurn = false;
                } else {
                    Log.i("GameBoard", "Player's Cards size :" + PlayersCards.size());
                    DealersCards.add(DrawnCard);
                    switch (DealersCards.size()) {
                        case 1:
                            resourceId = appContext.getResources().getIdentifier("card_back", "drawable", getPackageName());
                            dealerfirstCard.setBackgroundResource(resourceId);
                            break;
                        case 2:
                            dealersecondCard.setBackgroundResource(resourceId);
                            break;
                        case 3:
                            dealerthirdCard.setVisibility(View.VISIBLE);
                            dealerthirdCard.setBackgroundResource(resourceId);
                            break;
                        case 4:
                            dealerfourthCard.setVisibility(View.VISIBLE);
                            dealerfourthCard.setBackgroundResource(resourceId);
                            break;
                        case 5:
                            dealerfifthCard.setVisibility(View.VISIBLE);
                            dealerfifthCard.setBackgroundResource(resourceId);
                            break;
                    }
                    PlayersTurn = true;
                }
            }
        } else {
            //finished deck, recreating deck
            Deck.CreateDeck();
            Deck.SuffleDeck();
            Deck.RemoveCardsFromDeck(DealersCards);
            Deck.RemoveCardsFromDeck(PlayersCards);
            drawNCards(cardsToDraw);
        }
    }

    private int calculateScore(ArrayList<String[]> Player, Boolean PlayersTurn) {
        int PlayerScore = 0;
        int AceCount = 0;
        for (int i = 0; i < Player.size(); i++) {
            String[] Card = Player.get(i);
            if (Card[0].equals("ace")) {
                AceCount = AceCount + 1;
            }
            PlayerScore = PlayerScore + Integer.parseInt(Card[2]);
            if (PlayerScore > 21 && AceCount > 0) {
                do {
                    PlayerScore = PlayerScore - 10;
                    AceCount = AceCount - 1;
                } while (AceCount > 0 && PlayerScore > 21);
            }
        }
        if (PlayersTurn) {
            Log.i("GameBoard", "Player's Score: " + PlayerScore);
        } else {
            Log.i("GameBoard", "Dealer's Score: " + PlayerScore);
        }

        return PlayerScore;
    }

    private void DetermineWinner(Integer DealerScore, Integer PlayerScore) {

        if (PlayerScore > 21) {
            Log.i("GameBoard", "Player's Score BUST: " + PlayerScore);
            EndingResults("You Lost!", "You Busted with a score of " + PlayerScore);
            StoreEndingResults(PlayerScore, DealerScore, "DEALER");
        } else if (DealerScore > 21) {
            Log.i("GameBoard", "Dealers's Score BUST: " + PlayerScore);
            EndingResults("You Won!", "The dealer Busted with a score of " + DealerScore);
            StoreEndingResults(PlayerScore, DealerScore, "YOU");
        } else if (21 - PlayerScore == 21 - DealerScore) {
            EndingResults("Tie Game!", "Both you and the dealer had a score of " + PlayerScore);
            StoreEndingResults(PlayerScore, PlayerScore, "Tie");
        } else if (21 - PlayerScore < 21 - DealerScore) {
            EndingResults("You Won!", "You won with a score of " + PlayerScore);
            StoreEndingResults(PlayerScore, DealerScore, "YOU");
        } else if (DealerScore < 22 && (21 - DealerScore < 21 - PlayerScore)) {
            EndingResults("You Lost!", "You lost with a score of " + PlayerScore);
            StoreEndingResults(PlayerScore, DealerScore, "DEALER");
        }
        Hit.setEnabled(true);
    }

    private void EndingResults(String Title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT).create();
        alertDialog.setTitle(Title);
        alertDialog.setMessage(message);
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "New Game",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Clearboard();
                    }
                });
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "View Score History",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Clearboard();
                        Intent intent = new Intent(MainActivity.this,
                                Scoreboard.class);
                        startActivity(intent);
                    }
                });
        alertDialog.show();
    }

    private void StoreEndingResults(Integer PlayersScore, Integer DealerScore, String Winner) {
        SQLiteDatabase db = ScoreDbHelper.openDatabase(MainActivity.this, "MainActivity", "StoreEndingResults");

        ContentValues values = new ContentValues();
        values.put("winner", Winner);
        values.put("dealerscore", DealerScore);
        values.put("playerscore", PlayersScore);

        synchronized (db) {
            Log.i("GameBoard", "Adding to DB");
            db.insertWithOnConflict(
                    "scoreboard",
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_IGNORE
            );
        }
    }

    private void Clearboard() {
        DealersCards.clear();
        PlayersCards.clear();
        dealerthirdCard.setVisibility(View.GONE);
        dealerfourthCard.setVisibility(View.GONE);
        dealerfifthCard.setVisibility(View.GONE);
        dealerfirstCard.setBackgroundResource(R.color.emptyCardSlotGreen);
        dealersecondCard.setBackgroundResource(R.color.emptyCardSlotGreen);
        thirdCard.setVisibility(View.GONE);
        fourthCard.setVisibility(View.GONE);
        fifthCard.setVisibility(View.GONE);
        firstCard.setBackgroundResource(R.color.emptyCardSlotGreen);
        secondCard.setBackgroundResource(R.color.emptyCardSlotGreen);
        Hit.setVisibility(View.GONE);
        Stay.setVisibility(View.GONE);
        Deal.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                Clearboard();
                break;
            case R.id.score_board:
                Intent intent = new Intent(MainActivity.this,
                        Scoreboard.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}

package com.example.garret_m_aymond.garretsblackjack;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Garret-M-Aymond on 1/17/16.
 */
public class Deck {

    private static ArrayList<String[]> Deck = new ArrayList<String[]>();
    private static  String[] Suits = {"hearts","clubs","spades","diamonds"};


    public static void CreateDeck() {
        for (int i = 0;i<Suits.length;i++){
            CreateCards(Suits[i]);
        }
    }
    private static void CreateCards(String Suit){
        for (int i = 1; i <= 13; i++) {
            String[] tempCard = new String[3];
            switch (i) {
                case 1:
                    tempCard[0]="ace";
                    tempCard[1]=Suit;
                    tempCard[2]="11";
                    Deck.add(tempCard);
                    break;
                case 11:
                    tempCard[0]="jack";
                    tempCard[1]=Suit;
                    tempCard[2]="10";
                    Deck.add(tempCard);
                    break;
                case 12:
                    tempCard[0]="queen";
                    tempCard[1]=Suit;
                    tempCard[2]="10";
                    Deck.add(tempCard);
                    break;
                case 13:
                    tempCard[0]="king";
                    tempCard[1]=Suit;
                    tempCard[2]="10";
                    Deck.add(tempCard);
                    break;
                default:
                    tempCard[0]=String.valueOf(i);
                    tempCard[1]=Suit;
                    tempCard[2]=String.valueOf(i);
                    Deck.add(tempCard);
            }
        }
        Log.i("Deck", Deck.toString());
        Log.i("Deck", "Size :"+Deck.size());
    }
    public static void SuffleDeck() {
        Collections.shuffle(Deck);
    }

    public static String[] DrawCard() {
        String[] cardChosen = Deck.get(0);
        Deck.remove(0);
        return cardChosen;
    }

    public static boolean isEmpty(){
        return Deck.isEmpty();
    }

    public static int getSize(){
        return Deck.size();
    }

    public static void RemoveCardsFromDeck(ArrayList<String[]> CardsToRemove) {
        for(int i = 0; i < CardsToRemove.size(); i++){
            String[] Card = CardsToRemove.get(1);
            Deck.remove(Card);

            Log.i("Deck", "Removing : "+Card[0]+" of "+Card[1]);
        }
    }
}

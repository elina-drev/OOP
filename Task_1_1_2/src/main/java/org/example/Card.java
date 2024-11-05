package org.example;
//import java.util.Objects;
//Card: Класс, представляющий карту с мастью, номиналом и очковым значением
public class Card {
    private String suit; // Масть
    private final String rank; // Ранг
    private final int value;   // Значение карты

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }
//
    public int getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return rank + " " + suit + " (" + value + ")";
    }
}
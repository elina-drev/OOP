package org.example;

//Deck: Класс, представляющий колоду карт с методом для раздачи карт и перемешивания колоды
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Пик", "Червей", "Бубен", "Треф"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Валет", "Дама", "Король", "Туз"};

        // Заполнение колоды карт
        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                int value = (i >= 9) ? 10 : i + 2; // Значения карт: 2-10 - свои, Валет, Дама, Король - 10, Туз - 11
                if (ranks[i].equals("Туз")) {
                    value = 11; // Туз по умолчанию считается за 11
                }
                cards.add(new Card(suit, ranks[i], value));
            }
        }

        Collections.shuffle(cards); // Перемешивание колоды
    }

    public Card dealCard() {
        return cards.remove(0); // Раздача карты
    }
}
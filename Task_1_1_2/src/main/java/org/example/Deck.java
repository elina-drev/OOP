package org.example;

//Deck: Класс, представляющий колоду карт с методом для раздачи карт и перемешивания колоды
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Spades", "Hearts", "Diamonds ", "Clubs"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        // Заполнение колоды карт
        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                int value = (i >= 9) ? 10 : i + 2; // Значения карт: 2-10 - свои, Валет, Дама, Король - 10, Туз - 11
                if (ranks[i].equals("Ace")) {
                    value = 11; // Туз по умолчанию считается за 11
                }
                cards.add(new Card(suit, ranks[i], value));
            }
        }

        Collections.shuffle(cards); // Перемешивание колоды
    }

    public Card dealCard() {
        return cards.removeFirst(); // Раздача карты
    }

    public boolean hasEnoughCards() {
        return cards.size() >= 4; // Достаточно карт для нового раунда
    }

    // Getter для списка карт (для тестов)
    public List<Card> getCards() {
        return cards;
    }

}
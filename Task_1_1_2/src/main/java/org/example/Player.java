package org.example;

import java.util.List;
import java.util.ArrayList;

public class Player {
    private final String name;              // Имя игрока
    private final List<Card> hand;          // Рука игрока

    public Player(String name) {
        this.name = name;
        // Дилер или игрок
        hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card); // Добавление карты в руку
    }

    public int getHandValue() {
        int value = 0;
        int acesCount = 0;

        // Подсчет значений карт
        for (Card card : hand) {
            value += card.getValue();
            if (card.getRank().equals("Ace")) {
                acesCount++;
            }
        }

        // Обработка тузов как 1, если игрок перебрал 21
        while (value > 21 && acesCount > 0) {
            value -= 10; // Уменьшаем значение туза с 11 до 1
            acesCount--;
        }

        return value;
    }

    public boolean isBusted() {
        return getHandValue() > 21; // Проверка на перебор
    }

    public boolean hasBlackjack() {
        return hand.size() == 2 && getHandValue() == 21; // Проверка на блэкджек
    }

    public List<Card> getHand() {
        return hand; // Получение руки
    }

    public Card getHiddenCard() {
        return hand.get(1); // Получение закрытой карты (вторая карта)
    }

    public void displayHand() {
        System.out.print(name + " cards: " + hand + " => " + getHandValue());//использует toString
        System.out.println();
    }
    public void clearHand() {
        hand.clear();  // Очищаем список карт
    }

}

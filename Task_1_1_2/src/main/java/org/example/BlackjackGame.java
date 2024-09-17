package org.example;
import java.util.Scanner;

public class BlackjackGame {
    private Deck deck;
    private Player player;
    private Player dealer;
    private int playerWins;
    private int dealerWins;

    public BlackjackGame() {
        deck = new Deck();
        player = new Player("Игрок", false);
        dealer = new Player("Дилер", true);
        playerWins = 0;
        dealerWins = 0;
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean continuePlaying = true;

        while (continuePlaying) {
            System.out.println("Добро пожаловать в Блэкджек!");
            System.out.println("Раунд " + (playerWins + dealerWins + 1));
            dealInitialCards();
            if (checkInitialBlackjack()) {
                continuePlaying = askToContinue(scanner);
                continue;
            }

            playerTurn(scanner);
            if (!player.isBusted()) {
                dealerTurn();
            }
            determineWinner();
            continuePlaying = askToContinue(scanner);
        }
        scanner.close();
    }

    private void dealInitialCards() {
        System.out.println("Дилер раздал карты");
        for (int i = 0; i < 2; i++) {
            player.addCard(deck.dealCard());
            dealer.addCard(deck.dealCard());
        }
        displayHands(true);
    }

    private boolean checkInitialBlackjack() {
        if (player.hasBlackjack()) {
            System.out.println("Игрок получил Блэкджек! Игрок выигрывает раунд!");
            playerWins++;
            return true;
        }
        if (dealer.hasBlackjack()) {
            System.out.println("Дилер получил Блэкджек! Дилер выигрывает раунд!");
            dealerWins++;
            return true;
        }
        return false;
    }

    private void playerTurn(Scanner scanner) {
        while (true) {
            System.out.println("Ваш ход");
            System.out.println("-------");
            System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться (не берите больше карт)...");
            int action = scanner.nextInt();

            if (action == 1) {
                Card newCard = deck.dealCard();
                player.addCard(newCard);
                System.out.println("Вы открыли карту " + newCard);
                displayHands(false);
                if (player.isBusted()) {
                    System.out.println("Игрок перебрал! Дилер выигрывает раунд!");
                    dealerWins++;
                    return;
                }
            } else if (action == 0) {
                System.out.println("Вы решили остановиться. Дальше ходит дилер.");
                break; // Игрок заканчивает свой ход
            } else {
                System.out.println("Ошибочный ввод. Пожалуйста, введите “1” или “0”.");
            }
        }
    }

    private void dealerTurn() {
        System.out.println("Ход дилера");
        System.out.println("-------");
        System.out.println("Дилер открывает закрытую карту " + dealer.getHiddenCard());

        // Дилер открывает одну еще карту
        dealer.addCard(deck.dealCard());
        displayHands(false);

        while (dealer.getHandValue() < 17) {
            Card newCard = deck.dealCard();
            dealer.addCard(newCard);
            System.out.println("Дилер открывает карту " + newCard);
            displayHands(false);
        }
    }

    private void determineWinner() {
        int playerValue = player.getHandValue();
        int dealerValue = dealer.getHandValue();
        System.out.println("Ваши карты: " + player.getHand() + " => " + playerValue);
        System.out.println("Карты дилера: " + dealer.getHand() + " => " + dealerValue);

        if (dealer.isBusted()) {
            System.out.println("Дилер перебрал! Игрок выигрывает раунд!");
            playerWins++;
        } else if (playerValue > dealerValue) {
            System.out.println("Игрок выигрывает раунд!");
            playerWins++;
        } else if (playerValue < dealerValue) {
            System.out.println("Дилер выигрывает раунд!");
            dealerWins++;
        } else {
            System.out.println("Ничья!");
        }

        System.out.println("Счет: Игрок " + playerWins + " – Дилер " + dealerWins);
    }

    private void displayHands(boolean revealDealer) {
        player.displayHand();
        if (revealDealer) {
            dealer.displayHand();
        } else {
            System.out.println("Карты дилера: " + dealer.getHand().get(0) + ", <закрытая карта>");
        }
    }

    private boolean askToContinue(Scanner scanner) {
        System.out.println("Хотите продолжить играть? (да/нет)");
        String response = scanner.next().toLowerCase();
        return response.equals("да");
    }

    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame();
        game.playGame();
    }
}
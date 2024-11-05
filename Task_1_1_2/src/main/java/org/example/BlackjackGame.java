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
        player = new Player("Player");
        dealer = new Player("Dealer");
        playerWins = 0;
        dealerWins = 0;
    }
//for tests
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setDealer(Player dealer) {
        this.dealer = dealer;
    }

    public int getPlayerWins() {
        return playerWins;
    }

    public int getDealerWins() {
        return dealerWins;
    }
    /////end of test

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean continuePlaying = true;
        System.out.println("Welcome to Blackjack!");

        while (continuePlaying) {
            // Проверка, достаточно ли карт в колоде
            if (!deck.hasEnoughCards()) {
                deck = new Deck();  // Создаем новую колоду
                System.out.println("Shuffling new deck...");
            }

            resetHands();  // Очищаем руки перед началом раунда

            System.out.println("\nRound " + (playerWins + dealerWins + 1));
            dealInitialCards(); //Раздает начальные карты игроку и дилеру
            if (checkInitialBlackjack()) { //если есть ли у игрока или дилера блэкджек сразу после раздачи первых двух карт
                displayHands(true);  // Открываем карту дилера, если игрок сразу выигрывает
                continuePlaying = askToContinue(scanner);//если есть блэкджек спросить про новый раунд
                continue;
            }

            playerTurn(scanner);//если еще нет блэка ни у кого - очередь хода игрока
            if (!player.isBusted()) {//если игрок не перебрал, то ход дилера
                dealerTurn();
            }
            determineWinner();
            continuePlaying = askToContinue(scanner);
        }
        scanner.close();
    }

    private void resetHands() {
        player.clearHand();  // Очищаем карты игрока
        dealer.clearHand();  // Очищаем карты дилера
        deck = new Deck();   // Создаем новую перетасованную колоду
    }
    private void dealInitialCards() {
        System.out.println("Dealer dealt the cards");
        for (int i = 0; i < 2; i++) {
            player.addCard(deck.dealCard());
            dealer.addCard(deck.dealCard());
        }
        displayHands(false);//show players and dealers if revealDealer==true (closed card is false)
    }

    private boolean checkInitialBlackjack() {
        if (player.hasBlackjack()) {
            System.out.println("Player got a Blackjack! Player wins the round!");
            playerWins++;
            return true;
        }
        if (dealer.hasBlackjack()) {
            System.out.println("Dealer got the Blackjack! Dealer wins the round!");
            dealerWins++;
            return true;
        }
        return false;
    }

    private void playerTurn(Scanner scanner) {
        while (true) {
            System.out.println("\nPlayer turn");
            System.out.println("-------");
            System.out.println("Enter \"1\" to take a card and \"0\" to stop (don't take any more cards)...");
            int action = scanner.nextInt();

            if (action == 1) {
                Card newCard = deck.dealCard();
                player.addCard(newCard);
                System.out.println("You opened card " + newCard);
                displayHands(false);//карта диллера еще скрыта
                if (player.isBusted()) {
                    System.out.println("Player has overdone it! Dealer wins the round!");
                    dealerWins++;
                    return;
                }
            } else if (action == 0) {
                System.out.println("You have decided to stop. The dealer goes next.");
                break; // Игрок заканчивает свой ход
            } else {
                System.out.println("Incorrect input. Please enter \"1\" or \"0\".");
            }
        }
    }

    private void dealerTurn() {
        System.out.println("\nDealer's turn");
        System.out.println("-------");
        System.out.println("Dealer opens a closed card " + dealer.getHiddenCard());


        // Дилер открывает одну еще карту
        displayHands(true);

        while (dealer.getHandValue() < 17) {
            Card newCard = deck.dealCard();
            dealer.addCard(newCard);
            System.out.println("\nDealer opens the card " + newCard);
            //dealer.addCard(deck.dealCard());
            displayHands(true);
        }
    }

    private void determineWinner() {
        int playerValue = player.getHandValue();
        int dealerValue = dealer.getHandValue();

        if (dealer.isBusted()) {
            System.out.println("\nDealer has overdone it! Player wins the round!");
            playerWins++;
        } else if (playerValue > dealerValue) {
            System.out.println("\nPlayer wins the round!");
            playerWins++;
        } else if (playerValue < dealerValue) {
            System.out.println("\nDealer wins the round!");
            dealerWins++;
        } else {
            System.out.println("\nIt's a draw!");//ничья
        }

        System.out.println("Score: Player " + playerWins + " - Dealer " + dealerWins);
    }
    //for tests
    public void endRound() {
        determineWinner();
    }

    private void displayHands(boolean revealDealer) {
        player.displayHand();
        if (revealDealer) {
            dealer.displayHand(); // Показываем все карты дилера
        } else {
            // Отображаем только первую карту дилера, а вторую скрываем
            System.out.println("Dealer's cards: " + "[" + dealer.getHand().getFirst() + ", <closed card>" + "]");
        }
    }

    private boolean askToContinue(Scanner scanner) {
        System.out.println("\nDo you want to continue playing? (yes/no)");
        String response = scanner.next().toLowerCase();
        return response.equals("yes");
    }

    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame();
        game.playGame();
    }
}
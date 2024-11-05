import org.example.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestUnit {

    /////////////////////Deck tests
    @Test
    public void testInitialDeckSize() {
        Deck deck = new Deck();
        // Проверяем, что колода содержит 52 карты после создания
        assertEquals(52, deck.getCards().size(), "Initial deck should contain 52 cards.");
    }

    @Test
    public void testDealCardReducesDeckSize() {
        Deck deck = new Deck();
        int initialSize = deck.getCards().size();

        // Вызываем dealCard() и проверяем, что количество карт уменьшилось на 1
        deck.dealCard();
        assertEquals(initialSize - 1, deck.getCards().size(), "Deck size should decrease by 1 after dealing a card.");
    }

    @Test
    public void testDealAllCards() {
        Deck deck = new Deck();

        // Раздаем все 52 карты
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }

        // Проверяем, что после раздачи всех карт колода пуста
        assertEquals(0, deck.getCards().size(), "Deck should be empty after all cards are dealt.");
    }

    ////////////////////////////// Tests for Player Class
    @Test
    public void testAddCard() {
        Player player = new Player("Test Player");
        Card card = new Card("Hearts", "Ace", 11);
        player.addCard(card);

        assertEquals(1, player.getHand().size(), "Player should have 1 card after adding.");
        assertEquals(card, player.getHand().getFirst(), "The added card should be the Ace of Hearts.");
    }

    @Test
    public void testGetHandValueWithNoCards() {
        Player player = new Player("Test Player");

        assertEquals(0, player.getHandValue(), "Hand value should be 0 when no cards are present.");
    }

    @Test
    public void testGetHandValueWithCards() {
        Player player = new Player("Test Player");
        player.addCard(new Card("Hearts", "10", 10));
        player.addCard(new Card("Diamonds", "Ace", 11)); // Ace counts as 11 initially

        assertEquals(21, player.getHandValue(), "Hand value should be 21 with a 10 and an Ace.");
    }

    @Test
    public void testIsBusted() {
        Player player = new Player("Test Player");
        player.addCard(new Card("Hearts", "10", 10));
        player.addCard(new Card("Diamonds", "Queen", 10)); // Value = 20
        player.addCard(new Card("Clubs", "10", 10)); // Now value = 30

        assertTrue(player.isBusted(), "Player should be busted with a hand value over 21.");
    }

    @Test
    public void testHasBlackjack() {
        Player player = new Player("Test Player");
        player.addCard(new Card("Hearts", "Ace", 11));
        player.addCard(new Card("Diamonds", "King", 10));

        assertTrue(player.hasBlackjack(), "Player should have blackjack with an Ace and a King.");
    }

    @Test
    public void testClearHand() {
        Player player = new Player("Test Player");
        player.addCard(new Card("Hearts", "Ace", 11));
        player.clearHand();

        assertEquals(0, player.getHand().size(), "Player's hand should be empty after clearing.");
    }
    ///////////////////////BlackjackGame
    @Test
    public void testInitialGameSetup() {
        BlackjackGame game = new BlackjackGame();

        assertEquals(0, game.getPlayerWins(), "Player wins should start at 0.");
        assertEquals(0, game.getDealerWins(), "Dealer wins should start at 0.");
    }

    @Test
    public void testDeckReshuffleWhenNotEnoughCards() {
        Deck deck = new Deck();
        int initialSize = deck.getCards().size();

        // Deal all cards to force reshuffle
        for (int i = 0; i < initialSize; i++) {
            deck.dealCard();
        }

        // Check reshuffle scenario
        assertFalse(deck.hasEnoughCards(), "Deck should not have enough cards after dealing all.");
        deck = new Deck(); // Simulating reshuffle

        assertEquals(52, deck.getCards().size(), "Deck should contain 52 cards after reshuffle.");
    }

    @Test
    public void testEndRoundWhenDealerBusted() {
        Player player = new Player("Player");
        Player dealer = new Player("Dealer");

        player.addCard(new Card("Hearts", "10", 10));
        player.addCard(new Card("Diamonds", "Queen", 10));
        dealer.addCard(new Card("Clubs", "10", 10));
        dealer.addCard(new Card("Spades", "Ace", 11));
        dealer.addCard(new Card("Diamonds", "Ace", 11)); // Dealer busted

        BlackjackGame game = new BlackjackGame();
        game.setPlayer(player);
        game.setDealer(dealer);
        game.endRound(); // determineWinner

        assertEquals(1, game.getPlayerWins(), "Player wins count should increase.");
        assertEquals(0, game.getDealerWins(), "Dealer wins count should not increase.");
    }
}


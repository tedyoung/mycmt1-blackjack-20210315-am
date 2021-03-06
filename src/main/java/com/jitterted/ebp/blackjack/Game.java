package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

  private final Deck deck;

  private final Hand dealerHand = new Hand();
  private final Hand playerHand = new Hand();
  private int playerBalance = 0;
  private int playerBetAmount = 0;

  public static void main(String[] args) {
    displayWelcome();
    playGame();
    resetDisplay();
  }

  private static void playGame() {
    Game game = new Game();
    game.initialDeal();
    game.play();
  }

  private static void resetDisplay() {
    System.out.println(ansi().reset());
  }

  private static void displayWelcome() {
    System.out.println(ansi()
                           .bgBright(Ansi.Color.WHITE)
                           .eraseScreen()
                           .cursor(1, 1)
                           .fgGreen().a("Welcome to")
                           .fgRed().a(" Jitterted's")
                           .fgBlack().a(" BlackJack"));
  }

  public Game() {
    deck = new Deck();
  }

  public void initialDeal() {
    dealRoundOfCards();
    dealRoundOfCards();
  }

  private void dealRoundOfCards() {
    // deal first round of cards, players first
    playerHand.drawCardFrom(deck);
    dealerHand.drawCardFrom(deck);
  }

  public void play() {
    // get Player's decision: hit until they stand, then they're done (or they go bust)
    boolean playerBusted = false;
    playerBusted = playerTurn(playerBusted);

    dealerTurn(playerBusted);

    displayFinalGameState();

    displayGameResult(playerBusted);
  }

  private void displayGameResult(boolean playerBusted) {
    if (playerBusted) {
      System.out.println("You Busted, so you lose.  💸");
//      playerLoses();
    } else if (dealerHand.isBusted()) {
      System.out.println("Dealer went BUST, Player wins! Yay for you!! 💵");
//      playerWins();
    } else if (playerHand.beats(dealerHand)) {
      System.out.println("You beat the Dealer! 💵");
//      playerWins();
    } else if (dealerHand.pushes(playerHand)) {
      System.out.println("Push: You tie with the Dealer. 💸");
//      playerPushes();
    } else {
      System.out.println("You lost to the Dealer. 💸");
//      playerLoses();
    }
  }

  private boolean playerTurn(boolean playerBusted) {
    while (!playerBusted) {
      displayGameState();
      String playerChoice = inputFromPlayer().toLowerCase();
      if (playerChoice.startsWith("s")) {
        break;
      }
      if (playerChoice.startsWith("h")) {
        playerHand.drawCardFrom(deck);
        if (playerHand.isBusted()) {
          playerBusted = true;
        }
      } else {
        System.out.println("You need to [H]it or [S]tand");
      }
    }
    return playerBusted;
  }

  private void dealerTurn(boolean playerBusted) {
    // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>=stand)
    if (!playerBusted) {
      while (dealerHand.dealerMustHit()) {
        dealerHand.drawCardFrom(deck);
      }
    }
  }

  private String inputFromPlayer() {
    System.out.println("[H]it or [S]tand?");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }

  private void displayGameState() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
    System.out.println("Dealer has: ");
    System.out.println(dealerHand.firstCard().display()); // first card is Face Up

    // second card is the hole card, which is hidden
    displayBackOfCard();

    System.out.println();
    System.out.println("Player has: ");
    playerHand.display();
    System.out.println(" (" + playerHand.value() + ")");
  }

  private void displayFinalGameState() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
    System.out.println("Dealer has: ");
    dealerHand.display();
    System.out.println(" (" + dealerHand.value() + ")");

    System.out.println();
    System.out.println("Player has: ");
    playerHand.display();
    System.out.println(" (" + playerHand.value() + ")");
  }

  private void displayBackOfCard() {
    System.out.print(
        ansi()
            .cursorUp(7)
            .cursorRight(12)
            .a("┌─────────┐").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("└─────────┘"));
  }

  public int playerBalance() {
    return playerBalance;
  }

  public void playerDeposits(int amount) {
    playerBalance += amount;
  }

  public void playerBets(int betAmount) {
    playerBalance -= betAmount;
    playerBetAmount = betAmount;
  }

  public void playerWins() {
    playerBalance += playerBetAmount * 2;
  }
}

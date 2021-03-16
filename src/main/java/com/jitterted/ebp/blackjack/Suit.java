package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;

public enum Suit {
  SPADES("♠"),
  DIAMONDS("♦"),
  HEARTS("♥"),
  CLUBS("♣");

  private final String symbol;

  Suit(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return symbol;
  }

  Ansi.Color color() {
    return "♥♦".contains(symbol()) ? Ansi.Color.RED : Ansi.Color.BLACK;
  }
}

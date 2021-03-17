package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HandValueTest {

  private static final Suit DUMMY_SUIT = Suit.CLUBS;

  @Test
  public void handWithValueOf22IsBusted() throws Exception {
    Hand hand = createHand("9", "8", "5");

    assertThat(hand.isBusted())
        .isTrue();
  }

  @Test
  public void handWithValueOf21IsNotBusted() throws Exception {
    Hand hand = createHand("9", "8", "4");

    assertThat(hand.isBusted())
        .isFalse();
  }

  private Hand createHand(String... ranks) {
    List<Card> cards = new ArrayList<>();
    for (String rank : ranks) {
      cards.add(new Card(DUMMY_SUIT, rank));
    }
    return new Hand(cards);
  }
}

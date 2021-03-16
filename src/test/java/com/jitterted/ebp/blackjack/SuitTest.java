package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

// this can be deleted
class SuitTest {

  @Test
  public void heartsSuitIsRed() throws Exception {
    Suit hearts = Suit.HEARTS;

    assertThat(hearts.isRed())
        .isTrue();
  }

  @Test
  public void spadesSuitIsNotRed() throws Exception {
    assertThat(Suit.SPADES.isRed())
        .isFalse();
  }
}
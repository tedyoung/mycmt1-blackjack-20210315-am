package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class WalletBettingTest {

  @Test
  public void walletWithBalance12WhenBet8BalanceIs4() throws Exception {
    Wallet wallet = new Wallet();
    wallet.addMoney(12);

    wallet.bet(8);

    assertThat(wallet.balance())
        .isEqualTo(12 - 8);
  }

  @Test
  public void walletWith27Bet7And9ThenBalanceIs11() throws Exception {
    Wallet wallet = new Wallet();
    wallet.addMoney(27);

    wallet.bet(7);
    wallet.bet(9);

    assertThat(wallet.balance())
        .isEqualTo(27 - 7 - 9);
  }

  @Test
  public void whenBetFullBalanceWalletIsEmpty() throws Exception {
    Wallet wallet = new Wallet();
    wallet.addMoney(33);

    wallet.bet(33);

    assertThat(wallet.isEmpty())
        .isTrue();
  }

  @Test
  public void betMoreThanBalanceThrowsException() throws Exception {
    Wallet wallet = new Wallet();
    wallet.addMoney(73);

    assertThatThrownBy(() -> {
      wallet.bet(74);
    }).isInstanceOf(IllegalStateException.class);
  }
  
}

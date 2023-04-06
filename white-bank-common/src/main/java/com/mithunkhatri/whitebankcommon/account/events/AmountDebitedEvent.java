package com.mithunkhatri.whitebankcommon.account.events;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountDebitedEvent {

  private String transactionId;
  private String accountId;
  private BigDecimal amount;

  // Including new calculated balance in the event
  private BigDecimal newBalance;
}

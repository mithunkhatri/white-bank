package com.mithunkhatri.whitebankcommon.account.events;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountCreditedEvent {

  private String transactionId;
  private String accountId;
  private BigDecimal amount;
  private BigDecimal newBalance;
}

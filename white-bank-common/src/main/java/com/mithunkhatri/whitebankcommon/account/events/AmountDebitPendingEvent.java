package com.mithunkhatri.whitebankcommon.account.events;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountDebitPendingEvent {

  private String transactionId;
  private String accountId;
  private BigDecimal amount;

  // Status of the transaction in case debit unsuccessful
  private String status;
  // Reason of the transaction in case debit being unsuccessful
  private String reason;
}

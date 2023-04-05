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
  private String status;
  private String reason;
}

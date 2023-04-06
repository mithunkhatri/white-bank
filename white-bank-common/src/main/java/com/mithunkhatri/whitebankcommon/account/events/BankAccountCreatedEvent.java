package com.mithunkhatri.whitebankcommon.account.events;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountCreatedEvent {

  private String accountId;
  private String accountOwner;
  private BigDecimal initialDeposit;
  private BigDecimal creditLine;
}

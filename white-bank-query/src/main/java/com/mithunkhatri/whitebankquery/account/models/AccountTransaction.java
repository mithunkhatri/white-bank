package com.mithunkhatri.whitebankquery.account.models;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransaction {
    
    private Instant transactionOn;
    private String type;
    private BigDecimal amount;
    private String status;
    private String reason;


    public AccountTransaction(Instant transactionOn, String type, BigDecimal amount) {
      this.transactionOn = transactionOn;
      this.type = type;
      this.amount = amount;
    }
}

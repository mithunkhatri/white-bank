package com.mithunkhatri.whitebankquery.account.controllers;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionResponse {
    
    private Date transactionOn;
    private String type;
    private BigDecimal amount;
}

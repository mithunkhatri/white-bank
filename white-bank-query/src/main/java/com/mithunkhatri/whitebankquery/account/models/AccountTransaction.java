package com.mithunkhatri.whitebankquery.account.models;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransaction {
    
    private Date transactionOn;
    private String type;
    private BigDecimal amount;
}

package com.mithunkhatri.whitebankquery.account.models;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    
    @Id
    private String accountId;
    private String accountOwner;
    private BigDecimal balance;
    private BigDecimal overdraftLimit;
    private String status;

    List<AccountTransaction> transactions;
}

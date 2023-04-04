package com.mithunkhatri.whitebankcmd.account.controllers;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountRequest {
    
    private String accountOwner;
    private BigDecimal initialDeposit;
    private BigDecimal creditLine;
}

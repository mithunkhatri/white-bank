package com.mithunkhatri.whitebankcmd.account.commands;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBankAccountCommand {
    
    @TargetAggregateIdentifier
    private String accountId;

    private String accountOwner;
    private BigDecimal initialDeposit;
    private BigDecimal creditLine;
}

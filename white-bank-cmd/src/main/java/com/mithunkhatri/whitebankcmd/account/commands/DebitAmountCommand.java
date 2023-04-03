package com.mithunkhatri.whitebankcmd.account.commands;

import java.math.BigDecimal;

public class DebitAmountCommand extends PaymentBaseCommand {
    
    public DebitAmountCommand(String accountId, BigDecimal amount) {
        super(accountId, amount);
    }
}
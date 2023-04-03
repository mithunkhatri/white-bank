package com.mithunkhatri.whitebankcmd.account.commands;

import java.math.BigDecimal;

public class CreditAmountCommand extends PaymentBaseCommand {
    public CreditAmountCommand(String accountId, BigDecimal amount) {
        super(accountId, amount);
    }
}

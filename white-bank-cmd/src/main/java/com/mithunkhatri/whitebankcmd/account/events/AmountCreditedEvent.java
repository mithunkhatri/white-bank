package com.mithunkhatri.whitebankcmd.account.events;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountCreditedEvent {
    
    private String accountId;
    private BigDecimal amount;
}

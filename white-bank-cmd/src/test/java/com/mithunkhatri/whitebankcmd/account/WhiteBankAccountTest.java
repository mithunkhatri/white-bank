package com.mithunkhatri.whitebankcmd.account;

import java.math.BigDecimal;
import java.util.UUID;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mithunkhatri.whitebankcmd.account.commands.CreateBankAccountCommand;
import com.mithunkhatri.whitebankcmd.account.commands.CreditAmountCommand;
import com.mithunkhatri.whitebankcmd.account.commands.DebitAmountCommand;
import com.mithunkhatri.whitebankcommon.account.events.AmountCreditedEvent;
import com.mithunkhatri.whitebankcommon.account.events.AmountDebitedEvent;
import com.mithunkhatri.whitebankcommon.account.events.BankAccountCreatedEvent;

public class WhiteBankAccountTest {
    
    private FixtureConfiguration<BankAccountAggregate> fixture;
    private String accountId;

    @BeforeEach
    public void setup() {
        fixture = new AggregateTestFixture<>(BankAccountAggregate.class);
        accountId = UUID.randomUUID().toString();
    }


    @Test
    public void givenCreateAccountCommandEmitAccountCreatedEvent() {

        fixture
            .givenNoPriorActivity()
            .when(new CreateBankAccountCommand(
                accountId,
                "Mithun",
                BigDecimal.valueOf(1000l),
                BigDecimal.valueOf(500l)
            ))
            .expectSuccessfulHandlerExecution()
            .expectEvents(new BankAccountCreatedEvent(
                accountId,
                "Mithun",
                BigDecimal.valueOf(1000l),
                BigDecimal.valueOf(500l)
            ));
    }

    @Test
    public void givenDebitAmountThenAmountDebited() {

        fixture
            .given(new BankAccountCreatedEvent(
                accountId,
                "Mithun",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(500)
            ))
            .when(new DebitAmountCommand(
                accountId,
                BigDecimal.valueOf(100)
            ))
            .expectSuccessfulHandlerExecution()
            .expectEvents(new AmountDebitedEvent(
                accountId,
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(900)
            ));
    }

    @Test
    public void givenCreditAmountThenAmountCredited() {

        fixture
            .given(new BankAccountCreatedEvent(
                accountId,
                "Mithun",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(500)
            ))
            .when(new CreditAmountCommand(
                accountId,
                BigDecimal.valueOf(100)
            ))
            .expectSuccessfulHandlerExecution()
            .expectEvents(new AmountCreditedEvent(
                accountId,
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(1100)
            ));
    }
}

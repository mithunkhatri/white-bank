package com.mithunkhatri.whitebankcmd.account.controllers;

import java.math.BigDecimal;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payment request. Common class for both debit and credit.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

  @Min(value = 1l, message = "Payment amount can not be zero or less")
  private BigDecimal amount;
}

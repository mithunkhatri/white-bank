package com.mithunkhatri.whitebankcmd.account.controllers;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Bank account create request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountRequest {

  @NotEmpty(message = "Account owner name should not be empty")
  private String accountOwner;

  @Min(value = 1l, message = "Initial deposit can not be zero or less")
  private BigDecimal initialDeposit;
  private BigDecimal creditLine;
}

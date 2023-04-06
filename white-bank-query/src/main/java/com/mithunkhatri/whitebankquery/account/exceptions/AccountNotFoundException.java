package com.mithunkhatri.whitebankquery.account.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Account not found exception
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(String accountId) {
    super("Bank account not found with id : " + accountId);
  }
  
}

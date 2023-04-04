package com.mithunkhatri.whitebankquery.account.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccounts {
  
  List<BankAccount> bankAccounts;
}

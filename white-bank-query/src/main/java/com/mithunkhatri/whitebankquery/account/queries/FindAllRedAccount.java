package com.mithunkhatri.whitebankquery.account.queries;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAllRedAccount {

  // balance to mark the account red
  private BigDecimal redMarker;
}

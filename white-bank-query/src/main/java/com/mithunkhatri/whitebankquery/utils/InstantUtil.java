package com.mithunkhatri.whitebankquery.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Instant conversion utility.
 */
public class InstantUtil {

  /**
   * Converts ISO Date to Instant object
   * @param since Example : 2023-01-01
   * @return
   */
  public static Instant toInstant(String since) {
    if(since == null) {
      return null;
    }
    LocalDate localDate = LocalDate.parse(since, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    return localDate.atStartOfDay(ZoneId.of("UTC")).toInstant();
  }
}

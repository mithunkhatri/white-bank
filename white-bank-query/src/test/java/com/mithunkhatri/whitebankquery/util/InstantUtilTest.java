package com.mithunkhatri.whitebankquery.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.mithunkhatri.whitebankquery.utils.InstantUtil;

/**
 * Tests InstantUtil methods
 */
public class InstantUtilTest {

  @ParameterizedTest
  @ValueSource(strings = { "2023-01-01" })
  public void testISOStringDateToInstant(String date) {
    assertEquals(InstantUtil.toInstant(date).toString(), "2023-01-01T00:00:00Z");
  }

  @ParameterizedTest
  @ValueSource(strings = { "", "randomdatestring", "2023-19-19", "2023-13-01", "23-01-01" })
  public void testISOStringDateToInstant() {
    assertThrows(DateTimeParseException.class, () -> InstantUtil.toInstant(""));
  }
}

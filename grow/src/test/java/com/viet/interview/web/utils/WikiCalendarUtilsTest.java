package com.viet.interview.web.utils;

import com.viet.interview.web.models.WikiDate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WikiCalendarUtilsTest {

  @Test
  void testPadWithZeroes() {
    WikiCalendarUtils calendarUtils = new WikiCalendarUtils();
    assertEquals("00", calendarUtils.padWithZeroes(0));
    assertEquals("01", calendarUtils.padWithZeroes(1));
    assertEquals("02", calendarUtils.padWithZeroes(2));
    assertEquals("03", calendarUtils.padWithZeroes(3));
    assertEquals("04", calendarUtils.padWithZeroes(4));
    assertEquals("05", calendarUtils.padWithZeroes(5));
    assertEquals("06", calendarUtils.padWithZeroes(6));
    assertEquals("07", calendarUtils.padWithZeroes(7));
    assertEquals("08", calendarUtils.padWithZeroes(8));
    assertEquals("09", calendarUtils.padWithZeroes(9));
    assertEquals("10", calendarUtils.padWithZeroes(10));
    assertEquals("11", calendarUtils.padWithZeroes(11));
    assertEquals("12", calendarUtils.padWithZeroes(12));
  }

  @Test
  void testConvertToString() {
    WikiCalendarUtils calendarUtils = new WikiCalendarUtils();
    assertEquals(
        "2020101000",
        calendarUtils.convertToString(new WikiDate(2020, 10, 10))
    );
    assertEquals(
        "2020011000",
        calendarUtils.convertToString(new WikiDate(2020, 1, 10))
    );
    assertEquals(
        "2020100100",
        calendarUtils.convertToString(new WikiDate(2020, 10, 1))
    );
    assertEquals(
        "2020010100",
        calendarUtils.convertToString(new WikiDate(2020, 1, 1))
    );
  }

  @Test
  void testComputeEndOfMonth() {
    WikiCalendarUtils calendarUtils = new WikiCalendarUtils();
    assertDatesEqual(
        new WikiDate(2020, 10, 31),
        calendarUtils.computeEndOfMonth(2020, 10)
    );
  }

  @Test
  void testComputeEndOfMonthLeapYear() {
    WikiCalendarUtils calendarUtils = new WikiCalendarUtils();
    assertDatesEqual(
        new WikiDate(2020, 2, 29),
        calendarUtils.computeEndOfMonth(2020, 2)
    );
  }

  @Test
  void testComputeNextWeek() {
    WikiCalendarUtils calendarUtils = new WikiCalendarUtils();
    assertDatesEqual(
        new WikiDate(2020, 11, 6),
        calendarUtils.computeNextWeek(2020, 10, 30)
    );
    assertDatesEqual(
        new WikiDate(2021, 1, 7),
        calendarUtils.computeNextWeek(2020, 12, 31)
    );
  }

  @Test
  void testComputeNextWeekLeapYear() {
    WikiCalendarUtils calendarUtils = new WikiCalendarUtils();
    assertDatesEqual(
        new WikiDate(2020, 2, 29),
        calendarUtils.computeNextWeek(2020, 2, 22)
    );
    assertDatesEqual(
        new WikiDate(2021, 3, 1),
        calendarUtils.computeNextWeek(2021, 2, 22)
    );
  }

  @Test
  void testComputeNext7Days() {
    WikiCalendarUtils calendarUtils = new WikiCalendarUtils();
    List<WikiDate> week = calendarUtils.computeNext7Days(2020, 10, 10);
    assertEquals(7, week.size());
    assertDatesEqual(new WikiDate(2020, 10, 10), week.get(0));
    assertDatesEqual(new WikiDate(2020, 10, 11), week.get(1));
    assertDatesEqual(new WikiDate(2020, 10, 12), week.get(2));
    assertDatesEqual(new WikiDate(2020, 10, 13), week.get(3));
    assertDatesEqual(new WikiDate(2020, 10, 14), week.get(4));
    assertDatesEqual(new WikiDate(2020, 10, 15), week.get(5));
    assertDatesEqual(new WikiDate(2020, 10, 16), week.get(6));
  }

  @Test
  void testComputeNext7DaysWrapYear() {
    WikiCalendarUtils calendarUtils = new WikiCalendarUtils();
    List<WikiDate> week = calendarUtils.computeNext7Days(2020, 12, 30);
    assertEquals(7, week.size());
    assertDatesEqual(new WikiDate(2020, 12, 30), week.get(0));
    assertDatesEqual(new WikiDate(2020, 12, 31), week.get(1));
    assertDatesEqual(new WikiDate(2021, 1, 1), week.get(2));
    assertDatesEqual(new WikiDate(2021, 1, 2), week.get(3));
    assertDatesEqual(new WikiDate(2021, 1, 3), week.get(4));
    assertDatesEqual(new WikiDate(2021, 1, 4), week.get(5));
    assertDatesEqual(new WikiDate(2021, 1, 5), week.get(6));
  }

  @Test
  void testComputeNext7DaysLeapYear() {
    WikiCalendarUtils calendarUtils = new WikiCalendarUtils();
    List<WikiDate> week = calendarUtils.computeNext7Days(2020, 2, 26);
    assertEquals(7, week.size());
    assertDatesEqual(new WikiDate(2020, 2, 26), week.get(0));
    assertDatesEqual(new WikiDate(2020, 2, 27), week.get(1));
    assertDatesEqual(new WikiDate(2020, 2, 28), week.get(2));
    assertDatesEqual(new WikiDate(2020, 2, 29), week.get(3));
    assertDatesEqual(new WikiDate(2020, 3, 1), week.get(4));
    assertDatesEqual(new WikiDate(2020, 3, 2), week.get(5));
    assertDatesEqual(new WikiDate(2020, 3, 3), week.get(6));
  }

  void assertDatesEqual(WikiDate date1, WikiDate date2) {
    assertEquals(date1.getDay(), date2.getDay());
    assertEquals(date1.getMonth(), date2.getMonth());
    assertEquals(date1.getYear(), date2.getYear());
  }
}

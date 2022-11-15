package com.viet.interview.web.utils;

import com.viet.interview.web.models.WikiDate;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class WikiCalendarUtils {

  public List<WikiDate> computeNext7Days(int year, int month, int day) {
    List<WikiDate> wikiDays = new ArrayList<>();
    DateTime dateTime = new DateTime(year, month, day, 0, 0);
    for (int i = 0 ; i < 7; i++) {
      DateTime newDateTime = dateTime.plusDays(i);
      wikiDays.add(
          new WikiDate(
              newDateTime.getYear(),
              newDateTime.getMonthOfYear(),
              newDateTime.getDayOfMonth()
          )
      );
    }

    return wikiDays;
  }

  public WikiDate computeNextWeek(int year, int month, int day) {
    DateTime dateTime = new DateTime(year, month, day, 0, 0);
    dateTime = dateTime.plusDays(7);
    return new WikiDate(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth());
  }

  public WikiDate computeEndOfMonth(int year, int month) {
    DateTime dateTime = new DateTime(year, month, 01, 0, 0);
    dateTime = dateTime.plusMonths(1).minusDays(1);
    return new WikiDate(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth());
  }

  public String convertToString(WikiDate wikiDate) {
    return wikiDate.getYear() +
        padWithZeroes(wikiDate.getMonth()) +
        padWithZeroes(wikiDate.getDay()) +
        "00";
  }

  public String padWithZeroes(int number) {
    if (number < 10) {
      return "0" + number;
    }

    return "" + number;
  }
}

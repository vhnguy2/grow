package com.viet.interview.web.models;

public class WikiDate {

  private int year;
  private int month;
  private int day;

  public WikiDate(int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof WikiDate)) {
      return false;
    }

    WikiDate d = (WikiDate) o;
    return d.getDay() == getDay() && d.getMonth() == getMonth() && d.getYear() == getYear();
  }

  @Override
  public int hashCode() {
    return year * month * day;
  }
}

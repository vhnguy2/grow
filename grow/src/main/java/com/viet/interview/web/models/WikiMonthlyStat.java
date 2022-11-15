package com.viet.interview.web.models;

import java.util.List;

public class WikiMonthlyStat {

  private int year;
  private int month;
  private List<WikiArticleMeta> articles;

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

  public List<WikiArticleMeta> getArticles() {
    return articles;
  }

  public void setArticles(List<WikiArticleMeta> articles) {
    this.articles = articles;
  }
}

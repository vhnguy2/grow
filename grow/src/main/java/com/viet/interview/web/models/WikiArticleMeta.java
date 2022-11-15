package com.viet.interview.web.models;

public class WikiArticleMeta {

  private String article;
  private long views;
  private int rank;

  public WikiArticleMeta() {

  }

  public WikiArticleMeta(String article, long views, int rank) {
    this.article = article;
    this.views = views;
    this.rank = rank;
  }

  public String getArticle() {
    return article;
  }

  public void setArticle(String article) {
    this.article = article;
  }

  public long getViews() {
    return views;
  }

  public void setViews(long views) {
    this.views = views;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }
}

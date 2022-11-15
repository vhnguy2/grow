package com.viet.interview.web.models;

public class WikiDailyStat {

  private String project;
  private String article;
  private String granularity;
  private long views;
  private long timestamp;

  public String getArticle() {
    return article;
  }

  public void setArticle(String article) {
    this.article = article;
  }

  public String getGranularity() {
    return granularity;
  }

  public void setGranularity(String granularity) {
    this.granularity = granularity;
  }

  public long getViews() {
    return views;
  }

  public void setViews(long views) {
    this.views = views;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public String getProject() {
    return project;
  }
}

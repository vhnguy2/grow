package com.viet.interview.web.models;

import java.util.List;

public class WikiProject {

  private String project;
  private List<WikiArticleMeta> articles;

  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public List<WikiArticleMeta> getArticles() {
    return articles;
  }

  public void setArticles(List<WikiArticleMeta> articles) {
    this.articles = articles;
  }
}

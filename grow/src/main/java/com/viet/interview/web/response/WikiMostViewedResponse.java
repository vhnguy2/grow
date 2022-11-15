package com.viet.interview.web.response;

import com.viet.interview.web.models.WikiArticleMeta;

import java.util.List;

public class WikiMostViewedResponse {

  private List<WikiArticleMeta> articles;

  public List<WikiArticleMeta> getArticles() {
    return articles;
  }

  public void setArticles(List<WikiArticleMeta> articles) {
    this.articles = articles;
  }
}

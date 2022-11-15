package com.viet.interview.web.utils;

import com.viet.interview.web.models.WikiArticleMeta;

import java.util.*;

public class ArticleUtils {

  public List<WikiArticleMeta> aggregate(List<WikiArticleMeta> articles) {
    Map<String, Long> articleToViews = new HashMap<>();
    for (WikiArticleMeta article : articles) {
      long newViewsValue = articleToViews.containsKey(article.getArticle())
          ? articleToViews.get(article.getArticle()) + article.getViews()
          : article.getViews();
      articleToViews.put(article.getArticle(), newViewsValue);
    }

    articleToViews = sortByValue(articleToViews);

    List<WikiArticleMeta> aggregatedList = new ArrayList<>();
    int rank = 1;
    for (Map.Entry<String, Long> entry : articleToViews.entrySet()) {
      aggregatedList.add(new WikiArticleMeta(entry.getKey(), entry.getValue(), rank++));
    }

    return aggregatedList;
  }

  private Map<String, Long> sortByValue(Map<String, Long> map) {
    // Create a list from elements of HashMap
    List<Map.Entry<String, Long>> list = new LinkedList<>(map.entrySet());

    // Sort the list
    list.sort(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()));

    // put data from sorted list to hashmap
    Map<String, Long> temp = new LinkedHashMap<>();
    for (Map.Entry<String, Long> entry : list) {
      temp.put(entry.getKey(), entry.getValue());
    }
    return temp;
  }
}

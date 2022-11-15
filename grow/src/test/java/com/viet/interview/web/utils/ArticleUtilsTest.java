package com.viet.interview.web.utils;

import com.viet.interview.web.models.WikiArticleMeta;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticleUtilsTest {

  @Test
  void testAggregate() {
    List<WikiArticleMeta> list = new ArrayList<>();
    list.add(new WikiArticleMeta("Article1", 10, 1));
    list.add(new WikiArticleMeta("Article2", 10, 1));
    list.add(new WikiArticleMeta("Article1", 10, 1));
    list.add(new WikiArticleMeta("Article2", 10, 1));
    list.add(new WikiArticleMeta("Article3", 11, 1));
    list.add(new WikiArticleMeta("Article4", 10, 1));

    ArticleUtils articleUtils = new ArticleUtils();
    List<WikiArticleMeta> outputList = articleUtils.aggregate(list);

    assertEquals(4, outputList.size());

    assertEquals("Article1", outputList.get(0).getArticle());
    assertEquals(20, outputList.get(0).getViews());
    assertEquals(1, outputList.get(0).getRank());

    assertEquals("Article2", outputList.get(1).getArticle());
    assertEquals(20, outputList.get(1).getViews());
    assertEquals(2, outputList.get(1).getRank());

    assertEquals("Article3", outputList.get(2).getArticle());
    assertEquals(11, outputList.get(2).getViews());
    assertEquals(3, outputList.get(2).getRank());

    assertEquals("Article4", outputList.get(3).getArticle());
    assertEquals(10, outputList.get(3).getViews());
    assertEquals(4, outputList.get(3).getRank());
  }
}

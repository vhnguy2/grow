package com.viet.interview.web.thirdparty;

import com.viet.interview.web.models.WikiDailyStat;
import com.viet.interview.web.models.WikiProject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;

public interface PublicWikiService {
  @GET("/api/rest_v1/metrics/pageviews/per-article/en.wikipedia/all-access/user/{article}/daily/{month_start}/{month_end}")
  Call<Map<String, List<WikiDailyStat>>> monthly(
      @Path("article") String article,
      @Path("month_start") String monthStart,
      @Path("month_end") String monthEnd
  );

  @GET("/api/rest_v1/metrics/pageviews/per-article/en.wikipedia/all-access/user/{article}/daily/{start}/{end}")
  Call<Map<String, List<WikiDailyStat>>> dailyForRange(
      @Path("article") String article,
      @Path("start") String start,
      @Path("end") String end
  );

  @GET("/api/rest_v1/metrics/pageviews/top/en.wikipedia/all-access/{year}/{month}/all-days")
  Call<Map<String, List<WikiProject>>> topMonthly(
      @Path("year") String year,
      @Path("month") String month
  );

  @GET("/api/rest_v1/metrics/pageviews/top/en.wikipedia/all-access/{year}/{month}/{day}")
  Call<Map<String, List<WikiProject>>> topDaily(
      @Path("year") String year,
      @Path("month") String month,
      @Path("day") String day
  );
}
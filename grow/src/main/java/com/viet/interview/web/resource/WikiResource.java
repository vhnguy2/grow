package com.viet.interview.web.resource;

import com.google.gson.GsonBuilder;
import com.viet.interview.web.models.WikiArticleMeta;
import com.viet.interview.web.models.WikiDailyStat;
import com.viet.interview.web.models.WikiDate;
import com.viet.interview.web.models.WikiProject;
import com.viet.interview.web.response.WikiDayMonthResponse;
import com.viet.interview.web.response.WikiMostViewedResponse;
import com.viet.interview.web.response.WikiViewsStatResponse;
import com.viet.interview.web.thirdparty.PublicWikiService;
import com.viet.interview.web.utils.ArticleUtils;
import com.viet.interview.web.utils.WikiCalendarUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The resource used to intercept all publicly facing API calls related to Wikipedia operations.
 */
@Path("/wiki/")
@Produces(MediaType.APPLICATION_JSON)
public class WikiResource {

  private static final String WIKI_BASE_URL = "https://wikimedia.org/";

  // TODO(viet): Build a data layer that fetches from in-memory cache first, if fails then
  //             fetches from database, and as a final resort fetches from Wikipedia.
  //
  //             As the data is retrieved, it will get stored in the cache and DB.

  // TODO(viet): inject these as dependencies
  private PublicWikiService service;
  private WikiCalendarUtils wikiCalendarUtils;
  private ArticleUtils articleUtils;

  public WikiResource() {
    // initialize 3p services
    Retrofit rf = new Retrofit.Builder()
        .baseUrl(WIKI_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
        .build();
    service = rf.create(PublicWikiService.class);
    // initialize utils
    wikiCalendarUtils = new WikiCalendarUtils();
    articleUtils = new ArticleUtils();
  }

  /**
   * Retrieves a list of the most viewed articles for a provided month.
   *
   * Example usage: http://localhost:8080/wiki/top/year/2015/month/10
   */
  @GET()
  @Path("/top/year/{year}/month/{month}")
  public WikiMostViewedResponse topMonth(@PathParam("year") Integer year,
                                         @PathParam("month") Integer month) throws IOException {
    WikiMostViewedResponse response = new WikiMostViewedResponse();

    Call<Map<String, List<WikiProject>>> call =
        service.topMonthly(year + "" , wikiCalendarUtils.padWithZeroes(month));
    Response<Map<String, List<WikiProject>>> wikiResponse = call.execute();
    List<WikiArticleMeta> topArticles = wikiResponse.body().get("items").get(0).getArticles();

    response.setArticles(topArticles);

    return response;
  }

  /**
   * Retrieves a list of the most viewed articles for a provided week. A "week" is defined
   * as [d, ..., d+6] where d is the start day.
   *
   * Example usage: http://localhost:8080/wiki/top/year/2015/month/10/week_start/31
   */
  @GET()
  @Path("/top/year/{year}/month/{month}/week_start/{day}")
  public WikiMostViewedResponse topWeek(@PathParam("year") Integer year,
                                        @PathParam("month") Integer month,
                                        @PathParam("day") Integer day) throws IOException {
    WikiMostViewedResponse response = new WikiMostViewedResponse();
    List<WikiArticleMeta> topArticles = new ArrayList<>();
    List<WikiDate> daysToFetch = new ArrayList<>();
    daysToFetch.addAll(wikiCalendarUtils.computeNext7Days(year, month, day));

    for (WikiDate dayToFetch : daysToFetch) {
      Call<Map<String, List<WikiProject>>> call = service.topDaily(
          dayToFetch.getYear() + "",
          wikiCalendarUtils.padWithZeroes(dayToFetch.getMonth()),
          wikiCalendarUtils.padWithZeroes(dayToFetch.getDay())
      );
      Response<Map<String, List<WikiProject>>> wikiResponse = call.execute();
      topArticles.addAll(wikiResponse.body().get("items").get(0).getArticles());
    }

    response.setArticles(articleUtils.aggregate(topArticles));

    return response;
  }

  /**
   * For any given article, get the view count of that specific article for a week. A
   * "week" is defined as [d, ..., d+6] where d is the start day.
   *
   * Example usage: http://localhost:8080/wiki/article/Albert_Einstein/stats/year/2015/month/10/week_start/01
   */
  @GET
  @Path("/article/{article}/stats/year/{year}/month/{month}/week_start/{day}")
  public WikiViewsStatResponse articleWeek(@PathParam("article") String article,
                                           @PathParam("year") Integer year,
                                           @PathParam("month") Integer month,
                                           @PathParam("day") Integer day) throws IOException {
    WikiViewsStatResponse response = new WikiViewsStatResponse();
    long viewCount = 0;

    Call<Map<String, List<WikiDailyStat>>> articleWeek = service.dailyForRange(
        article,
        wikiCalendarUtils.convertToString(new WikiDate(year, month, day)),                      // start of week
        wikiCalendarUtils.convertToString(wikiCalendarUtils.computeNextWeek(year, month, day))  // end of week
    );
    Response<Map<String, List<WikiDailyStat>>> wikiResponse = articleWeek.execute();
    List<WikiDailyStat> stats = wikiResponse.body().get("items");
    for (WikiDailyStat stat : stats) {
      viewCount += stat.getViews();
    }

    response.setViewCount(viewCount);
    return response;
  }

  /**
   * For any given article, be able to get the view count of that specific article for a month
   *
   * Example usage: http://localhost:8080/wiki/article/Albert_Einstein/stats/year/2015/month/10
   */
  @GET
  @Path("/article/{article}/stats/year/{year}/month/{month}")
  public WikiViewsStatResponse articleMonth(@PathParam("article") String article,
                                            @PathParam("year") Integer year,
                                            @PathParam("month") Integer month) throws IOException {
    WikiViewsStatResponse response = new WikiViewsStatResponse();
    long viewCount = 0;

    Call<Map<String, List<WikiDailyStat>>> articleMonth = service.monthly(
        article,
        wikiCalendarUtils.convertToString(new WikiDate(year, month, 01)),
        wikiCalendarUtils.convertToString(wikiCalendarUtils.computeEndOfMonth(year, month))
    );
    Response<Map<String, List<WikiDailyStat>>> wikiResponse = articleMonth.execute();
    List<WikiDailyStat> stats = wikiResponse.body().get("items");
    for (WikiDailyStat stat : stats) {
      viewCount += stat.getViews();
    }

    response.setViewCount(viewCount);
    return response;
  }


  /**
   * Retrieve the day of the month where an article got the most page views
   *
   * Example usage: http://localhost:8080/wiki/article/Albert_Einstein/topDay/year/2015/month/10
   */
  @GET
  @Path("/article/{article}/topDay/year/{year}/month/{month}")
  public WikiDayMonthResponse articleTopDay(@PathParam("article") String article,
                                            @PathParam("year") Integer year,
                                            @PathParam("month") Integer month) throws IOException {
    WikiDayMonthResponse response = new WikiDayMonthResponse();
    long bestDayOfMonth = 0;
    long mostViews = 0;

    Call<Map<String, List<WikiDailyStat>>> articleMonth = service.monthly(
        article,
        wikiCalendarUtils.convertToString(new WikiDate(year, month, 01)),
        wikiCalendarUtils.convertToString(wikiCalendarUtils.computeEndOfMonth(year, month))
    );
    Response<Map<String, List<WikiDailyStat>>> wikiResponse = articleMonth.execute();
    List<WikiDailyStat> stats = wikiResponse.body().get("items");
    for (WikiDailyStat stat : stats) {
      if (stat.getViews() > mostViews) {
        mostViews = stat.getViews();
        bestDayOfMonth = stat.getTimestamp();
      }
    }

    response.setDayOfMonthTimestamp(bestDayOfMonth);

    return response;
  }
}

// package com.web_scrapper;

// import java.io.IOException;
// import java.sql.SQLException;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.time.Year;
// import java.util.ArrayList;
// import java.util.Calendar;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.Locale;
// import java.util.TimeZone;
// import java.util.regex.Pattern;

// import org.jsoup.Connection;
// import org.jsoup.Jsoup;
// import org.jsoup.nodes.Document;
// import org.jsoup.nodes.Element;

// import models.News;
// import models.Rubric;

// public class RIAScraper implements WebScraper{
//     private String baseUrl = "https://ria.ru";
//     private Connection connection;
//     private Document document;

//     public HashMap<String, ArrayList<News>> parseNews() {
//         try {
//             this.getHtml();
//         } catch (IOException e) {
//             e.printStackTrace();
//             return null;
//         }

//         var news = new HashMap<String, ArrayList<News>>();
//         var navmenu = this.document.getElementsByClass("cell-extension__item-link");
//         //var navmenu = this.document.select("#content > div > div:nth-child(1) > div > div > div > div > div > div > div > div > div > div > div.the-in-carousel__stage > div > div > div > div > a");
//         navmenu.remove(navmenu.size()-1);

        

//         for (Element element : navmenu) {
//             var rubricLink = this.baseUrl + element.attr("href");
//             var rubricName = element.text();

//             var rubric = new Rubric();
//             rubric.setName(rubricName);
//             rubric.setSource_id(2);
//             try {
//                 rubric.Insert();
//             } catch (SQLException e) {
//                 e.printStackTrace();
//                 continue;
//             }

//             var newsFromRubricList = this.parseNewsFromRubric(rubricLink, rubricName);
//             news.put(rubricName, newsFromRubricList);
//         }
//         return news;
//     }

//     public void getHtml() throws IOException {
//         this.connection = Jsoup.connect(this.baseUrl)
//         .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
//         .referrer("http://www.google.com/")
//         .timeout(5000);
//         this.document = connection.get();
//     }

//     private ArrayList<News> parseNewsFromRubric(String url, String rubricName) {
//         int rubricID;
//         try {
//             rubricID = Rubric.SelectByRubric(rubricName, 2);
//         } catch (SQLException e) {
//             e.printStackTrace();
//             return null;
//         }

//         var list = new ArrayList<News>();
//         try {
//             Document page = Jsoup.connect(url)
//                 .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
//                 .referrer("http://www.google.com/")
//                 .timeout(5000)
//                 .get();

//             var elements = page.select("div.list-item, div.cell-list__item");
//             //var elements = page.select("a._longgrid > div.card-big__titles, a._longgrid > div.card-mini__title");

//             for (Element element : elements) {
//                 var news = this.parseNews(element);
//                 news.setRubric(rubricID);
//                 news.Insert();
//                 list.add(news);
//             }

//             return list;
//         } catch (IOException | SQLException e) {
//             e.printStackTrace();
//             return null;
//         }
//     }

//     private News parseNews(Element element) {
//         var news = new News();

//         var listItem = element
//             .select("div.list-item__content > a.list-item__title, a.cell-list__item-link > span.cell-list__item-title");
//         news.setTitle(listItem.text());
    
//         var timeString = element
//             .select("div.list-item__info > div.list-item__date," 
//                 + " a.cell-list__item-link > div.cell-list__item-info > div.cell-info__date ").text();
//         var date = parseDateTimeElement(timeString);
//         if (date == null)
//             return null;
//         news.setDatetime(date);

//         var link = element
//             .select("div.list-item__content > a.list-item__title, a.cell-list__item-link").attr("href");
//         news.setLink(link);
//         return news;
//     }   

//     private Date parseDateTimeElement(String date) {
//         //var patternFullDate = Pattern.compile("\\d\\d:\\d\\d,\\s\\d{1,2}\\s\\D+\\s\\d{4}");
//         var patternToday = Pattern.compile("^\\d\\d:\\d\\d$");
//         var patternYesterday = Pattern.compile("Вчера,\\s\\d\\d:\\d\\d");
//         var patternDateWOYear = Pattern.compile("\\d{1,2}\\s\\D+,\\s\\d\\d:\\d\\d");
        
//         var mather = patternToday.matcher(date);
//         if (mather.find()) {
//             var calendar = Calendar.getInstance();
//             this.setTime(calendar, date);
//             return calendar.getTime();
//         }
//         mather = patternYesterday.matcher(date);
//         if (mather.find()) { 
//             var calendar = Calendar.getInstance();
//             this.setTimeYesterday(calendar, date);
//             return calendar.getTime();
//         }
//         mather = patternDateWOYear.matcher(date);
//         if (mather.find()) {
//             return this.parseDateTimeWOYear(date);
//         }
//         else {
//             return this.parseDateTimeWYear(date);
//         }
//     }

//     private void setTimeYesterday(Calendar calendar, String parsedTime) {
//         var str = parsedTime.split(" ");
//         calendar.add(Calendar.DAY_OF_MONTH, -1);
//         this.setTime(calendar, str[1]);
//     }

//     private void setTime(Calendar calendar, String parsedTime) {
//         var str = parsedTime.split(":");
//         calendar.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
//         calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(str[0]));
//         calendar.set(Calendar.MINUTE, Integer.parseInt(str[1]));
//         calendar.set(Calendar.SECOND, 0);
//         calendar.set(Calendar.MILLISECOND, 0);
//     }

//     private Date parseDateTimeWOYear(String datetime) {
//         SimpleDateFormat parser = new SimpleDateFormat("dd MMMM, HH:mm"
//             , new Locale.Builder().setLanguage("ru").setScript("Cyrl").build());
//         parser.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
//         try {
//             Date date = parser.parse(datetime);
//             Calendar calendar = Calendar.getInstance();
//             calendar.setTime(date);
//             calendar.set(Calendar.YEAR, Year.now().getValue());
//             return calendar.getTime();
//         } catch (ParseException e) {
//             e.printStackTrace();
//             return null;
//         }
//     }

//     private Date parseDateTimeWYear(String datetime) {
//         SimpleDateFormat parser = new SimpleDateFormat("dd MMMM yyyy, HH:mm"
//             , new Locale.Builder().setLanguage("ru").setScript("Cyrl").build());
//         parser.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        
//         try {
//             Date date = parser.parse(datetime);
//             return date;
//         } catch (ParseException e) {
//             e.printStackTrace();
//             return null;
//         }
//     }
// }

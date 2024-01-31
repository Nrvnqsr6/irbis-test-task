// package com.web_scrapper;

// import java.io.IOException;
// import java.sql.SQLException;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.ArrayList;
// import java.util.Calendar;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Locale;
// import java.util.TimeZone;
// import java.util.regex.Pattern;

// import org.jsoup.Connection;
// import org.jsoup.Jsoup;
// import org.jsoup.nodes.Document;
// import org.jsoup.nodes.Element;
// import org.jsoup.select.Elements;

// import com.parser.DateTimeParser;

// import models.Link;
// import models.News;
// import models.Rubric;

// public class LentaScraper implements WebScraper {

//     private String baseUrl = "https://lenta.ru";
//     private Document document;
//     private DateTimeParser datetimeParser;
//     private int sourceId;
//     private HashMap<String, ArrayList<News>> news;

//     public LentaScraper() throws IOException, SQLException {
//         this.sourceId = Link.getIdByURL(baseUrl);
//         this.parseDocument(this.baseUrl);
//         this.news = new HashMap<>();
//     }

//     public HashMap<String, ArrayList<News>> parseNews() {

//         var navmenu = this.getNavMenu();

//         for (Element element : navmenu) {
//             Rubric rubric;
//             int rubricID;

//             try {
//                 rubric = this.parseRubric(element);
//                 rubricID = Rubric.SelectByRubric(
//                     rubric.getName(), 
//                     rubric.getSource_id()
//                 );
//             } catch (SQLException e) {
//                 e.printStackTrace();
//                 continue;
//             }

//             var newsFromRubricList = this.parseAllNewsFromRubric(
//                 rubric.getLink(), 
//                 rubric.getName(), 
//                 rubricID
//             );

//             if (newsFromRubricList == null) {
//                 continue;
//             }

//             news.put(rubric.getName(), newsFromRubricList);
//         }

//         return this.news;
//     }

//     private Elements getNavMenu() {
//         var navmenu = this.document.getElementsByClass("_is-extra");

//         //первый элемент - ссылка на главную
//         navmenu.remove(0);

//         return navmenu;
//     }

//     private Rubric parseRubric(Element element) throws SQLException {
//         var rubricLink = this.baseUrl + element.attr("href");
//         var rubricName = element.text();

//         var rubric = new Rubric();
//         rubric.setName(rubricName);
//         rubric.setSource_id(this.sourceId);
//         rubric.setLink(rubricLink);
        
//         rubric.Insert();

//         return rubric;
//     }

//     private void parseDocument(String url) throws IOException {
//         var connection = Jsoup.connect(url)
//             .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64)" 
//                 + " AppleWebKit/537.36 (KHTML, like Gecko)"
//                 + " Chrome/32.0.1667.0 Safari/537.36")
//             .timeout(5000);
//         this.document = connection.get();
//     }

//     private ArrayList<News> parseAllNewsFromRubric(
//         String url, 
//         String rubricName, 
//         int rubricID
//     ) {        
//         var list = new ArrayList<News>();
//         try {
//             this.parseDocument(url);

//             var elements = this.document.select("a._longgrid, a.card-feature");

//             for (Element element : elements) {
//                 var news = this.parseNewsFromElement(element);
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
    

//     private News parseNewsFromElement(Element element) {
//         var news = new News();
//         news.setTitle(
//             element.select("""
//                 div.card-big__titles > h3, 
//                 div.card-mini__text > h3, 
//                 div.card-feature__topic > h3
//             """)
//             .text());
        
//         var timeString = element.select("time").text();
//         var date = this.datetimeParser.parse(timeString);
//         news.setDatetime(date);

//         var link = element.attr("href");
//         news.setLink(this.baseUrl + link);
//         return news;
//     }

    
// }
 
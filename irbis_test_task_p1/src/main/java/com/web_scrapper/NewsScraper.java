package com.web_scrapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.models.Link;
import com.models.News;
import com.models.Rubric;
import com.parser.DateTimeParser;
import com.parser.NavmenuParser;

public class NewsScraper implements WebScraper{

    private String baseUrl;
    private Document document;
    private DateTimeParser datetimeParser;
    private NavmenuParser navmenuParser;
    private WebScrapingData webScrapingData;
    private int sourceId;
    private HashMap<Rubric, HashSet<News>> news;

    public NewsScraper(
        NavmenuParser navmenuParser,
        DateTimeParser dateTimeParser,
        WebScrapingData webScrapingData
    ) throws IOException, SQLException {
        this.baseUrl = webScrapingData.getBaseUrl();
        this.sourceId = Link.getIdByURL(baseUrl);
        this.datetimeParser = dateTimeParser;
        this.parseDocument(this.baseUrl);
        this.news = new HashMap<>();
        this.navmenuParser = navmenuParser;
        this.webScrapingData = webScrapingData;
    }

    public HashMap<Rubric, HashSet<News>> parseNews() {

        var navmenu = this.navmenuParser.parse(this.document);

        for (Element element : navmenu) {
            Rubric rubric = this.parseRubric(element);

            var newsFromRubricList = this.parseAllNewsFromRubric(
                rubric.getLink(), 
                rubric.getName()
            );

            if (newsFromRubricList == null) {
                continue;
            }

            news.put(rubric, newsFromRubricList);
        }

        return this.news;
    }

    // private Elements getNavMenu() {
    //     var navmenu = this.document.getElementsByClass("_is-extra");

    //     //первый элемент - ссылка на главную
    //     //navmenu.remove(0);

    //     return navmenu;
    // }

    private Rubric parseRubric(Element element) {
        var rubricLink = element.attr("href");
        if (!rubricLink.contains("https://"))
            rubricLink = this.baseUrl + rubricLink;
            
        var rubricName = element.text();

        var rubric = new Rubric();
        rubric.setName(rubricName);
        rubric.setSource_id(this.sourceId);
        rubric.setLink(rubricLink);
        
        //rubric.Insert();

        return rubric;
    }

    private void parseDocument(String url) throws IOException {
        var connection = Jsoup.connect(url)
            .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64)" 
                + " AppleWebKit/537.36 (KHTML, like Gecko)"
                + " Chrome/32.0.1667.0 Safari/537.36")
            .timeout(5000);
        this.document = connection.get();
    }

    private HashSet<News> parseAllNewsFromRubric(
        String url, 
        String rubricName
    ) {        
        var list = new HashSet<News>();
        try {
            this.parseDocument(url);

            var elements = this.document
                .select(this.webScrapingData.getNewsItemQuery());

            for (Element element : elements) {
                var news = this.parseNewsFromElement(element);
                list.add(news);
            }

            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private News parseNewsFromElement(Element element) {
        var news = new News();
        news.setTitle(
            element.select(this.webScrapingData.getTitleQuery())
            .text());
        
        var timeString = element
            .select(this.webScrapingData.getTimeQuery()).text();

        var date = this.datetimeParser.parse(timeString);
        news.setDatetime(date);

        String stringLink;
        if (this.webScrapingData.getLinkQuery() != "href") {
            var link = element.select(this.webScrapingData.getLinkQuery());
            stringLink = link.attr("href");
        }
        else {
            stringLink = element.attr("href");
        }

        //var rubricLink = element.attr("href");
        if (!stringLink.contains("https://"))
            stringLink = this.baseUrl + stringLink;
        news.setLink(stringLink);

        return news;
    }
}


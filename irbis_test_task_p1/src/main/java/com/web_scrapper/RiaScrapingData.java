package com.web_scrapper;

public class RiaScrapingData implements WebScrapingData{

    private String baseUrl = "https://ria.ru";
    private String rubricQuery = "cell-extension__item-link";
    private String newsItemQuery = "div.list-item, div.cell-list__item";
    private String titleQuery = """
        div.list-item__content > a.list-item__title, 
        a.cell-list__item-link > span.cell-list__item-title
        """;
    private String timeQuery = """
        div.list-item__info > div.list-item__date, 
        a.cell-list__item-link > div.cell-list__item-info > div.cell-info__date
        """;

    private String linkQuery = """
        div.list-item__content > a.list-item__title, 
        a.cell-list__item-link""";

    public String getBaseUrl() {
        return baseUrl;
    }    

    public String getRubricQuery() {
        return rubricQuery;
    };

    public String getNewsItemQuery() {
        return newsItemQuery;
    }

    public String getTitleQuery() {
        return titleQuery;
    };
    
    public String getTimeQuery() {
        return timeQuery;
    };

    public String getLinkQuery() {
        return linkQuery;
    };
}

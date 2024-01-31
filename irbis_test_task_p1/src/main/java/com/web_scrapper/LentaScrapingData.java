package com.web_scrapper;

public final class LentaScrapingData implements WebScrapingData{
    
    private String baseUrl = "https://lenta.ru";
    private String rubricQuery = "_is-extra";
    private String newsItemQuery = "a._longgrid, a.card-feature";
    private String titleQuery = """
        div.card-big__titles > h3, 
        div.card-mini__text > h3, 
        div.card-feature__topic > h3
    """;
    private String timeQuery = "time";
    private String linkQuery = "href";

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

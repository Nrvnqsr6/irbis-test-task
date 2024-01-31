package com.web_scrapper;

import java.util.HashMap;
import java.util.HashSet;

import com.models.News;
import com.models.Rubric;

public interface WebScraper {
    public HashMap<Rubric, HashSet<News>> parseNews();
}

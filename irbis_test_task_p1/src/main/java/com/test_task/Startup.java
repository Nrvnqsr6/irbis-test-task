package com.test_task;

import java.io.IOException;
import java.sql.SQLException;

import com.data_inserter.DataInserter;
import com.data_inserter.IDataInserter;
import com.parser.LentaDateTimeParser;
import com.parser.LentaNavmenuParser;
import com.parser.RiaDateTimeParser;
import com.parser.RiaNavmenuParser;
import com.web_scrapper.LentaScrapingData;
import com.web_scrapper.NewsScraper;
import com.web_scrapper.RiaScrapingData;
import com.web_scrapper.WebScraper;

public class Startup {
    public static void StartParsing() {
        try {
            WebScraper scraper = new NewsScraper(new LentaNavmenuParser(), new LentaDateTimeParser(), new LentaScrapingData());
            initParsing(scraper);
            scraper = new NewsScraper(new RiaNavmenuParser(), new RiaDateTimeParser(), new RiaScrapingData());
            initParsing(scraper);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    private static void initParsing(WebScraper scraper) {
        var resData = scraper.parseNews();
        IDataInserter DataInserter = new DataInserter();
        DataInserter.InsertData(resData);
    }
}

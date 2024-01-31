package com.parser;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class RiaNavmenuParser implements NavmenuParser{

    public Elements parse(Document document) {
        var navmenu = document.getElementsByClass("cell-extension__item-link");
        //var navmenu = this.document.select("#content > div > div:nth-child(1) > div > div > div > div > div > div > div > div > div > div > div.the-in-carousel__stage > div > div > div > div > a");
        navmenu.remove(navmenu.size()-1);

        return navmenu;
    }
}

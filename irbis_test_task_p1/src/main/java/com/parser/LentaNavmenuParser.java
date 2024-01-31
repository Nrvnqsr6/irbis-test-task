package com.parser;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class LentaNavmenuParser implements NavmenuParser{

    public Elements parse(Document document) {
        var navmenu = document.getElementsByClass("_is-extra");

        //первый элемент - ссылка на главную
        navmenu.remove(0);

        return navmenu;
    }
}

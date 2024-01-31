package com.parser;

import javax.swing.text.html.parser.Element;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public interface NavmenuParser {
    public Elements parse(Document document);
}

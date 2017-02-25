package Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

public class HtmlParser {
    private Document _document;

    public void loadHtmlSource(String htmlSource) {
        this._document = Jsoup.parse(htmlSource);
    }

    public boolean hasElement(String className) {
        Element link = this._document.select(className).first();
        return link != null;
    }

    public List<Element> getElements(String className) {
        return !this.hasElement(className) ? null : this._document.select(className);
    }

    public Element getElement(Element parentElement, String className) {
        return !this.hasElement(className) ? null : parentElement.select(className).first();
    }

    public Element getElement(String className) {
        return !this.hasElement(className) ? null : this._document.select(className).first();
    }
}

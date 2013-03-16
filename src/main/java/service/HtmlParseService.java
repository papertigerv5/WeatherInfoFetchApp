package service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-3-12
 * Time: 上午11:01
 * To change this template use File | Settings | File Templates.
 */
public class HtmlParseService {


    public static HtmlParseService getHtmlParseService(){
        if(htmlParseService == null){
            htmlParseService = new HtmlParseService();
        }

        return htmlParseService;
    }

    public List<Element> getTableElementsInDiv(String url,String divId){
        Document document = getDocumentByUrl(url);
        Element element = document.getElementById(divId);
        if(element == null){
            System.out.print(url+"   ");
        }
        return element.getElementsByTag("table");
    }

    public String getContentByTableElement(Element tableElement,int rowIndex,int columnIndex){
        List<Element> rowElements = tableElement.getElementsByTag("tr");
        if(rowIndex < rowElements.size()){
            Element rowElement = rowElements.get(rowIndex);
            List<Element> columnElements = rowElement.getElementsByTag("td");
            if(columnIndex < columnElements.size()){
                Element columnElement = columnElements.get(columnIndex);
                return columnElement.text();
            }
        }

        return null;
    }
    public Document getDocumentByUrl(String url){
        String htmlString  = urlInfoService.fetchInfoFromUrl(url);
        Document document = Jsoup.parse(htmlString);

        return document;
    }

    private HtmlParseService(){

    }

    private static HtmlParseService htmlParseService;

    //Service
    private URLInfoService urlInfoService = URLInfoService.getURLInfoServiceInstance();

}

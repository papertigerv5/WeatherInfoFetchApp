package service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-3-12
 * Time: 上午11:01
 * To change this template use File | Settings | File Templates.
 */
public class HtmlParseService {


    public static HtmlParseService getHtmlParseServiceInstance(){
        if(htmlParseService == null){
            htmlParseService = new HtmlParseService();
        }

        return htmlParseService;
    }


    public List<ArrayList<String>> parseDWRString(String htmlString){

        Document document = getDocumentByHtmlString(htmlString);
        Element firstTableElement = getFirstTableElementInDocument(document);
        List<Element> rowElements = firstTableElement.getElementsByTag(TABLEROWTAG);

        List<ArrayList<String>> tableTextList = new ArrayList<ArrayList<String>>();
        for(Element rowElement : rowElements){
            ArrayList<String> columnTextList = getRowColumnTextByRowElement(rowElement);
            tableTextList.add(columnTextList);
        }

        return tableTextList;
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
       return getDocumentByHtmlString(urlInfoService.fetchInfoFromUrl(url));
    }
    public Document getDocumentByHtmlString(String htmlString){
        return Jsoup.parse(htmlString);
    }


    private ArrayList<String> getRowColumnTextByRowElement(Element rowElement){
        List<Element> columnElements = rowElement.getElementsByTag(TABLECOLUMNTAG);
        ArrayList<String> columnTextList = new ArrayList<String>();
        for(Element columnElement : columnElements){
            columnTextList.add(columnElement.text());
        }

        return columnTextList;
    }

    /**
     * Fetch the first table element in the document if there is. Otherwise return null object.
     * @param document
     * @return
     */
    private Element getFirstTableElementInDocument(Document document){
        List<Element> allTableElements = document.getElementsByTag(TABLETAG);
        if(allTableElements != null && !allTableElements.isEmpty()){
            return allTableElements.get(0);
        }

        return null;
    }

    private HtmlParseService(){

    }

    private static final String TABLECOLUMNTAG = "td";
    private static final String TABLEROWTAG = "tr";
    private static final String TABLETAG = "table";

    private static HtmlParseService htmlParseService;

    //Service
    private URLInfoService urlInfoService = URLInfoService.getURLInfoServiceInstance();

}

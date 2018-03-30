package by.kolbun.andersen.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Stack;


public class MySAXParser extends DefaultHandler implements MyXmlParser {

    private StringBuilder result = new StringBuilder();
    private Stack<String> currentTag = new Stack<>();
    private boolean statusFailed = false;
    private String currency = "";

    @Override
    public void parse(String path) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxPF = SAXParserFactory.newInstance();
        SAXParser parser = saxPF.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        reader.setContentHandler(this);
        InputSource source = new InputSource(path);

        reader.parse(source);

        System.out.println(result.toString());
    }

    /**
     * метод вызывается в начале разбора документа
     */
    @Override
    public void startDocument() throws SAXException {
        result.append(" - Начинаем разбор документа - \n");
//        result.append("");
    }

    /**
     * метод вызывается когда начинается какой-либо элемент
     *
     * @param uri        - namespace uri, либо "" пустая строка, если мы не используем ns
     * @param localName  - локальное имя, либо "" пустая строка, если мы не используем ns
     * @param qName      - квалификационное имя, либо пустая строка если мы не используем qn
     * @param attributes - атрибуты элемента, либо пустой объект Attributes, если мы не используем их
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName.toLowerCase()) {
            case "transactions":
                result.append("transactions:\n");
                break;
            case "transaction":
                result.append("\t[");
                break;
            case "comment":
            case "id":
            case "status":
            case "from":
            case "to":
                result.append(qName).append(": ");
                break;
            case "amount":
                currency = attributes.getValue("currency");
                result.append(qName).append(": ");
                break;
            case "reason":
                result.append("(");
                break;
        }
        currentTag.push(qName);
    }

    /**
     * метод читает и обрабатывает символы внутри тегов
     *
     * @param ch     - массив символов
     * @param start  - начальная позиция массива
     * @param length - количество символов, используемых из массива
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s = new String(ch, start, length);
        switch (currentTag.peek()) {
            case "comment":
                result.append("\"").append(s).append("\"");
                break;
            case "from":
            case "to":
                result.append("'").append(s).append("'");
                break;
            case "id":
                result.append("#").append(s);
                break;
            case "status":
                if ("failed".equals(s)) statusFailed = true;
                result.append(s);
                break;
            case "reason":
                result.append(s);
                break;
            case "amount":
                result.append(s).append(" ").append(currency);
                break;
        }
//        System.out.print(new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName.toLowerCase()) {
            case "transactions":
                result.append("\n");
                break;
            case "transaction":
                result.append("]\n");
                break;
            case "status":
                if (!statusFailed) result.append(", ");
                break;
            case "id":
            case "from":
            case "to":
            case "comment":
                result.append(", ");
                break;
            case "amount":
                break;
            case "reason":
                result.append("), ");
                statusFailed = false;
                break;
        }
        currentTag.pop();
    }


    @Override
    public void endDocument() throws SAXException {
        result.append(" - Закончили разбор документа - ");
    }
}

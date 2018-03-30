package by.kolbun.andersen.parsers;

import by.kolbun.andersen.entity.MyTransaction;
import by.kolbun.andersen.entity.Statuses;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.sql.rowset.spi.TransactionalWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyDOMParser implements MyXmlParser {
    @Override
    public void parse(String path) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory docBF = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBF.newDocumentBuilder();
        InputSource source = new InputSource(path);
        Document doc = docBuilder.parse(source);

        List<MyTransaction> myTransactions = new ArrayList<>();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                int id = Integer.parseInt(elem.getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue());
                Statuses status = Statuses.valueOf(elem.getElementsByTagName("status").item(0).getChildNodes().item(0).getNodeValue().toUpperCase());
                String reason = "";
                if (status == Statuses.FAILED)
                    reason = elem.getElementsByTagName("reason").item(0).getChildNodes().item(0).getNodeValue();
                String from = elem.getElementsByTagName("from").item(0).getChildNodes().item(0).getNodeValue();
                String to = elem.getElementsByTagName("to").item(0).getChildNodes().item(0).getNodeValue();

                Set<String> comments = new HashSet<>();
                String comment;
                NodeList comts = elem.getElementsByTagName("comment");
                for (int c = 0; c < comts.getLength(); c++) {
                    comment = comts.item(c).getChildNodes().getLength() == 0 ?
                            "" : comts.item(c).getChildNodes().item(0).getNodeValue();
                    comments.add(comment);
                }

                int amount = Integer.parseInt(elem.getElementsByTagName("amount").item(0).getChildNodes().item(0).getNodeValue());
                String currency = elem.getElementsByTagName("amount").item(0).getAttributes().getNamedItem("currency").getNodeValue();

                myTransactions.add(new MyTransaction(
                        id, status, reason, from, to, comments, amount, currency
                ));
            }
        }

        myTransactions.forEach(System.out::println);
    }
}
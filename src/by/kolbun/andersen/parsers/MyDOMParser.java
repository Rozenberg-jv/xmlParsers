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
import java.util.List;

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
                String comment =
                        elem.getElementsByTagName("comment").item(0).getChildNodes().getLength() == 0 ?
                                "" : elem.getElementsByTagName("comment").item(0).getChildNodes().item(0).getNodeValue();
                int amount = Integer.parseInt(elem.getElementsByTagName("amount").item(0).getChildNodes().item(0).getNodeValue());
                String currency = elem.getElementsByTagName("amount").item(0).getAttributes().getNamedItem("currency").getNodeValue();

                myTransactions.add(new MyTransaction(
                        id, status, reason, from, to, comment, amount, currency
                ));
            }
        }

        myTransactions.forEach(System.out::println);
    }
}
/*private int id;
    private Statuses status;
    private String reason;
    private String from;
    private String to;
    private String comment;
    private int amount;
    private String currency;*/
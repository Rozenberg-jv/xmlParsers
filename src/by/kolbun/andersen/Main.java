package by.kolbun.andersen;

import by.kolbun.andersen.parsers.MyDOMParser;
import by.kolbun.andersen.parsers.MySAXParser;
import by.kolbun.andersen.parsers.MyXmlParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        String file = "example.xml";

        MyXmlParser parser;

        parser = new MySAXParser();
        parser.parse(file);

        parser = new MyDOMParser();
        parser.parse(file);

    }
}

package com.example.soap.support.utils;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

public final class XPathUtils {
    private XPathUtils() {}

    public static boolean exists(String xml, String expression) {
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            Document document = factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            String result = (String) xpath.evaluate(expression, document, XPathConstants.STRING);
            return result != null && !result.isBlank();
        } catch (Exception e) {
            throw new IllegalStateException("XPath evaluation failed for expression: " + expression, e);
        }
    }
}

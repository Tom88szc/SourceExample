package com.example.soapframework.util;

import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

public final class XPathHelper {
    private XPathHelper() {
    }

    public static boolean nodeExists(String xml, String expression) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            Object result = xPath.evaluate(expression, new InputSource(new StringReader(xml)), XPathConstants.NODE);
            return result != null;
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Invalid XPath expression: " + expression, e);
        }
    }

    public static String evaluate(String xml, String expression) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            return xPath.evaluate(expression, new InputSource(new StringReader(xml)));
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Invalid XPath expression: " + expression, e);
        }
    }
}

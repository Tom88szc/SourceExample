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

/*
Wyjaśnienie po polsku:
Ta klasa pomaga sprawdzać odpowiedź XML przez XPath.

Co robi ten kod:
1. Zamienia tekst XML na dokument DOM.
2. Tworzy obiekt XPath.
3. Wykonuje przekazane wyrażenie XPath na odpowiedzi.
4. Zwraca true, jeśli wynik istnieje i nie jest pusty.

Najprościej mówiąc:
To jest pomocnik do sprawdzania, czy w odpowiedzi SOAP istnieje konkretne pole lub wartość.
*/

/*
EDU komentarz:
Ta klasa pomaga czytać wartości z XML za pomocą XPath.
Dzięki temu możesz sprawdzić, czy w odpowiedzi istnieje konkretny element
albo czy ma oczekiwaną wartość.

To jest wygodniejsze i czytelniejsze niż ręczne szukanie tekstu w całym XML.
*/

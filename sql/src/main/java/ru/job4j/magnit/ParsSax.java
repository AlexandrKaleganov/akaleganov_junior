package ru.job4j.magnit;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Alexander Kaleganov
 * @since 26.10.2018
 * @version 1
 * парсер xml в первом методе используется SAX подход во втором методе используется STAX подход
 * вроде как последний стандарт по обраотки xml в потоке  как я понял он как и сакс сможет обработать бесконечно большой xml документ
 * типо новый метод
 */
public class ParsSax {

    /**
     * STAX
     * @param dest
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws XMLStreamException
     */
    public long parsSax(File dest) throws ParserConfigurationException, SAXException, IOException, XMLStreamException {
        Long res = Long.valueOf(0);
        Optional<Long>  optional = Optional.of(res);
        XMLInputFactory factory = XMLInputFactory.newFactory();
        XMLStreamReader parser = factory.createXMLStreamReader(new FileInputStream(dest));
        while (parser.hasNext()) {
            int evenet = parser.next();
            if (evenet == XMLStreamConstants.START_ELEMENT) {
                if (parser.getAttributeValue(0) != null) {
                    optional = Optional.of(optional.get() + Long.valueOf(parser.getAttributeValue(0)));
                }
            }
        }
        return optional.get();
    }
}

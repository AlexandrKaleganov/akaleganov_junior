package ru.job4j.forum;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;


public class LogicProgramm {

    /**
     * генератор xml файлов
     */
    public StringWriter generate(Gemmass gemmass) throws JAXBException {
        StringWriter data = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(Gemmass.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(gemmass, data);
        return data;
    }

    /**
     * коневертер xml файла в html
     */

    public StringWriter convertXMLtoHTML(StringWriter xml) throws TransformerException {
        StringWriter result = new StringWriter();
       /* String xsl = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\">\n" +
                "<xsl:template match=\"/\">\n" +
                "<html>"+
                "<gemmass>" +
                "   <xsl:for-each select=\"gemmass/gems\">\n" +
                "       <gems>" +
                "           <xsl:attribute name=\"Местодобычи\">" +
                "               <xsl:value-of select=\"origin\"/>" +
                "           </xsl:attribute>" +
                "       <name>" +
                "           <xsl:attribute name= \"Название\">" +
                "                 <xsl:value-of select=\"name\"/>" +
                "            </xsl:attribute>" +
                "       </name>\n" +
                "       <preciousness>" +
                "           <xsl:attribute name= \"Драгоценный_Да_Нет\">" +
                "                 <xsl:value-of select=\"preciousness\"/>" +
                "            </xsl:attribute>" +
                "       </preciousness>\n" +
                "       </gems>\n" +

                "   </xsl:for-each>\n" +
                " </gemmass>\n" +
                "</html>"+
                "</xsl:template>\n" +
                "</xsl:stylesheet>\n";
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(new ByteArrayInputStream(xsl.getBytes())));
        transformer.transform(new StreamSource(new ByteArrayInputStream(xml.toString().getBytes())), new StreamResult(result));*/
        return result;
    }
}

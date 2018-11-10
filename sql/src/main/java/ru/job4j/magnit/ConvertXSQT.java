package ru.job4j.magnit;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * @author Alexander Kaleganov
 * трансформер трансформирует один xml  документ в другой по xsl схеме
 */
public class ConvertXSQT {
    /**
     *
     * @param source
     * @param dest
     * @param scheme
     * @throws TransformerException
     */
  public void convert(File source, File dest, File scheme) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(scheme));
        transformer.transform(new StreamSource(source), new StreamResult(dest));
    }
}

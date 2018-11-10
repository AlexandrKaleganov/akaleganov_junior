package ru.job4j.magnit;

import org.junit.Test;

import javax.xml.transform.TransformerException;
import java.io.File;

/**
 * тестирование конвертирования файла
 */
public class ConvertXSQTTest {

    @Test
    public void convert() throws TransformerException {
        ConvertXSQT convertXSQT = new ConvertXSQT();
        convertXSQT.convert(new File("src//main//resources//magnit//rsl.xml"),
                new File("src//main//resources//magnit//dest.xml"),
                new File("src//main//resources//magnit//scheme.xsl"));
    }
}
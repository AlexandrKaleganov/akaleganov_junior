package ru.job4j.magnit;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * @author Alexander Kaleganov
 * финальный тест, подклчение  базе генерация даных и тестирование двух методов SAX и STAX
 */
public class ParsSaxTest {

    @Test
    public void parsSaxverOne() {
        File source = new File("src//main//resources//magnit//rsl.xml");
        File dest = new File("src//main//resources//magnit//dest.xml");
        File scheme = new File("src//main//resources//magnit//scheme.xsl");
        StoreXML storeXML = new StoreXML(source);
        ConvertXSQT convertXSQT = new ConvertXSQT();
        ParsSax parsSax = new ParsSax();
        try (StoreSQL storeSQL = new StoreSQL(new Conf("magnit.properties"))) {

            //подключение к базе
            Assert.assertThat(storeSQL.init(), Is.is(true));
            //генерация базы
            storeSQL.generate(10);
            //создание xml файла
            storeXML.save(storeSQL.getList());
            //конвертация созданного xml файла
            convertXSQT.convert(source, dest, scheme);

            Assert.assertThat(parsSax.parsSax(dest), Is.is(parsSax.parsSax(dest)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parsSaxverTwo() {
    }
}
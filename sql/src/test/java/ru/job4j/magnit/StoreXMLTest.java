package ru.job4j.magnit;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * проверка генерации файла
 */
public class StoreXMLTest {

    @Test
    public void testExml() {
        try (StoreSQL storeSQL = new StoreSQL(new Conf("magnit.properties"))) {
            Assert.assertThat(storeSQL.init(), Is.is(true));
            storeSQL.generate(10);
            StringWriter d = new StringWriter();
            StoreXML storeXML = new StoreXML(new File("src//main//resources//magnit//rsl.xml"));
            storeXML.save(storeSQL.getList());
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src//main//resources//magnit//rsl.xml")));
            String temp;
            StringBuilder actual = new StringBuilder();
            while ((temp = reader.readLine()) != null) {
                actual.append(temp + "\n");
            }
            System.out.println(actual.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
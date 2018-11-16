package ru.job4j.controlnaia.deletdabl;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class DablkillTest {
    @Test
    public void testdeleteItems() {
        try (Dablkill dablkill = new Dablkill(new Config())) {
            dablkill.addtoItemName("object1");
            dablkill.addtoItemName("object1");
            dablkill.addtoItemName("object2");
            dablkill.addtoItemName("object1");
            dablkill.addtoItemName("object3");
            dablkill.addtoItemName("object3");
            System.out.println(dablkill.getListItems());
            dablkill.deleteDabl();
//            Assert.assertThat(dablkill.getListItems().get(0), Is.is("object1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
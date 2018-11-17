package ru.job4j.controlnaia.deletdabl;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

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
            //проверим что мы добавили в БД 6 объектов
            Assert.assertThat(dablkill.getListItems().size(), Is.is(6));
            //удаляем все дубли
            dablkill.deleteDabl();
            //проверяем что в БД остался только один объект
            Assert.assertThat(dablkill.getListItems().size(), Is.is(1));
            //это объект 2 т.е. он в единственном экземпляре в БД
            Assert.assertThat(dablkill.getListItems().get(0), Is.is("object2"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
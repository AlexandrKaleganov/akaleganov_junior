package ru.job4j.xmlparser;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class XmlUsage {

    @XmlRootElement
    public static class User {
        private List<XmlTesterObject> values;

        public User() {
        }

        public User(List<XmlTesterObject> values) {
            this.values = values;
        }

        public List<XmlTesterObject> getValues() {
            return values;
        }

        public void setValues(List<XmlTesterObject> values) {
            this.values = values;
        }
    }

    @XmlRootElement
    public static class Field {
        private int value;

        public Field() {
        }

        public Field(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }


    public static void main(String[] args) throws Exception {
        StringWriter res = new StringWriter();
        StringWriter res2 = new StringWriter();

        JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(new User(Arrays.asList(new XmlTesterObject(1, "Dmitrii", "Goman"),
                new XmlTesterObject(2, "Kaleganov", "Alexander"))),
                res
        );
        System.out.println(res);
    }
}
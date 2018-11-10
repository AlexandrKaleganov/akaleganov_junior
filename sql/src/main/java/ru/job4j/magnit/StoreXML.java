package ru.job4j.magnit;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Alexander Kaleganov
 * генерируем XML документ по объекту EntryList
 */
public class StoreXML {
    private Path path;

    StoreXML(File target) {
        this.path = Paths.get(String.valueOf(target));
    }

    public void save(List<EntryList.Entry> data) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(EntryList.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(new EntryList(data), path.toFile());
    }

}

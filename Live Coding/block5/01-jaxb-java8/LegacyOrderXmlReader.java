package seminar.legacy;

import java.io.StringReader;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

/** JAXB-Reader mit Jakarta-API. */
public class LegacyOrderXmlReader {
    public Order read(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Order.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Order) unmarshaller.unmarshal(new StringReader(xml));
    }
}

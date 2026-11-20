package seminar.jakarta;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/** Illustratives Zielmodell für eine bewusste Jakarta-API-Migration. */
@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderJakarta {
    public String id;
    public String customer;
}

package seminar.legacy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/** Static reference for one preparatory step; not compiled in the seminar. */
@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderExplicitMappingAfterStep {
    @XmlElement(name = "id")
    public String id;

    @XmlElement(name = "customer")
    public String customer;
}

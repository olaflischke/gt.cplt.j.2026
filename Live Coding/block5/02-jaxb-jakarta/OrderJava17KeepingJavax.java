package seminar.java17;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** JDK-Upgrade-Pfad: passende externe javax-API verwenden, Imports bleiben javax. */
@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderJava17KeepingJavax {
    public String id;
    public String customer;
}

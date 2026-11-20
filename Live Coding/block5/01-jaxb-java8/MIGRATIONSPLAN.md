# Migrationsplan: JAXB unter JDK 25

## Annahmen und offene Entscheidungen

- Umfang sind die drei Java-Dateien in diesem Ordner: `LegacyOrderXmlReader.java`, `Order.java` und `OrderExplicitMappingAfterStep.java`.
- `OrderExplicitMappingAfterStep.java` ist laut Kommentar eine Referenzdatei, die im Seminar nicht kompiliert wird. Sie soll separat kompilierbar sein, aber nicht zusätzlich im JAXB-Kontext des Readers registriert werden.
- Für diese Migration wird `jakarta.xml.bind` als Ziel festgelegt. JDK 25 erzwingt diesen Namensraumwechsel nicht; alternativ könnte eine kompatible externe `javax`-JAXB-API beibehalten werden.
- Die Maven-Build-Konfiguration ist `pom.xml`. Die Testinfrastruktur muss noch im Projekt geklärt werden.

## Schritt 1: XML-Vertrag für die Migration

Für den Migrationstest wird folgende Struktur festgelegt:

```xml
<order>
    <id>A-1007</id>
    <customer>Beispiel GmbH</customer>
</order>
```

Das Wurzelelement heißt `order`; `id` und `customer` sind direkte Kindelemente. Alle drei Elemente gehören zu keinem XML-Namespace (Namespace-URI ist leer). Der XML-Namespace ist unabhängig vom Java-API-Namensraum `javax.xml.bind` beziehungsweise `jakarta.xml.bind`.

Abgleich: Das [Beispieldokument im Nachbarordner](../02-jaxb-jakarta/order.xml) enthält diese Elementnamen und keinen deklarierten Namespace. In `Order.java` ist `order` als Wurzel annotiert und Feldzugriff für `id` und `customer` festgelegt; `OrderExplicitMappingAfterStep.java` nennt beide Kindelemente ausdrücklich. Eine `package-info.java` mit Namespace-Vorgaben ist unter `block5` nicht vorhanden.

**Noch offene Projektentscheidung:** Das Beispiel belegt keinen vollständigen fachlichen XML-Vertrag. Pflichtfelder, Reihenfolge, Wiederholungen, weitere Elemente und fachliche Wertebereiche sind nicht festgelegt. Eine fachliche Bestätigung oder ein Schema für produktive Eingaben liegt nicht vor.

## Schritt 2: JAXB-Namensraumstrategie

Für die Migration wird die Jakarta-JAXB-API mit dem Java-Paketnamen `jakarta.xml.bind` gewählt. Das gilt für `LegacyOrderXmlReader.java`, `Order.java` und `OrderExplicitMappingAfterStep.java`; die jeweiligen Imports werden erst in den Schritten 5 bis 7 umgestellt.

Die Alternative, `javax.xml.bind` mit einer passenden externen API und Implementierung beizubehalten, wird für diesen Plan nicht gewählt. Der XML-Vertrag aus Schritt 1 bleibt davon unberührt.

## Schritt 3: JAXB-API im Maven-Build

`pom.xml` deklariert `jakarta.xml.bind:jakarta.xml.bind-api:4.0.2` als Compile-Abhängigkeit und konfiguriert die Java-Dateien im Ordner als Quellen für JDK 25. Die Referenzdatei importiert bis Schritt 7 weiterhin `javax.xml.bind`; ein vollständiger Compile ist daher erst nach dieser Importumstellung zu erwarten.

## Schritt 4: JAXB-Laufzeitimplementierung

`pom.xml` deklariert `org.glassfish.jaxb:jaxb-runtime:4.0.6` als Runtime-Abhängigkeit. Die Eclipse-JAXB-Referenzimplementierung verwendet die Jakarta-JAXB-API. Ob die ausgewählten Abhängigkeiten gemeinsam unter JDK 25 für `Order` funktionieren, muss nach der Importumstellung durch einen Laufzeittest bestätigt werden.

## Schritt 5: JAXB-Imports des Readers

`LegacyOrderXmlReader.java` verwendet `jakarta.xml.bind` für `JAXBContext`, `Unmarshaller` und `JAXBException`. Der Ablauf und der Rückgabetyp bleiben unverändert; der deklarierte Ausnahmetyp wechselt von `javax.xml.bind.JAXBException` zu `jakarta.xml.bind.JAXBException`. Aufrufer müssen gegebenenfalls ihre Ausnahmebehandlung anpassen.

## Schritt 6: JAXB-Imports des Modells

`Order.java` verwendet `jakarta.xml.bind.annotation` für seine JAXB-Annotationen. Der Wurzelname `order`, der Feldzugriff sowie die Felder `id` und `customer` bleiben unverändert. Die Kompilierung und das Zusammenspiel mit dem Reader müssen noch unter JDK 25 geprüft werden.

## Kleinschrittiger Plan

| Schritt | Umfang – genau eine Änderung | Prüfkriterium | Erwartetes Ergebnis |
|---|---|---|---|
| 1 | Den erwarteten XML-Vertrag festlegen: Wurzel `order`, Kindelemente `id` und `customer`. | Die Vertragsentscheidung dokumentiert Elementnamen und Namespace und ist gegen ein fachliches Beispieldokument oder Schema geprüft. | Der Testumfang beruht auf einem bestätigten Vertrag. **Noch offene Projektentscheidung:** In diesem Ordner gibt es weder XML-Beispiel noch Schema. |
| 2 | JAXB-Namensraumstrategie auf Jakarta festlegen. | Die Entscheidung nennt `jakarta.xml.bind` als Ziel und hält fest, dass alle drei Dateien dazugehören. | Die Importänderungen der folgenden Schritte haben ein einheitliches Ziel. Die mögliche Alternative `javax` wird für diesen Plan nicht gewählt. |
| 3 | Die Jakarta-JAXB-API-Abhängigkeit in der Maven-Build-Konfiguration ergänzen. | Die Abhängigkeit wird aufgelöst; die Jakarta-Imports sind nach den Schritten 5 bis 7 beim Kompilieren auflösbar. | JAXB-Annotationen und -Typen sind auf JDK 25 zur Compile-Zeit verfügbar. |
| 4 | Eine JAXB-Laufzeitimplementierung als Runtime-Abhängigkeit ergänzen. | Ein Testlauf unter JDK 25 kann nach der Importumstellung einen `JAXBContext` für `Order` erzeugen. | Die Implementierung ist zur Laufzeit verfügbar; die Kompatibilität von API und Provider wird durch den Test bestätigt. |
| 5 | In `LegacyOrderXmlReader.java` die JAXB-Imports von `javax.xml.bind` auf `jakarta.xml.bind` umstellen. | Die Datei kompiliert mit der ausgewählten API; Rückgabetyp und Ablauf bleiben unverändert, der deklarierte Ausnahmetyp wechselt zu Jakarta. | Der Reader verwendet die vereinbarte API, liest weiterhin den XML-String und gibt ein `Order` zurück. |
| 6 | In `Order.java` die Annotation-Imports auf `jakarta.xml.bind.annotation` umstellen. | Die Klasse kompiliert; `@XmlRootElement`, `@XmlAccessorType(XmlAccessType.FIELD)` und die Felder bleiben erhalten. | Das Modell behält die Zuordnung zu `order`, `id` und `customer`, verwendet aber den gewählten API-Namensraum. |
| 7 | In `OrderExplicitMappingAfterStep.java` die Annotation-Imports auf `jakarta.xml.bind.annotation` umstellen. | Die Datei kompiliert separat; `@XmlElement`-Namen `id` und `customer` bleiben erhalten. | Auch die Referenzdatei ist konsistent migriert, ohne ihren Status als nicht im Seminar-Reader verwendetes Modell zu ändern. |
| 8 | Einen erfolgreichen Unmarshal-Test für den bestätigten XML-Vertrag ergänzen. | Unter JDK 25 liefert der Reader für `<order>` mit `id` und `customer` ein Objekt mit den erwarteten Feldwerten. | API, Provider, XML-Wurzel und Feldzuordnung funktionieren zusammen. |
| 9 | Einen Fehlerfalltest für nicht wohlgeformtes XML ergänzen. | Der Test prüft, dass der Aufruf erwartungsgemäß mit `JAXBException` fehlschlägt und keinen Erfolg vortäuscht. | Der deklarierte Fehlerpfad des Readers ist unter dem neuen Provider geprüft. |
| 10 | Die vorhandene Build-/CI-Prüfung auf JDK 25 festlegen. | Kompilierung und beide Tests laufen nachweislich mit JDK 25, nicht nur mit einem älteren lokalen JDK. | Die geforderte Lauffähigkeit ist reproduzierbar überprüft. **Noch offen:** Eine Build- oder CI-Konfiguration ist in diesem Ordner nicht sichtbar. |

Die Schritte ändern weder die fachliche Datenstruktur noch registrieren sie beide Modellklassen gemeinsam im selben JAXB-Kontext. Der Reader bleibt bei `Order.class`; ob die explizite Referenzdatei künftig produktiv verwendet werden soll, bleibt eine separate Projektentscheidung.

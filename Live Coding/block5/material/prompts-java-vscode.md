GitHub Copilot in VS Code: Promptfolge für den Java-Migrationskreislauf

Diese Prompts sind Plain Text. Jeder Prompt steht als einzelner Absatz ohne Blockquote-Zeichen. Füge jeweils nur den Prompt der aktuellen Station in Copilot Chat ein und gib den passenden Datei- oder XML-Kontext dazu.

Prompt 0 - Vager Auftrag zum Vergleich
Mach diesen Java-Code modern und migriere ihn.

Prompt 1 - Bestandscode verstehen
Erkläre den markierten Code und den Datenfluss vom XML-String bis zum Ergebnisobjekt. Beschreibe nur, was aus den geöffneten Dateien hervorgeht. Nenne Wurzel-Element, verwendete Felder, JAXB-Aufrufe und erkennbare Laufzeitannahmen. Trenne sichtbare Fakten von Vermutungen. Aendere keinen Code und schlage noch keine Migration vor.

Prompt 2 - Veraltete APIs und Muster identifizieren
Liste die im markierten Code verwendeten APIs und Muster. Ordne jeden Fund ein als: mit dem JDK ausgeliefert und später entfernt, externe Abhängigkeit, möglicher Jakarta-API-Wechsel oder optionale Modernisierung. Erkläre, was ein JDK-Upgrade tatsächlich erzwingt und was eine separate Projektentscheidung ist. Erfinde keine Versionen und ändere nichts.

Prompt 3 - Risiko und Kopplung bewerten
Bewerte Reader, Modell und XML-Beispiel. Gib eine Tabelle mit Fundstelle, Kopplung, möglichem Risiko, Auswirkung und fehlendem Beleg aus. Berücksichtige JAXB-API und Implementierung, Namensraum, XML-Wurzel und Elementnamen. Trenne Fakten von Unbekanntem. Stelle mir keine Rückfragen; markiere unbekannte Server- oder Abhängigkeitsdetails als offene Projektentscheidung. Aendere keinen Code.

Prompt 4 - Kleinschrittigen Migrationsplan erstellen
Erstelle einen kleinschrittigen Plan für dieses Beispiel. Jeder Schritt darf genau eine unabhängig prüfbare Änderung enthalten und muss Umfang, Voraussetzung, erwartetes Ergebnis, Risiko, Prüfkriterium und Stop-Punkt nennen. Beginne mit dem Festhalten des bestehenden XML-Vertrags. Plane als ersten Code-Schritt nur explizite XML-Feldnamen in einer Modellkopie; ändere noch nichts. Trenne diesen Vorbereitungsschritt von der späteren Entscheidung zwischen externer JAXB-API im javax-Namensraum und Jakarta. Stelle mir keine Rückfragen und erfinde keine Projektinformationen.

Prompt 5 - Genau einen ersten Schritt umsetzen
Arbeite nur an einer Kopie der Modellklasse Order.java. Füge für die vorhandenen Felder id und customer jeweils eine explizite JAXB-Elementzuordnung mit demselben Namen hinzu. Erhalte den Wurzel-Elementnamen order und den FIELD-Zugriff. Aendere keine andere Datei, keine Reader-Logik, keine Abhängigkeit und keinen Namensraum. Gib nur den minimalen Diff und eine kurze Begründung aus. Wenn du Dateien nicht direkt bearbeiten kannst, liefere den Patch als Text. Führe keinen Build und keinen Test aus.

Prompt 6 - Verhalten vorher und nachher vergleichen
Vergleiche die ursprüngliche Modellklasse, die geänderte Arbeitskopie und das XML-Beispiel. Prüfe Wurzel-Element, Namen der Kindelemente und erwartete Objektwerte. Erkläre, ob der Quelltextvergleich für gleiches erwartetes Verhalten spricht und worauf du diese Aussage stützt. Behaupte nicht, dass der Code ausgeführt oder ein Test bestanden wurde. Nenne den Laufzeittest, der in der echten Projektumgebung noch erforderlich ist.

Prompt 7 - Nach der Prüfung den Kreislauf wiederholen
Nimm die aktualisierte Arbeitskopie als neuen Ausgangspunkt. Fasse zuerst den aktuellen Stand zusammen. Identifiziere danach die nächste offene API- oder Migrationsfrage, bewerte deren Kopplung und schlage genau einen nächsten Planungsschritt vor. JAXB-Auslieferung ab JDK 11, externe Bereitstellung im javax-Namensraum und Jakarta-Wechsel sind getrennt zu behandeln. Wenn Projektinformationen fehlen, markiere sie als offene Entscheidung statt mir Fragen zu stellen. Aendere keinen Code.

Prompt 8 - Copilot-Vorschlag kritisch prüfen
Prüfe deine vorherige Antwort. Welche Aussagen sind direkt durch Quelltext oder XML belegt? Welche beruhen auf Annahmen? Hast du mehrere Schritte vermischt, unaufgefordert weitere Dateien geändert oder eine Testausführung behauptet? Korrigiere die Antwort und ändere keinen Code.

Prompt 9 - Den Kreislauf auf Reflection übertragen
Analysiere LegacyReflectiveAccess.java nach demselben Schema: Bestandscode verstehen, problematisches Muster identifizieren, Risiko und Kopplung bewerten, einen kleinen Plan erstellen und einen geeigneten Verhaltens- oder Kompatibilitätsnachweis nennen. Setze nichts um. Unterscheide eine unterstützte API von einem internen JDK-Detail und stelle mir keine Rückfragen.

Prompt 10 - Den Kreislauf auf Datum und Zeit übertragen
Analysiere LegacyOrderDateService.java nach demselben Schema. Unterscheide eine optionale Modernisierung mit java.time von einer durch Java 17 erzwungenen Migration. Nenne vor einer Änderung die benötigten fachlichen Entscheidungen zu Kalenderdatum, Zeitpunkt und Zeitzone. Schlage keinen umfassenden Umbau vor und ändere keinen Code.

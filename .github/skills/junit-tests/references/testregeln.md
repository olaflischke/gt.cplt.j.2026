# Testregeln

- Testmethoden heißen methode_szenario_erwartetesErgebnis.
- Jeder Test folgt dem AAA-Muster (Arrange, Act, Assert). Die drei Abschnitte trennst du mit Leerzeilen.
- Mehrere Eingaben für dieselbe Prüfung: @ParameterizedTest mit @CsvSource.
- Mindestens ein Test für Grenzwerte: 0, leer, null, maximaler Wert.
- Abhängigkeiten mit Mockito ersetzen. Keine echte Datenbank.
- Assertions mit AssertJ: assertThat(...).
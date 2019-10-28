# Modul for web-serveren

Dette prosjektet inneholder web-serveren til REST-api-et for [Mailprosjektet](../readme.md)

## Web-serveren

For å realisere rest-api'et trenger vi en web-server. Vi har valgt å bruke Jersey, som implementerer JAX-RS-Standarden, på samme måte som simpleexample2.

Siden implementasjonen av JAX-RS bare tar hånd om å enklere koble sammen vanlig java-metoder til HTTP kall som GET, POST, PUT osv., må vi også ha noe som faktisk implementerer en HTTP server for oss. Her har vi igjen fulgt simpleexample2 og bruker Grizzly for å gjøre dette.

Ved å bruke disse ferdilagde løsningene for serveren blir koden vår særdeles kort og enkel (ca. 2 filer med 20 linjer). Klassene gjenspeiler også stort sett hvordan det gjøres i simpleexample2, men vi har simplifisert det for vårt formål, med de to følgende klassene:
* **Config** - Dette er en enkel klasse som sier hva slags ressurser som skal være med. For oss blir det (AccountService fra rest-api og ting reltarert til JSON format).
* **GrizzlyApp** - Denne vil sette opp serveren med enkel oppstartskode og bruker en instans av Config til å sette opp api-et.

## Bygging med Gradle

Når det kommer til bygging med gradle for serveren prosjektet er det viktig å få alt av avhengigheter fra jersey og girzzly til å fungere riktig. Dette gjenspeiler igjen mye av det som er gjort i simpleexample2. I tillegg har jo serveren avhengigheter fra project_api underprosjektet, så det er også tatt med. Ekstra avhengigheter er da kun jackson biblioteket for JSON håndtering. Ellers er det de andre tingene som går igjen i alle prosjekter: testing, testrapportering med jacoco, kodesjekking og rapportering med checkstyles og spotbugs.

# CORE

Dette java-prosjektet inneholder domenelogikken til [Mailprosjektet]() til [gr1922]()

## Domenelaget

Siden dette er et mailprosjekt trenger vi klasser som kan håndtere og representere data knyttet til kontoer, meldinger og inboxer. Derfor har vi bygget opp vårt domenelag rundt klassene Account, Message og Inbox. Account klassen inneholder data om brukernavn, passord og inbox (som blir delegert til inbox-klassen). Account-klassen vil også kunne kommunisere med systemet (presistenslaget), f.eks om kontoen eksisterer eller er gyldig forhold til det som er lagret (dette gjøres ved hjelp av AccountIO). Message klassen er bare en enkelt ikke-muterbar klasse som inneholder data om selve meldingen, emne, fra- og til konto. Inbox er en klasse som represterer en liste med meldinger, og mye av funksjonene er delgert til List-interfacet og ArrayList implementasjonen i Java. Her vil også Inbox-klassen kunne kommunisere med systemet (presistenslaget), f.eks ved å skrive over alle meldingene med inbox-objektet sine meldinger eller bare laste opp en enkel melding.

I tillegg har vi utvidet funksjonaliteten etter en brukerhistorie om at brukeren skal kunne automatisk se alle som har sendt meldinger til brukeren. Dette gjøres da med klassen Contacts, som implementerer InboxListener-interfacet fordi den trenger å lytte på eventuelle endringer som kan skje i Inbox for å oppdatere kontaktene.

Her er et enkelt klasse diagram av hvordan klassene henger sammen.

```plantuml
class Account {
	String email_address
	String password
}

class Message {
	String subject
	String message
}

class Inbox {
	
}

interface MailReader {
	
}

class InboxIO {

}

class AccountIO {
	
}

interface InboxListener {
	void inboxChanged(List<Message> messages)
	void addedMessage(Message message)
}

class Contacts {
	
}

Account "account" -- "inbox" Inbox
Account *--> "1" AccountIO : "io"

Message *--> Account: "to/from"

Inbox *--> "*" InboxListener: "listeners"
Inbox *--> "*" Message: "messages"
Inbox *--> "1" InboxIO: "io"

Contacts *--> "*" Account: "accounts"
InboxListener <|-- Contacts

MailReader <|-- InboxIO
```


## Presistenslaget

For at vår mailapplikasjon skal fungere som forventent, må vi naturligvis kunne lagre meldinger på en maskin (om det er lokalt eller på en server) så de kan leses uavhengig av om man avslutter applikasjonen eller ikke. Dataene som må lagres er dermed både konto data, altså brukernavn og passord (siden vi har login og logout funksjonalitet), og alt av meldingsdata som ligger i en enekel account sin inbox. Klassene som håndtere skriving og lesing er derfor AccountIO og InboxIO. Når det gjelder InboxIO, vil den også implementere et grensesnitt MailReader, som gjør av vi kan implementere skrivingen og lesning på forskjellige måter. Vi har to implementasjoner, InboxIO og InboxIOJson, men InboxIO som har vanlig textformat er den som er for øyeblikket i bruk. AccountIO er bare en enkel klasse som skriver og leser brukernavn og passord fra/til fil. 

Vi har i tillegg klasser som håndtere serialisering og deserialisering av hovedklassene (Account og Message) vår til JSON format. Dette er i hovedsak for å kunne enkelt sende data og ta imot data fra et REST-api på en enkel og forventet måte. Men, kan også brukes til å gjøre lesing og skriving til filer enklere (selv om vi bruker tekst som også funker greit). Vi har også en CompleteObjectMapper som er en klasse som utvider ObjectMapper, som er den klassen i jackson-biblioteket som faktisk utfører konverteringen, og inneholder alle de klassene vi bruker for seralisering/deserialisering. Da kan man til slutt ha alt av seraliserings/deserialiserings funksjonalitet i en klasse slik at det blir enkelt å bare bruke den klassen til å utføre selve jobben.

## Bygging med Gradle

Når det kommer til bygging med gradle for core prosjektet er det bare å få alt av standard java funksjonalitet ordnet. Ekstra avhengigheter er da kun jackson biblioteket for JSON håndtering. Ellers er det de andre tingene som går igjen i alle prosjekter: testing, testrapportering med jacoco, kodesjekking og rapportering med checkstyles og spotbugs.

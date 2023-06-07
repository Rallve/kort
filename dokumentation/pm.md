# DnD-spel

Lucas LG, 2023-06-07

## Inledning

Syftet med arbetet var att göra något slags slutprojekt där vi skulle använda de kunskaper som vi har lärt oss genom kursens gång.

## Bakgrund

I denna uppgift var målet att skapa ett projekt som använder objektorienterad programmering med MVC struktur (antag att spelet har ett GUI) och ska innehålla antingen databastekniker eller nätverkstekniker (eller båda).
Först skapades ett nytt repo som laddades upp till github. Innan kodandet började skapades ett readme.md där jag skrev vad jag tänkte göra.
Sedan skapades lite planering om vilka klasser och tabeller som jag tänkte använda. Planeringen uppdaterades under projektets gång ifall nya saker dök upp.

Därefter började själva kodandet. I början skapades en model, en view och en controller (i detta fall döpte jag dock controllern till "main"). 
Jag började skriva några grundläggande funktioner, såsom att kunna rulla de virtuella tärningarna som skulle användas i mitt Dungeons and Dragons-inspirerade spel.
Det var dock viktigt att faktiskt få de viktiga sakerna att fungera först, såsom att kunna hämta data från databasen, innan jag började lägga till en massa annat.
Därför öppnade jag därefter tableplus och skapade de tabeller som jag skulle behöva till mitt projekt. 
Sedan kodade jag en funktion som kan hämta data från tabellerna efter att man har skrivit in databasens lösenord i en JOptionPanel.
Det visades sig däremot vara väldigt jobbigt att skriva in lösenordet varje gång man skulle hämta data från databasen, så jag ändrade funktionen till att den läste in lösenordet från ett textdokument som inte laddades upp på github istället.

Efter vidare programmering hade jag tillslut skapat ett spel som verkar fungera. 
Jag insåg däremot att jag inte skulle hinna göra alla sakerna som jag hade tänkt göra, såsom en view.
Istället använde jag flera olika JOptionPanels för att hämta input från användaren, vilket var lättare och mindre tidskrävande.
Jag hann dessutom inte skapa ett system för att till exempel levla upp sin karaktär eller liknande, tyvärr.
Efter några finslipningar var det endast en grej kvar att förbättra, och det var lösenordshanteringen för databasen.
För att göra det lättare för användaren så gjorde jag så att ifall programmet inte kan hitta en textfil med lösenordet i så kommer den att fråga användaren för lösenordet.
Efter att användaren har skrivit in lösenordet i en JOptionPane skapar programmet en textfil där den skriver ut lösenordet, som den senare kan läsa ifrån varje gång den behöver lösenordet.
Slutligen skapade jag en JAR-fil av programmet, samt en väldigt simpel html-sida där jag skapade en länk för att ladda ner JAR-filen.

## Positiva erfarenheter

Jag hade flera positiva erfarenheter med spelet. 
Det mesta av programmerandet var egentligen väldigt lätt. 
Det var bland annat lätt att skapa funktionen för att rulla tärningarna eller för att hämta data från databasen.
Att skriva och skicka SQLQuerys till databasen var heller inte svårt.

## Negativa erfarenheter

Det fanns dock också några negativa erfarenheter. 
Ett problem jag hade handlade om när spelaren skulle välja vilken fiende hen ville attackera.
För att välja fick man en JOptionPane med en dropdownmeny med alla fiender man kunde attackera. 
Problmemet var att fienderna, som egentligen bara var Enemy objekt, hade inga särskilda namn förutom typ "Enemy@5e34fg3", vilket gjorde det omöjligt att egentligen veta vad man attackerade.
Jag lyckade tillslut fixa det genom att istället för att visa Enemy objektena i dropdownmenyn så skapades en till lista av Strings med de riktiga namnen för fienderna som istället visades i dropdownmenyn.
När spelaren sedan valde ett av namnen i dropdownmenyn så hittades indexet av det namnet i listan av namn, och det indexet användes därefter för att välja själva fienden från den andra listan på samma index.

En till grej som var förvånandsvärt svårt var att sortera fienderna utifrån vilket initiativ de rullade. 
Jag blev tvungen att gå igenom hela listan av fiender en gång för varje fiende i listan (så det blev nästlade for-loopar) och för varje inre loop sparades det högsta initiativ-värdet samt positionen i listan.
Den fienden lades därefter till i en ny lista och ignorerades när loopen började om igen. Tillslut hade jag en lista med alla fiender sorterade.
Det hela blev ännu mer komplicerat när jag också var tvungen att lägga till spelaren i ordningslistan på nått sätt, trots att spelaren har en annan datatyp.
Det löstes genom att byta ut hela listan mot en array där ett tomt index representerade spelaren.

## Sammanfatning

Jag är överlag ganska nöjd med slutresultatet. 
Vissa saker hade jag däremot som sagt inte tillräckligt med tid för att slutföra.
Spelet som det är just nu fungerar bra, och ifall jag skulle vilja så är det ganska lätt att lägga till fler funktioner eller fler fiender, vapen och så vidare.
En grej som jag annars antagligen skulle kunna förbättra är att försöka skapa en gemensam klass för Enemy och Player.
Det var inte tanken från början men de två klasserna blev oerhört lika med exakt samma variabler i båda.
Enda skillnaden är att koden i klasserna är lite annorlunda.
# Biblioteks-web-applikation
## Demo-app på 2. semester forår 2022

Der er med vilje ikke refaktoreret i bund, og der er ikke lavet design, men applikationen indeholder:

- Strukturering i passende packages for overblik. Noget af strukturen er også givet af Maven, og kan ikke laves om. F.eks. opdelingen i `/java` og `/webapp`.
- Javaservlets
- JSP sider. De som ligger i WEB-INF kan kun tilgås via en servlet.
- En super skrabet css-fil til styling
- Datamappere, som anvender en connection pool.
- Fejlhåndtering med exceptions
- Integrationstest af datamappere.

Funtionelt kan applikationen:

- Vise en forside med links til undersider
- Vise alle lånere
- Vise alle bøger med detaljer
- Se alle udlån
- Logge en bruger på. Der findes to brugere i databasen.
  1. a@a.dk med kodeord: 1234 (rolle: laaner)
  2. b@b.dk med kodeord: 1234 (rolle: admin)
  - Man kan se på index.jsp hvordan man kan udnytte om en bruger er logget på eller ej.
  - Hvis man indtaster ugyldige data under indlogning, bliver man sendt til en en fejlside.

## Du skal gøre følgende for at få webapplikationen i luften:

1. Først skal du clone projektet eller downloade en zip-fil med projektet til din arbejdsstation.
2. Dernæst skal du åbne Workbench og køre disse to scripts:
   1. resources/bibliotek.sql
   2. resources/create_test_database.sql
3. Du skal evt. ændre kodeord til databasen i projektet. Det gøres i filerne: `/persistence/ConnectionPool` i linie 14 og 15. Du skal også ændre i testfilen til begge datamappere.
4. Til sidst skal du lave en Tomcat konfiguration. Dvs, 
   1. klik på "Add Configuration ..."
   2. Klik på "+" og  vælg "Tomcat Server Local".
   3. Klik på "Fix knappen"
   4. Vælg war-exploded som deployment type
   5. Nu kan du klikke på den grønne play-knap for at bygge og køre projektet.

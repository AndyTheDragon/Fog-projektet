Dette er eksamensprojekt for Gruppe B på 2. semester datamatiker uddannelsen på CphBusinessAcademy Campus Lyngby december 2024.

## Deployment
* Brug maven til at package projektet til en fat jar.
* Opret en database, og kør de to sql scripts `/src/man/resources/sql/tables.sql` og `/src/man/resources/sql/sample_data.sql`
* Deploy til en virtuel maskine med følgende fem ENVIRONMENT VARIABLES
* * JDBC_USER = database bruger
* * JDBC_PASSWORD = database bruger kodeord
* * JDBC_CONNECTION_STRING = jdbc:postgresql://IP:5432/%s?currentSchema=public
* * JDBC_DB = database navn
* * DEPLOYED = true
* se i øvrigt deployment guiden i filen `deploy.md`

## Demo version
Kan findes på `carport.wyrmlings.dk`

## Rapport og diagrammer
Findes i mapperne report og docs
# springboot-jpa-demo

Eine einfache Java-Spring-Boot Demo mit JPA, OpenApi und Swagger.<br>

## Aufbau des Projekts

Als Datenbank wurde eine MySQL Datenbank verwendet. <br>
Als Entity wurde ein simpler Employee mit den Attributen id, firstName, lastName und email definiert.<br>
Bei den Endpunkten handelt es sich um einfache CRUD-Operationen für den Employee.<br>
Zur einfachen Handhabung der Datenbankoperationen wurde JpaRepository verwendet.

## Setup
1. In der MySQL Datenbank muss mittels <br>
    ``CREATE DATABASE  IF NOT EXISTS `itzbund_demo`;`` <br>
Die Database erstellt werden. Mittels der application.properties wird die Tabelle automatisch beim Starten der Applikation erstellt.
2. Die JDBC-Url, Username und Passwort müssen mittels Umgebungsvariablen definiert werden.
    ``DB_URL=jdbc:mysql://<yourServer>:<yourPort>/itzbund_demo``<br>
    ``MYSQL_DB_CREDS_USR=<yourUsername>``<br>
    ``MYSQL_DB_CREDS_PSW=<yourPassword>``<br>
Beim lokalen Starten der Applikation aus einer Entwicklungsumgebung heraus bietet sich hier an, die Umgebungsvariablen in der Run-Configuration zu definieren.


## Jenkins Konfiguration und Pipeline
Zum Bauen des Projekts wurde eine Jenkins-Pipeline verwendet.

### Verwendete Jenkins Plugin
- Git Plugin
  - Zum Auschecken des Projekts mittels scmGit
- Github Plugin
  - Für die Möglichkeit, einen Github Webhook einzurichten um polling zu verringern.
- Warnings Plugin
  - Als Code-Quality-Plugin. Einfachheitshalber werden nur Java-Compiler warnings gesammelt und angezeigt.

### Konfiguration der Jenkins-Node
- Die DB_URL wurde als Umgebungsvariable in der Jenkins Node definiert.
- Da es sich bei dem Jenkins um eine Jenkinsinstanz mit nur einer Node handelte, funktioniert dies.

### Konfiguration der Datenbankcredentials
- Die Datenbankcredentials werden als Jenkins Credentials mit Namen `itzbund_demo_jdbc` definiert.

### Konfiguration der Pipeline
- Discard old builds: 14 Tage, 20 Builds
- Github Project: https://github.com/madaffner/springboot-jpa-demo
- Poll SCM: H/15 * * * *
  - Da der Jenkins nicht via URL erreichbar ist, wird mit Polling gearbeitet.
  - Bei einem Jenkins der via URL erreichbar ist wäre Github hook trigger for GITScm polling zu empfehlen.
- Definition der Pipeline:
  - Pipeline script from SCM

### Jenkinsfile
Das Jenkinsfile selbst ist recht simpel gehalten.<br>
Im environment Block werden die Datenbankcredentials ausgelesen.<br>
Bei Nutzung von `MYSQL_DB_CREDS = credentials()` werden automatisch auch Umgebungsvariablen für MYSQL_DB_CREDS_USR und _PSW erstellt.
Diese sind wiederum in der application.properties referenziert.
<br><br>
Darauf folgen die Stages beginnend mit der Checkout Code Stage.
Diese Stage checkt den Code mittels scmGit aus dem Repository aus.<br>
**ACHTUNG: Dieser Schritt checkt den Code mittels SSH aus. Da hierfür ein SSH-Key in der Jenkins Node / Im Github-Repository definiert werden muss wird dieser Schritt bei externen Entwicklern nicht funktionieren.**<br>
<br><br>
Darauf folgt die Build Stage.
In der Build Stage wird mittels mvn -DskipTests clean install die Applikation gebaut. Da später eine Test-Stage folgt, werden hier die Tests geskippt.
<br><br>
Darauf folgt die Test Stage.
In der Test Stage wird mittels mvn test die Applikation getestet.
Sollte es zu gescheiterten Tests kommen, so failed der Jenkinsbuild.
Als Post-Action werden die Testresults dem Buildergebnis hinzugefügt.
<br><br>
Als letztes kommt die Analysis-Stage.
In der Analysis-Stage wird mittels recordIssues (aus dem Warnings-Plugin) die Code-Quality-Analysis durchgeführt.
Einfachheitshalber werden hier nur Java-Compiler Warnings erfasst.
Sollte hier mehr gewünscht sein, so könnte man z. B. einen SonarQube Scanner verwenden.

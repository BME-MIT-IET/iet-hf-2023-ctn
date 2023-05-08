# CI és Gradle build keretrendszer

## Elvégzett munka :briefcase:

Gradle build keretrendszer beüzemelése.

Alapvető CI beüzemelése. Ehhez a gyakorlati anyagot illetve internetes blogokat használtam fel.

*Projekt struktúra helyrerakása*

## Képernyőképek :framed_picture:

![Egy sikeres build](/../screenshots/CI_01.png)

## Eredmények és tanulságok :thinking:

A gradle-t hamar sikerült beüzemelni, az IntelliJ sok segítséget adott benne illetve az internetes források. Tetszett, hogy "kézzel" kellett, utólag elkészítenem, mivel ilyet még nem csináltam. Mindig csak a projekt elkészítésénél választottam ki, hogy gradle-t szeretnék használni build-hez és az IDE elkészítette nekem.

A CI-hez picit többet kellett utána néznem. A segítségemre az volt, hogy a GitHub blog-ban vannak előre elkészített CI templatek amiken keresztül kifejezetten jól és gyorsan meg lehet érteni a workflow elkészítését.

*Sajnos az init commit elkészítése nem volt tökéletes és mivel a mappa hierarchia változás befolyásolja a CI konfigot ezért ebben a branch-ben kellett helyre raknom a projekt/repo struktúrát, hogy felesleges dolgok ne legyenek benne. Ez volt egy nagy tanulság, hogy legközelebb hasonló helyzetben ezt nagyon át kell gondolni mielőtt bármihez hozzáfogok...*
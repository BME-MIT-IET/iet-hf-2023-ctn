## Blackbox tesztelés
Ennek a tesztelésnek az a lényege, hogy a program szerkezetének ismerete nélkül, a csak a dokumentáció és követelmények alapján teszteljük
### Funkcionaális követelmények

|Sorszám| Követelmények | Teljesül-e |
|--|--|--|
|1| Egy laboratórium falán egy genetikai kód lelhető fel. | teljesül |
|2| Ágensek a letapogatott genetikai kód alapján hozhatók létre.|teljesül|
|3|Van olyan ágens, amely hatására az áldozat elfelejti az általa ismert genetikai kódokat.|teljesül|
|4| Ágens létrehozásához megfelelő mennyiségű anyag szükséges . | teljesül |
|5| Ágenst a virológus saját magán és másik virológuson is felhasználhat.|részlegesen teljesül|
|6| Van olyan ágens, amely megvéd más virológusok egyes ágenseinek hatásától.|teljesül|
|7| Az ágensek hatása adott idő után elévül. | teljesül |
|8| Egy virológus egyszerre maximum 3 felszerelést viselhet|teljesül|
|9| Baltával meg lehet ölni más virológusokat | teljesül |
|10|A kesztyű visszadobja a támadóra az ágenst.| teljesül |
|11| A kesztyű 3 használat után elkopik| nem teljesül |
|12| Találkozás esetén a támadó a megtámadott fél bénulása esetén elveheti annak anyagkészletét.|teljesül|
|13| A játékot összes genetikai kódot először megtanuló virológus nyeri. | nem teljesül |
|14| A zsák megnöveli az anyaggyűjtő képességet.| teljesül|
|15| A játékállás offline menthető| teljesül|
### Tesztelések menete

 - A játékot elindítottam 2 játékossal, majd ugyanabba a laborba irányítottam őket és letapogattam az ágenset és ugyan azt a genetikai kódot találtam
 - Az ágens elkészítésekor azt az ágenset hozhattam létre, amit már letapogattam és el is készült
 - Az egyik játékossal rákentem a felejtő ágenst egy olyan játékosra, aki 2 ágenst ismert, a rákenés után már nem ismerte őket
 - A különböző ágensek elkészítésekor kölönböző mennyiségű anyagra van szükség (pl.: felejtes 12-12, sebezhetetlenség 8-9), de konzisztensen. Mivel a specifikációban nem volt megadva pontos szám, ezért teljesül
 - Különböző ágensek elkészítése után valamelyiket (sebezhetetlenség) mindkét virológusra lehet használni, de másikat (felejtés) csak a másik virológusra lehet használni
 - A védő ágens használata után, a védett virológusra kent ágensek nem hatottak
 - A virológusra kent ágensek pár kör elteltével nem hatnak tovább
 - A virológussal megszereztem 3 különböző felszerelést,majd felvettem mégegyet és nem krült viselt állapotba, akkor sem amikor az aktiválásra rányomtam, de ha levettem az egyik felszerelést, akkor egy másikat rá tudtam rakni
 - Balta felvétele után, ha egy másik játékoson használtam, akkor kiesett a játékból és csak a többi játékost lehetett irányítani
 - Amikor egy virológusra, akinél volt egy kesztyű, kentem egy ágenst, az ágens az én virológusomra kezdett el hatni, amikor nálam is volt kesztyű, akkor az ágens megsemmisült.
 - Akárhányszor használtam ágenst, a kesztyűnek a hatása sose veszett el
 - egy megbénított virológustól elloptam az anyagkészletét, amikor ez a virológus került sorra, akkor nem volt semennyi anyaga
 - Ha egy játékos megszerzi mind a 4 genetikai kódot, akkor felugrik egy ablak, hogy megnyerte, de nincs vége a játéknak, lehet folytatni és ha egy másik játékos is megszerzi a 4 genetikai kódot, nála is kiírja, hogy ő nyert
 - A zsák felvétele esetén több anyagot tudtam felszedni és ezeket felhasználni is tudtam
 - A játék állását elmentettem és visszatöltésnél megfelelő állapotban volt minden.
 
 Észrevett hibák:
 
 - Ha több anyagot próbálunk felvenni, mint amennyi férőhely van, amikor még nem vettünk fel korábban anyagot, akkor azt írja, hogy 0 anyag van a virológusnál és akármennyit próbálunk felvenni, nem tudunk és a kiírás sem változik és a raktárról is eltűnnek az anyagok
 - Ha zsák van nálunk és nincsen összesen elég anyag, de mind a kettőből van a zsákban, egy ágens elkészítésére felhasználja a kellőnél kevesebb anyagokat és az ágens nem készül el


 



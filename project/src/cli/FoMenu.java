package cli;

import Logika.Jatek;
import Logika.Virologus;
import Modell.Agensek.*;
import Modell.Anyagok.Aminosav;
import Modell.Anyagok.Nukleotid;
import Modell.Genetika.GenetikaiKod;
import Modell.Palya.*;
import Modell.TulajdonsagModosito;
import Modell.Vedofelszereles.*;

import java.util.*;

public class FoMenu {
    // AZ a virologus akit iranyit a jatekos jelenleg
    // kor vege eseten mindig valtozik
    private Virologus aktivJatekos;
    private boolean lepettMar = false;

    // Beolvasunk
    private Scanner s = JatekMain.Scanner;

    // Ezt meghivva kezdodik el a jatek, azaz addig jatszunk (mennek a korok stb...) amig ki nem lep a jatekos
    public void JatekInditasa(){
        // gyors beallitjuk a kezdo jatekost az elso jatekost aki a sorban szerepel
        aktivJatekos = Jatek.getInstance().getJatekosok().get(0);

        // csak kilepes hatasara fog innen kilepni, nem tudni h mikor kovetkezik be
        while(true){
            menuMegjelenitese();
            int menu = menuIndexBekerese(14);

            switch (menu){

                // Kenes
                case 1:
                    KenesKezelese();
                    break;
                // Vandorlas
                case 2:
                    if(!lepettMar)
                        VandorlasKezelese();
                    break;
                // AgensKeszitese
                case 3:
                    AgensKesziteseKezelese();
                    break;
                // GenKodTanulas/Tapogatas
                case 4:
                    GenetikaiKodTanulasaKezelese();
                    break;
                // AnyagFelvetele
                case 5:
                    AnyagFelveteleKezelese();
                    break;
                // FelszerelesFelvetele
                case 6:
                    FelszerelesFelvetelKezelese();
                    break;
                // FelszerelesAktivalasa
                case 7:
                    FelszerelesAktivalasKezelese();
                    break;
                // FelszerelesEltetele
                case 8:
                    FelszerelesEltetelKezelese();
                    break;
                // Rablas
                case 9:
                    RablasKezelese();
                    break;
                // BaltaHasznalata
                case 10:
                    BaltaKezelese();
                    break;
                // Statisztika
                case 11:
                    StatisztikaKezelese();
                    break;
                // KorVEge
                case 12:
                    kovetkezoJatekosBeallitasa();
                    System.out.println("Jatekos valtva!");
                    break;
                // JatekMentese&Kilepes
                case 13:
                    // Itt majd a jatek.getInstanceog().mentes()-t kell hasznalni ha jol ertem
                    // ez szerializalva fogja mneteni a jatekot ami jo
                    //TODO: Teszt, teszt, teszt...
                    System.out.println("Mentes helye:");
                    var nev = JatekMain.Scanner.nextLine();
                    Jatek.getInstance().mentes(nev);
                    System.out.println("Jatek mentese...");
                    Jatek.reset();
                    return;
                case 14:
                    System.out.println("Kilepes...");
                    Jatek.reset();
                    return; // ezzel kilep a loop-bol es a fgv-bol, vege lesz a jateknak

                default: System.out.println("Ismeretlen menusorszam!"); break;
            }

            // TODO: Ezt beadasra kihagyjuk ezzel mukodni fognak a tesztek (legalabbis ez nem bassza el)
            // Varunk egy billentyu nyomasra hogy ne ugorjon el egybol az adott parancs kimenetele
            //System.out.print("Nyomj egy billentyut a folytatashoz...");
            //s.nextLine();
        }
    }

    private void StatisztikaKezelese(){
        System.out.println("~~~~~~~~~~~| STATISZTIKA |~~~~~~~~~~~~");
        // Genetikia kodok listazasa
        System.out.println("MEGTANULT GENETIKAI KODOK");
        for(GenetikaiKod g : aktivJatekos.getIsmertKodok()){
            System.out.println("\t* " + g.getKodNeve());
        }

        // felszerelesek listazasa
        // zsak eseteben meg a benne tarolt anyagok listazasa is
        System.out.println("BIRTOKOLT FELSZERELESEK");
        System.out.println("\t+ AKTIV FELSZERELESEK");
        for(Vedofelszereles f : aktivJatekos.getAktivVedofelszerelesek()){
            System.out.println("\t\t* " + f.getNeve());
        }
        System.out.println("\t+ HORDOZOTT FELSZERELESEK");
        for(Vedofelszereles f : aktivJatekos.getHordozottVedofelszerelesek()){
            System.out.println("\t\t* " + f.getNeve());
        }

        // Anyagainak mutatasa
        System.out.println("SAJAT ANYAGOK");
        System.out.println("\t* " + aktivJatekos.getAnyagok().toString());

        // Rahato agensek mutatasa
        System.out.println("RAHATO AGENSEK");
        for(TulajdonsagModosito a : aktivJatekos.getAktivModositok()){
            if (a instanceof Agens) System.out.println(((Agens) a).getNev());
            /*if(a instanceof Benulas)                    System.out.println("\t* Benulas");
            else if(a instanceof Felejtes)              System.out.println("\t* Felejtes");
            else if(a instanceof MedveVirus)            System.out.println("\t* MedveVirus");
            else if(a instanceof Sebezhetetlenseg)      System.out.println("\t* Sebezhetetlenseg");
            else if(a instanceof Vitustanc)             System.out.println("\t* Vitustanc");*/
        }

        // Agensei mutatasa
        System.out.println("BIRTOKOLT/ELKESZITETT AGENSEK");
        for (Agens a : aktivJatekos.getFelhasznalhatoAgensek()){
            System.out.println("\t* " + a.getNev());
        }

        // Egy mezon allok (Erinthetok) mutatasa
        System.out.println("EGY MEZON ALLO JATEKOSOK");
        for(Virologus v : beturendben( new ArrayList<Virologus>(aktivJatekos.getJelenlegiMezo().getErinthetok()))){
            System.out.println("\t* " + v.getNev());
        }

        // Jelenlegi mezo informaciok mutatasa
        System.out.println("JELENLEGI MEZO TULAJDONSAGAI");
        System.out.println("\t* " + aktivJatekos.getJelenlegiMezo().toString());
    }

    private void AnyagFelveteleKezelese(){
        System.out.println("~~~~~~~~~~~~~| ANYAGFELVETELE |~~~~~~~~~~~~~");

        System.out.println("[1] Aminosav");
        System.out.println("[2] Nukleotid");

        int anyagIndex = -1;
        while( !(anyagIndex == 1 || anyagIndex == 2) ){
            System.out.print("Milyen anyagot akarsz felvenni? ");
            anyagIndex = s.nextInt();
        }

        int anyagMennyiseg = -1;
        while( anyagMennyiseg <= 0 ){
            System.out.print("Mennyi anyagot akarsz felvenni? ");
            anyagMennyiseg = s.nextInt();
        }

        // itt mar megvan minden is ami kell az anyag felvetelehez
        // HA minden igaz akkor a .anyagFelvetele az majd lekezel mindent is amit kell es helyesen meg fogja oldani az anyagfelvetelt
        // ha aminosavat akar felvenni akkor azt feszunk fel
        if(anyagIndex == 1){
            Aminosav igeny = new Aminosav(anyagMennyiseg);
            aktivJatekos.anyagFelvetele(igeny);
        }
        // Ha meg nukleotid akar felvenni akkor meg azt
        else{
            Nukleotid igeny = new Nukleotid(anyagMennyiseg);
            aktivJatekos.anyagFelvetele(igeny);
        }
    }

    private void GenetikaiKodTanulasaKezelese(){
        System.out.println("~~~~~~~~~~~| AGENSLETAPOGATASA |~~~~~~~~~~~~");
        aktivJatekos.kodLetapogatasa();
    }

    private void AgensKesziteseKezelese(){
        System.out.println("~~~~~~~~~~~~~| AGENSKESZITESE |~~~~~~~~~~~~~");

        // Ha nem lenne mit csinalnia akkor felesleges tovabb folytatni a dolgokat...
        if(aktivJatekos.getIsmertKodok().size() == 0){
            System.out.println("Nincs hasznalhato Kod!");
            return;
        }

        var kodjai = new ArrayList<GenetikaiKod>(aktivJatekos.getIsmertKodok());

        for(int i = 0; i < kodjai.size(); i++){
            System.out.println("["+ i +"] " + kodjai.get(i).getKodNeve());
        }

       // System.out.println("Melyikbol akarsz valasztani?");

        int kodIndex = -1;
        while( !(0 <= kodIndex && kodIndex <= kodjai.size()) ){
            System.out.print("Melyikbol akarsz valasztani?" + "");
            kodIndex = s.nextInt();
        }

        // itt mar megvan minden ami az Agens keszitesehez kell
        // Ha minden igaz akkor ez a Modell beli fgv megoldja minden problemat
        aktivJatekos.agensKeszitese(kodjai.get(kodIndex));
    }

    private void KenesKezelese(){
        System.out.println("~~~~~~~~~~~~~~~~~~| KENES |~~~~~~~~~~~~~~~~~");

        // Ha nem lenne mit csinalnia akkor felesleges tovabb folytatni a dolgokat...
        if(aktivJatekos.getFelhasznalhatoAgensek().isEmpty()){
            System.out.println("Nincs hasznalhato Agens!");
            return;
        }

        // szukseges adatok
        var agensei = aktivJatekos.getFelhasznalhatoAgensek();
        var egyMezonlevok = new ArrayList<Virologus>(aktivJatekos.getJelenlegiMezo().getErinthetok());

        // Celpont kivalasztasa amig nem ad helyes celt
        System.out.println("Kire akarsz kenni?");
        ArrayList<Virologus> btuRendben = beturendben(egyMezonlevok);

        if(btuRendben.isEmpty()){
            System.out.println("Nincs kire kenni");
        }
        
        for(int i = 0; i < btuRendben.size(); i++){
            System.out.println("["+ (i+1) +"] " + btuRendben.get(i).getNev());
        }
        // kivalasztott opci indexe, sorszama
        int viroIndex = -1;
        while( !(1 <= viroIndex && viroIndex <= egyMezonlevok.size()) ){
            System.out.print("Valasztasom: ");
            viroIndex = s.nextInt();
        }
        // vissza allitjuk 1-n tartomanyrol 0-(n-1) tartomanyba
        viroIndex--;

        // Kenni akart agens kivalasztasa amig nem ad helyes valaszt, opciot
        System.out.println("Mit akarsz kenni?");
        for(int i = 0; i < agensei.size(); i++){
            System.out.println("["+ (i+1) +"] " + agensei.get(i).getNev());
        }

        // kivalasztott agens indexe
        int agensIndex = -1;
        while( !(1 <= agensIndex && agensIndex <= egyMezonlevok.size()) ){
            System.out.print("Valasztasom: ");
            agensIndex = s.nextInt();
        }
        // vissza allitjuk 1-n tartomanyrol 0-(n-1) tartomanyba
        agensIndex--;

        // Itt mar mindent is bekertunk amit kell, meg lehet tehat lehet kenni
        // Ha minden igaz akkor ha a Modell jol mukodik akkor innentol kezdve minden Tuljd.Modosito mellett le lesz jatszva a kenes/vetes

        // Agens kenese a celpontra, ha minden igaz itt az indexek mar helyesek a mukodeshez
        aktivJatekos.agensVetese(
                agensei.get(agensIndex),
                egyMezonlevok.get(viroIndex)
        );
    }

    private void VandorlasKezelese(){
        System.out.println("~~~~~~~~~~~~~~~| VANDORLAS |~~~~~~~~~~~~~~~~");
        lepettMar = true;

        TeruletiElem mezoje = aktivJatekos.getJelenlegiMezo();

        System.out.println("Szomszedos mezok:");
        ArrayList<TeruletiElem> sorban = beturendbenTerElem(new ArrayList<TeruletiElem>(mezoje.getSzomszedok()));
        for(int i = 0; i < mezoje.getSzomszedok().size(); i++){
            System.out.println("["+ (i+1) +"] Terulet" + (i+1));
        }

        int szomszedIndex = -1;
        while( !(1 <= szomszedIndex && szomszedIndex <= mezoje.getSzomszedok().size()) ){
            System.out.print("Hova akarsz menni? ");
            szomszedIndex = s.nextInt();
        }
        szomszedIndex--;

        // Itt mar a modell alapjan tudunk leptetni...
        // Ha minden igaz akkor ez a modell alapjan mar megoldja a teljes atlepeset minden fajta TuladjonsagModosito hatasokat figyelembe veve
        aktivJatekos.mozgas( new ArrayList<TeruletiElem>(mezoje.getSzomszedok()).get(szomszedIndex) );
    }

    // Kivalassza a soron kovetkezo jatekost RR(2) :))
    private void kovetkezoJatekosBeallitasa(){
        // Nullazuk a lepettmar-t hisz uj jatekos ujra lephet egyszer
        lepettMar = false;
        Jatek.getInstance().nextRound();
        aktivJatekos = Jatek.getInstance().getAktiv();
    }

    private void FelszerelesFelvetelKezelese(){
        System.out.println("~~~~~~~~~~~~~| FELSZERELES FELVETELE |~~~~~~~~~~~~~");
        System.out.println("[1] Balta");
        System.out.println("[2] Kesztyu");
        System.out.println("[3] Kopeny");
        System.out.println("[4] Zsak");

        int felszerelesIndex = -1;
        while (!(felszerelesIndex>=1&&felszerelesIndex<5)){
            System.out.println("Melyik felszerelest akarod felvenni?");
            felszerelesIndex = s.nextInt();
        }
        if (felszerelesIndex==1){
            Balta igeny = new Balta();
            aktivJatekos.vedofelszerelesFelvetele(igeny);
        }
        else if(felszerelesIndex==2){
            Kesztyu igeny = new Kesztyu();
            aktivJatekos.vedofelszerelesFelvetele(igeny);
        }
        else if(felszerelesIndex==3){
            Kopeny igeny = new Kopeny();
            aktivJatekos.vedofelszerelesFelvetele(igeny);
        }
        else{
            Zsak igeny = new Zsak();
            aktivJatekos.vedofelszerelesFelvetele(igeny);
        }
    }

    private void FelszerelesAktivalasKezelese(){
        System.out.println("~~~~~~~~~~~~~| FELSZERELES AKTIVALASA |~~~~~~~~~~~~~");
        System.out.println("\t+ HORDOZOTT FELSZERELESEK");
        int felszerelesIndex = 0;
        for(Vedofelszereles f : aktivJatekos.getHordozottVedofelszerelesek()){
            System.out.println("\t\t"+"["+(felszerelesIndex+1) +"] "+ f.getNeve());
            felszerelesIndex++;
        }
        int felszerelesCount=felszerelesIndex;
        felszerelesIndex = -1;
        while (!(felszerelesIndex>=1&&felszerelesIndex<=felszerelesCount)){
            System.out.println("Melyik felszerelest akarod aktivalni?");
            felszerelesIndex = s.nextInt();
        }
        felszerelesIndex--;
        int iteralo =0;
        Vedofelszereles igeny = new Vedofelszereles();
        for(Vedofelszereles f : aktivJatekos.getHordozottVedofelszerelesek()){
            if(iteralo==felszerelesIndex)
                igeny=f;
            iteralo++;
        }
        aktivJatekos.felszerelesAktivalasa(igeny);
    }

    private void FelszerelesEltetelKezelese(){
        System.out.println("~~~~~~~~~~~~~| FELSZERELES ELTETELE |~~~~~~~~~~~~~");
        System.out.println("\t+ AKTIV FELSZERELESEK");
        int felszerelesIndex = 0;
        for(Vedofelszereles f : aktivJatekos.getAktivVedofelszerelesek()){
            System.out.println("\t\t"+"["+(felszerelesIndex+1) +"] "+ f.getNeve());
            felszerelesIndex++;
        }
        int felszerelesCount=felszerelesIndex;
        felszerelesIndex = -1;
        while (!(felszerelesIndex>=1&&felszerelesIndex<=felszerelesCount)){
            System.out.println("Melyik felszerelest akarod eltenni?");
            felszerelesIndex = s.nextInt();
        }
        felszerelesIndex--;
        int iteralo =0;
        Vedofelszereles igeny = new Vedofelszereles();
        for(Vedofelszereles f : aktivJatekos.getAktivVedofelszerelesek()){
            if(iteralo==felszerelesIndex)
                igeny=f;
            iteralo++;
        }
        aktivJatekos.felszerelesEltetele(igeny);
    }
    private void RablasKezelese(){
        System.out.println("~~~~~~~~~~~~~| RABLAS |~~~~~~~~~~~~~");

        System.out.println("EGY MEZON ALLO JATEKOSOK");
        int jatekosIndex =0;
        var virok = new ArrayList<Virologus>(aktivJatekos.getJelenlegiMezo().getErinthetok());
        Collections.sort(virok, Comparator.comparing(Virologus::getNev));

        for(Virologus v : virok){
            System.out.println("\t["+(jatekosIndex+1) +"] "+ v.getNev());
            jatekosIndex++;
        }
        int jatekosCount=jatekosIndex;
        jatekosIndex=0;

        while (!(jatekosIndex>0&&jatekosIndex<=jatekosCount)){
            System.out.println("Melyik jatekostol akarsz lopni?");
            jatekosIndex=s.nextInt();
        }

        Virologus kit = virok.get(jatekosIndex-1);
        
        System.out.println("[1] Balta");
        System.out.println("[2] Kesztyu");
        System.out.println("[3] Kopeny");
        System.out.println("[4] Zsak");

        int felszerelesIndex = -1;
        while (!(felszerelesIndex>0&&felszerelesIndex<5)){
            System.out.println("Melyik felszerelest akarod ellopni?");
            felszerelesIndex = s.nextInt();
        }
        if(felszerelesIndex==1){
            Balta igeny = new Balta();
            aktivJatekos.lopas(kit,igeny);
        }
        else if(felszerelesIndex==2){
            Kesztyu igeny = new Kesztyu();
            aktivJatekos.lopas(kit,igeny);
        }
        else if(felszerelesIndex==3){
            Kopeny igeny = new Kopeny();
            aktivJatekos.lopas(kit,igeny);
        }
        else{
            Zsak igeny = new Zsak();
            aktivJatekos.lopas(kit,igeny);
        }
    }

    private void BaltaKezelese(){
        System.out.println("~~~~~~~~~~~~~| BALTA HASZNALAT |~~~~~~~~~~~~~");

        System.out.println("EGY MEZON ALLO JATEKOSOK");
        int jatekosIndex =0;
        for(Virologus v : beturendben(new ArrayList<Virologus>(aktivJatekos.getJelenlegiMezo().getErinthetok()))){
            System.out.println("\t["+(jatekosIndex+1) +"] "+ v.getNev());
            jatekosIndex++;
        }
        int jatekosCount=jatekosIndex;
        jatekosIndex=0;

        while (!(jatekosIndex>0&&jatekosIndex<=jatekosCount)){
            System.out.println("Melyik jatekostol akarod lebaltazni?");
            jatekosIndex=s.nextInt();
        }

        int iteralo = 1;
        Virologus kit = new Virologus();
        for(Virologus v : aktivJatekos.getJelenlegiMezo().getErinthetok()){
            if(iteralo==jatekosIndex){
                kit = v;
            }
            iteralo++;
        }
        aktivJatekos.baltaHasznalat(kit);
    }

    private void menuMegjelenitese(){
        System.out.println("\n~~~~~~~~~~~~~~~~~| FOMENU |~~~~~~~~~~~~~~~~~");
        System.out.println("AKTIV JATEKOS:\t\t" + aktivJatekos.getNev());
        System.out.println("[01] Kenes");
        System.out.println("[02] Vandorlas");
        System.out.println("[03] Agens Keszitese");
        System.out.println("[04] Genetikai Kod Tanulasa/Tapogatasa");
        System.out.println("[05] Anyag Felvetele");
        System.out.println("[06] Felszereles Felvetele");
        System.out.println("[07] Felszereles Aktivalasa");
        System.out.println("[08] Felszereles Eltetele");
        System.out.println("[09] Rablas");
        System.out.println("[10] Balta Hasznalata");
        System.out.println("[11] Statisztika");
        System.out.println("[12] Kor Vege");
        System.out.println("[13] Jatek Mentese");
        System.out.println("[14] Kilepes");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private int menuIndexBekerese(int maxMenuIndex){
        Scanner s = JatekMain.Scanner;
        int index = -1;
        // Amig nem valid a menuIndex erteke addig kerjuk be
        while( !(0 <= index && index <= maxMenuIndex) ){
            System.out.print("Valasztasom: ");
            index = -1;
            while(index < 0){
                try{
                    index = Integer.parseInt(s.nextLine());
                }catch (Exception e){

                }
            }
        }

        return index;
    }

    // beturendben kiirni a virologusokat
    private ArrayList<Virologus> beturendben(ArrayList<Virologus> ezeket){
        ezeket.sort(Comparator.comparing(Virologus::getNev));

        return ezeket;
    }

    // beturendben kiirni a virologusokat
    private ArrayList<TeruletiElem> beturendbenTerElem(ArrayList<TeruletiElem> ezeket){

        // Inline Comparator osztaly def, nem szep de mukodik (elmeletieg)
        Comparator<TeruletiElem> comp = new Comparator<TeruletiElem>() {
            @Override
            public int compare(TeruletiElem a, TeruletiElem b) {
                String aS = "a", bS = "b";

                if(a instanceof TeruletiElem)       aS = "TeruletiElem";
                else if(a instanceof Labor)         aS = "Labor";
                else if(a instanceof FertozoLabor)  aS = "FertozoLabor";
                else if(a instanceof Ovohely)       aS = "Ovohely";
                else if(a instanceof Raktar)        aS = "Raktar";

                if(b instanceof TeruletiElem)       bS = "TeruletiElem";
                else if(b instanceof Labor)         bS = "Labor";
                else if(b instanceof FertozoLabor)  bS = "FertozoLabor";
                else if(b instanceof Ovohely)       bS = "Ovohely";
                else if(b instanceof Raktar)        bS = "Raktar";

                return aS.compareTo(bS);
            }
        };

        // rendezes
        Collections.sort(ezeket, comp);
        return ezeket;
    }
}

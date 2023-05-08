package Logika;

import Modell.Agensek.*;
import Modell.Anyagok.Aminosav;
import Modell.Anyagok.Anyag;
import Modell.Anyagok.Nukleotid;
import Modell.Genetika.GenetikaiKod;
import Modell.Palya.*;
import Modell.Vedofelszereles.*;
import ggcfg.CfgProperty;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

// AXI
// Egy palyanak a konfiguracioit tartalmazza, kepes GG OP scriptjevel egy konfig file-t beolvasni
// ezzel egyszerubben lehet tesztelni a 9.-10. beadas korul.
// Tervek szerint: Jatek inditasnal (GG .bat-javal pl) argumentumban meg kell adni a kovetkezoket:
//      - konfig fajl (ezzel lesz kepes a jatak adott adatokkal palyat generalni).
//      - parancs bemenetek (szerintem opcionalis ez csak a teszteleshez fog kelleni h milyen input jott
//        a felhasznaloktol.
public class PalyaKonfiguracio {
    // mennyi db teruleti adott elem van
    @CfgProperty
    int dbTeruletiElem;
    @CfgProperty
    int dbRaktar;
    @CfgProperty
    int dbOvohely;
    @CfgProperty
    int dbLabor;
    @CfgProperty
    int dbFertozoLabor;

    // Raktarakban mennyi a min es a max aminosav/nukleotid ertek
    @CfgProperty
    int minAminosav;
    @CfgProperty
    int maxAminosav;
    @CfgProperty
    int minNukleotid;
    @CfgProperty
    int maxNukleotid;

    // Felszerelesek szama, ezeket fogja a palya generalas szetszorni az ovohelyek kozott
    @CfgProperty

    int dbBalta;
    @CfgProperty
    int dbKesztyu;
    @CfgProperty
    int dbKopeny;
    @CfgProperty
    int dbZsak;

    // engedelyezett agensek (csak ilyeneket adot genetikaikodok lesznek szetszorva a Laborokban)
    // es az adott agens szukseges anyagszukseglete
    @CfgProperty
    int lehetBenulas;
    @CfgProperty
    int benulasAminosav;
    @CfgProperty
    int benulasNukleotid;

    @CfgProperty
    int lehetFelejtes;
    @CfgProperty
    int felejtesAminosav;
    @CfgProperty
    int felejtesNukleotid;

    @CfgProperty
    int lehetSebezhetetlenseg;
    @CfgProperty
    int sebezhetetlensegAminosav;
    @CfgProperty
    int sebezhetetlensegNukleotid;
    @CfgProperty
    int lehetVitustanc;
    @CfgProperty
    int vitustancAminosav;
    @CfgProperty
    int vitustancNukleotid;

    private Random r = new Random();

    /*public void konfigBetoltese(String eleresiut){
        // TODO: megirni persze ha ezt GG OP scriptje nem oldja meg, nem emlekszem pontosan...
    }*/

    public ArrayList<TeruletiElem> palyaGeneralasa(){
        ArrayList<TeruletiElem> palya = new ArrayList<TeruletiElem>();

        // region Sima teruleti elemek letrehozasa
        for(int db = 0; db < dbTeruletiElem; ++db){
            palya.add(new TeruletiElem());
        }
        // endregion

        // region Ovohelyek letrehozasa, feltoltese cuccokkal
        // Ovohelyek letrehozasa
        ArrayList<Ovohely> engedelyezettOvohelyek = new ArrayList<Ovohely>();
        for(int db = 0; db < dbOvohely; db++){
            engedelyezettOvohelyek.add(new Ovohely());
        }

        // Felszerelesek letrehozasa es elhelyezese random ovohelyek kozott
        //ArrayList<Vedofelszereles> engedelyezettVedofelszerelesek = new ArrayList<Vedofelszereles>();
        boolean voltmeg = true;
        while( 0 < dbOvohely && voltmeg ){
            voltmeg = false;

            // HA van meg balta akkor random egyet bele rakunk egy Ovohelybe
            if(dbBalta > 0){
                //engedelyezettVedofelszerelesek.add(new Balta());
                engedelyezettOvohelyek.get(r.nextInt(engedelyezettOvohelyek.size())).vedofelszerelesElhelyezese(new Balta());
                dbBalta--;
                voltmeg = true;
            }

            // HA van meg kesztyu akkor random egyet bele rakunk egy Ovohelybe
            if(dbKesztyu > 0){
                //engedelyezettVedofelszerelesek.add(new Kesztyu());
                engedelyezettOvohelyek.get(r.nextInt(engedelyezettOvohelyek.size())).vedofelszerelesElhelyezese(new Kesztyu());
                dbKesztyu--;
                voltmeg = true;
            }

            // HA van meg kopeny akkor random egyet bele rakunk egy Ovohelybe
            if(dbKopeny > 0){
                //engedelyezettVedofelszerelesek.add(new Kopeny());
                engedelyezettOvohelyek.get(r.nextInt(engedelyezettOvohelyek.size())).vedofelszerelesElhelyezese(new Kopeny());
                dbKopeny--;
                voltmeg = true;
            }

            // HA van meg zsak akkor random egyet bele rakunk egy Ovohelybe
            if(dbZsak > 0){
                //engedelyezettVedofelszerelesek.add(new Zsak());
                engedelyezettOvohelyek.get(r.nextInt(engedelyezettOvohelyek.size())).vedofelszerelesElhelyezese(new Zsak());
                dbZsak--;
                voltmeg = true;
            }
        }

        // Feltoltott ovohelyek palyahoz adasa
        palya.addAll(engedelyezettOvohelyek);
        // endregion

        // region Laborok letrehozasa, feltoltese GenetikaiKodokkal

        try {
            // Elkeszitett, felkonfiguralt laborok palyahoz hozzadasa
            palya.addAll(LaborGenerealas(dbLabor, Labor.class));
        } catch (Exception e){
            System.out.println("Sikertelen Labor generalas! (PalyaKonfiguracio/PalyaGeneralas)");
        }

        // endregion

        // region FertozoLaborok letrehozasa

        try {
            // Elkeszitett, felkonfiguralt laborok palyahoz hozzadasa
            palya.addAll(LaborGenerealas(dbFertozoLabor, FertozoLabor.class));
        } catch (Exception e){
            System.out.println("Sikertelen FertozoLabor generalas! (PalyaKonfiguracio/PalyaGeneralas)");
        }

        // endregion

        // region Raktarak letrehozasa, feltoltese random mennyisegu Anyagokkal
        ArrayList<TeruletiElem> engedelyezettRaktarak = new ArrayList<TeruletiElem>();
        for(int db = 0; db < dbRaktar; db++){
            Raktar uj = new Raktar();

            // Anyagok elhelyezese random ertek min, max kozott
            uj.anyagElhelyezese(new Aminosav(
                    // StackOverflow-os magia
                    r.nextInt(maxAminosav + 1 - minAminosav) + minAminosav
            ));
            uj.anyagElhelyezese(new Nukleotid(
                    // StackOverflow-os magia
                    r.nextInt(maxNukleotid + 1 - minNukleotid) + minNukleotid
            ));

            // uj, elkeszult raktar hozzaadasa az engedelyezettekhez
            engedelyezettRaktarak.add(uj);
        }

        // palya teruleteihaz adasa az elkeszult adott mennyisegu raktarakat
        palya.addAll(engedelyezettRaktarak);

        // endregion

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Itt mar az osszes sukseges TerElem benne van a palyaban, felkonfigolva
        // Mar csak a kozottuk levo szomszedsagokat kell kialakitani
        // Random amig minden TerElemnek nem lesz szomszedja
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        // region Szomszedsagok kialakitasa TeruletiElemek kozott

        // itt mindenkinek lesz legalabb 1 szomszedja, igy osszefuggo lesz
        for(TeruletiElem mezo : palya){
            int rSzomszed = r.nextInt(palya.size());
            szomszedsagKialakitasa(mezo, palya.get(rSzomszed));
        }

        // azert kell ez az ertek h legyenek azert szomszedsagok
        int surusseg = 2;   // Ez határozza meg egy kicsit h mennyire legyenek suruk a kapcsolatok
        int kuszobErtek = palya.size() * surusseg;
        while (0 < kuszobErtek || !mindnekVanSzomszed(palya)){
            // Random indexek kivalasztasa a palya kozul
            int randomA = r.nextInt(palya.size());
            int randomB = r.nextInt(palya.size());

            // Ha nem ugyan azt az TeruletiElemet valsztottuk ki
            if(randomA != randomB){
                szomszedsagKialakitasa(
                        palya.get(randomA),
                        palya.get(randomB)
                );
            }

            kuszobErtek--;
        }
        // endregion

        return palya;
    }

    private void szomszedsagKialakitasa(TeruletiElem a, TeruletiElem b){
        a.getSzomszedok().add(b);
        b.getSzomszedok().add(a);
    }

    private boolean mindnekVanSzomszed(ArrayList<TeruletiElem> teruletek){
        // Szirmay fele mindent egysorban mert az jo :)))))
        // rakLvL++;
        for(TeruletiElem t : teruletek) if(t.getSzomszedok().size() == 0) return false;
        return true;
    }

    // T itt igazabol Fertozott / Sima labor
    // Lenyege h Fertozo/Sima laborbol hoz letre ugy ahogyan kell neki, kodduplikacio elkerulese erdekeben lett ilyen...
    private <T extends Labor> ArrayList<T> LaborGenerealas(int dbElem, Class<T> cls) throws Exception{

        // Az elkeszitett laborok amiket majd beszurunk az eddigi kesz TerueltiElemekhez
        ArrayList<T> engedelyezettLaborok = new ArrayList<T>();

        // voltak-e mar elhelyezve ilyen agenset ado genetikai kodok a laborokban
        boolean voltBenulas = false, voltFelejtes = false, voltSebez = false, voltVitus = false;

        // Ezekbol lehet sorsolni agenseket
        ArrayList<Agens> engedelyezettAgensek = new ArrayList<Agens>();

        // ha nincsennek engedelyezve akkor egybol voltaknak tekintjuk
        if(lehetBenulas == 0)           voltBenulas     = true;
        else engedelyezettAgensek.add(new Benulas(-1));

        if(lehetFelejtes == 0)          voltFelejtes    = true;
        else engedelyezettAgensek.add(new Felejtes(-1));

        if(lehetSebezhetetlenseg == 0)  voltSebez       = true;
        else engedelyezettAgensek.add(new Sebezhetetlenseg(-1));

        if(lehetVitustanc == 0)         voltVitus       = true;
        else engedelyezettAgensek.add(new Vitustanc(-1));

        for(int db = 0; db < dbElem; db++) {
            T uj = cls.newInstance();

            if (lehetBenulas == 1 && !voltBenulas) {
                voltBenulas = true;
                // benulast adunk hozza
                // uj labor hozzadasa az engedelyezett laborokhoz
                engedelyezettLaborok.add(BenulasosLabor(uj));
            } else if (lehetFelejtes == 1 && !voltFelejtes) {
                voltFelejtes = true;
                // felejtes adunk hozza
                // uj labor hozzadasa az engedelyezett laborokhoz
                engedelyezettLaborok.add(FelejtosLabor(uj));
            } else if (lehetSebezhetetlenseg == 1 && !voltSebez) {
                voltSebez = true;
                // sebezhetetlenseget adunk hozza
                // uj labor hozzadasa az engedelyezett laborokhoz
                engedelyezettLaborok.add(SebezhetesLabor(uj));
            } else if (lehetVitustanc == 1 && !voltVitus) {
                voltVitus = true;
                // vitustancot adunk hozza
                // uj labor hozzadasa az engedelyezett laborokhoz
                engedelyezettLaborok.add(VitusosLabor(uj));
            } else {
                // HA mar mindenbol is volt 1-1 db amit engedelyezte de meg tobbet kene
                // belerakni a laborokba akkor random fogunk innentol kezdve sorsolni h mi kelrul bele

                Agens valasztott = engedelyezettAgensek.get(
                        r.nextInt(engedelyezettAgensek.size())
                );
                if (valasztott instanceof Benulas) {
                    engedelyezettLaborok.add(BenulasosLabor(uj));
                } else if (valasztott instanceof Felejtes) {
                    engedelyezettLaborok.add(FelejtosLabor(uj));
                } else if (valasztott instanceof Sebezhetetlenseg) {
                    engedelyezettLaborok.add(SebezhetesLabor(uj));
                } else if (valasztott instanceof Vitustanc) {
                    engedelyezettLaborok.add(VitusosLabor(uj));
                }
            }
        }

        return engedelyezettLaborok;
    }

    // Koddupliakcio elkereulese erdekeben, LaborGeneralas-hoz kellettek
    // Mindegyik egy-egy fajta Agensel rendelkezo Fertozo/Sima labort hoz letre
    private <T extends Labor> T BenulasosLabor(T elem){
        // Anyagszuksegletenek beallitasa
        Set<Anyag> szukseglet = new HashSet<Anyag>();
        szukseglet.add(new Aminosav(benulasAminosav));
        szukseglet.add(new Nukleotid(benulasNukleotid));

        // genetikai kod laborhoz adasa
        elem.setGenKod(new GenetikaiKod(szukseglet, new Benulas(3)));

        return elem;
    }
    private <T extends Labor> T FelejtosLabor(T elem){
        // Anyagszuksegletenek beallitasa
        Set<Anyag> szukseglet = new HashSet<Anyag>();
        szukseglet.add(new Aminosav(felejtesAminosav));
        szukseglet.add(new Nukleotid(felejtesNukleotid));

        // genetikai kod laborhoz adasa
        elem.setGenKod(new GenetikaiKod(szukseglet, new Felejtes(2)));

        return elem;
    }
    private <T extends Labor> T SebezhetesLabor(T elem){
        // Anyagszuksegletenek beallitasa
        Set<Anyag> szukseglet = new HashSet<Anyag>();
        szukseglet.add(new Aminosav(sebezhetetlensegAminosav));
        szukseglet.add(new Nukleotid(sebezhetetlensegNukleotid));

        // genetikai kod laborhoz adasa
        elem.setGenKod(new GenetikaiKod(szukseglet, new Sebezhetetlenseg(3)));

        return elem;
    }
    private <T extends Labor> T VitusosLabor(T elem){
        // Anyagszuksegletenek beallitasa
        Set<Anyag> szukseglet = new HashSet<Anyag>();
        szukseglet.add(new Aminosav(vitustancAminosav));
        szukseglet.add(new Nukleotid(vitustancNukleotid));

        // genetikai kod laborhoz adasa
        elem.setGenKod(new GenetikaiKod(szukseglet, new Vitustanc(4)));

        return elem;
    }
}

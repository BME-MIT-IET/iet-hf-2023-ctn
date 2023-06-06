package Logika;

import Logika.Idozites.TickReceiver;
import Modell.Agensek.Agens;
import Modell.Agensek.MedveVirus;
import Modell.Anyagok.Anyag;
import Modell.Anyagok.AnyagTarolo;
import Modell.Genetika.GenetikaiKod;
import Modell.Palya.TeruletiElem;
import Modell.TulajdonsagModosito;
import Modell.Vedofelszereles.Vedofelszereles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A virológusokat kezeli, felel azért, amit csinálhatnak, amit birtokolnak, és az összes tevékenységükért.
 * */

public class Virologus implements TickReceiver, Serializable {
    private static final long serialVersionUID = 3227973466718081153L;
    String nev;
    Set<GenetikaiKod> ismertKodok;
    /**
     * az épp aktív védőfelszerelések (max 3 lehet aktív)
     */
    ArrayList<Vedofelszereles> aktivVedofelszerelesek;
    /**
     * Azon védőfelszerelések listája, ami épp a virológus birtokában van, valamint tárolja, hogy éppen aktív-e egy felszerelés.
     */
    ArrayList<Vedofelszereles> hordozottVedofelszerelesek;
    /**
     * tulajdonában lévő anyagok
     */
    AnyagTarolo anyagok;
    /**
     * az aktív ágensek és aktív védőfelszerelések összessége
     */
    ArrayList<TulajdonsagModosito> aktivTulajdonsagModositok;
    /**
     * azon ágensek, amiket a virológus fel tud használni.
     */
    ArrayList<Agens> felhasznalhatoAgensek;
    /**
     * az a mező, ahol épp áll
     */
    TeruletiElem jelenlegiMezo;

    /**
     * Az a terulet ahol az elobb jart
     * **/
    TeruletiElem elozoTerulete;

    //INNEN NEM ATTR

    // region Konstruktor
    public Virologus(){
        ismertKodok = new HashSet<GenetikaiKod>();
        aktivVedofelszerelesek = new ArrayList<Vedofelszereles>();
        hordozottVedofelszerelesek = new ArrayList<Vedofelszereles>();
        anyagok = new AnyagTarolo();
        aktivTulajdonsagModositok = new ArrayList<TulajdonsagModosito>();
        felhasznalhatoAgensek = new ArrayList<Agens>();
        Jatek.getInstance().getIdozito().addListener(this);

        elozoTerulete = null;
    }
    // endregion

    //region getterek (nehogy dokumentalva legyenek xD)
    public String getNev(){
        return nev;
    }

    public boolean elozoTerulete(TeruletiElem t){
        if(t == elozoTerulete) return true;
        return false;
    }

    public ArrayList<Vedofelszereles> getAktivVedofelszerelesek() {
        return aktivVedofelszerelesek;
    }

    public ArrayList<Vedofelszereles> getHordozottVedofelszerelesek() {
        return hordozottVedofelszerelesek;
    }

    public AnyagTarolo getAnyagok() {
        return anyagok;
    }

    public ArrayList<TulajdonsagModosito> getAktivModositok() {
        return aktivTulajdonsagModositok;
    }

    public ArrayList<Agens> getFelhasznalhatoAgensek() {
        return felhasznalhatoAgensek;
    }

    public TeruletiElem getJelenlegiMezo() {
        return jelenlegiMezo;
    }

    public Set<GenetikaiKod> getIsmertKodok() {
        return ismertKodok;
    }
    //endregion
    //region lopas

    /**
     * kezeli, amikor a virológus megpróbál lopni egy másiktól (tole).
     * @param tole
     * @param ezt
     */
    public void lopas(Virologus tole, Vedofelszereles ezt) {
        // v0.2.1
        if(ezt == null){
            tole.lopasKezelese(this, null);
            return;
        }
        for(var it :aktivTulajdonsagModositok){
            ezt =it.lopas(tole, ezt);
        }

        /*
        v0.1.1 megoldas
        boolean bAnyagLopas = false;
        if(ezt == null)
        {
            bAnyagLopas = true;
            ezt = new Vedofelszereles();
        }
        for(var it :aktivTulajdonsagModositok){
            ezt =it.lopas(tole, ezt);
        }
        if(ezt != null)
            tole.lopasKezelese(this, bAnyagLopas ? null : ezt);

         */

    }

    /**
     * Kezeli, ha az adott virológustól a paraméterben szereplő lopni próbál.
     * @param altala
     * @param ezt
     */
    public void lopasKezelese(Virologus altala, Vedofelszereles ezt) {
        if(ezt == null){

        }
        for(var it :aktivTulajdonsagModositok){
            ezt = it.lopasKezelese(ezt, altala, this);
        }
    }

    //endregion
    //region anyag

    /**
     * megmondja, hogy a virológus fel tud-e használni minden a szótárban szereplő
     * (anyag-mennyiség páros által megállapított) mennyiségű anyagot, majd ha lehetséges, fel is használja.
     * @param mit
     * @return
     */
    public Anyag anyagFelhasznalasa(Anyag mit) {
        var osszesTarolo = new HashSet<AnyagTarolo>();
        osszesTarolo.add(anyagok);
        for (var tm : aktivTulajdonsagModositok){
            tm.anyagTarolasa(osszesTarolo);
        }
        Anyag eredmeny = (Anyag) mit.clone();
        eredmeny.setMennyiseg(0);
        int elvart = mit.getMennyiseg();
        for (var tarolo:osszesTarolo){
            eredmeny.merge(tarolo.Hasznalat(mit));
            mit.setMennyiseg(elvart - eredmeny.getMennyiseg());
            if(eredmeny.getMennyiseg() == mit.getMennyiseg())
                break;
        }
        if(eredmeny.getMennyiseg() == elvart)
            return eredmeny;
        //sikertelen, visszarakjuk
        anyagTarolasa(eredmeny);
        return null;
        /*var res = mit.clone();
        res.setMennyiseg(0);
        return res;*/
    }

    // AXI

    /**
     * azért felel, hogy a raktárból felvegye a virológus az anyagokat.
     * @param anyag
     */
    public void anyagFelvetele(Anyag anyag) {
        Anyag anyagok = anyag;
        for(var tm : aktivTulajdonsagModositok){
            anyagok = tm.anyagFelvetel(anyag, this);
        }
        jelenlegiMezo.anyagokFelvetele(anyagok, this);
    }

    // AXI | 4.4.9.0 | ++GG

    /**
     * Megkísérli tárolni ‘mit’ paramétereként kapott anyagot a vírus alapTár tárolójában, vagy zsákjaiban.
     * Sikeres tárolás esetén null-al tér vissza, egyéb esetben a sikertelenül tárolni kívánt anyaggal (maradékkal).
     * @param mit
     * @return
     */
    public Anyag anyagTarolasa(Anyag mit) {
        Set<AnyagTarolo> tarakOssz = new HashSet<>();
        tarakOssz.add(anyagok);
        // Osszes tarolo osszegyujtese
        for(var tm : aktivTulajdonsagModositok){
            tarakOssz = tm.anyagTarolasa(tarakOssz);
        }

        // Anyagot megrpobalja elrakni a tarolokba
        Anyag maradek = mit;
        for(var at : tarakOssz){
            maradek = at.Tarolas(maradek.clone());
            if(maradek == null)
                break;
        }

        // ha lenne maradek akkor visszaadja
        return maradek;
    }

    // AXI | 4.4.9.1 | ++GG

    /**
     * Az előzővel megegyező célú metódus, a ‘miket’ anyagtároló teljes tartalmát
     * megpróbálja tárolni, siker esetén null-t,
     * sikertelen tárolás esetén a maradék anyagokat tartalmazó AnyagTarolo-t ad vissza.
     * @param tempTarolo
     * @return
     */
    public AnyagTarolo anyagTarolasa(AnyagTarolo tempTarolo){
        AnyagTarolo maradek = tempTarolo;

        Set<AnyagTarolo> tarakOssz = new HashSet<>();
        tarakOssz.add(anyagok);
        // Osszes tarolo osszegyujtese
        for(var tm : aktivTulajdonsagModositok){
            tarakOssz = tm.anyagTarolasa(tarakOssz);
        }

        // Megprobaljuk eltarolni az osszes anyagot ami a taroloban van
        for(AnyagTarolo at : tarakOssz){
            if(maradek == null) break;  // Ha mar eltaroltunk minden anyagot a kapott tarolobol

            maradek = at.Merge(maradek);
        }

        return maradek;
    }

        //endregion
    //region agens

    // AXI | GG | SZTROK

    /**
     * A paraméterben kapott ágenst alkalmazza azon a virológuson, akit szintén paraméterben kapott.
     * @param ezt
     * @param tamadott
     */
    public void agensVetese(Agens ezt, Virologus tamadott){
        Virologus tamado = this;
        Agens vetendo = felhasznalhatoAgensek.stream().filter(p->p.getClass().isInstance(ezt)).findFirst().orElse(null);

        if(vetendo == null)
            return;

        for(var tulajdModosito:aktivTulajdonsagModositok){
            tamado = tulajdModosito.agensKenese(ezt, tamadott, tamado);
            if(tamado == null)
                break;
        }

        if(tamado != null){
            /*
            try{
                var field = ezt.getClass().getSuperclass().getDeclaredField("hatralevoHatas");
                field.setAccessible(true);
                field.set(field, 10); //TODO: UI verziora megirni a gettert/settert --> meglett
            }catch (Exception e){}
             */
            ezt.setHatralevoHatas(3);
            tamadott.agensKapasa(tamado, ezt);
            felhasznalhatoAgensek.remove(vetendo);
        }
    }
    // AXI

    /**
     *  Kezeli a támadás elszenvedését, adott helyzetben kezeli a visszakenést,
     *  megállapítja, hogy egy adott ágens kenése hatásos lehet-e, ha igen, a virológus elszenvedi az ágens hatását.
     * @param tamado
     * @param ezt
     */
    public void agensKapasa(Virologus tamado, Agens ezt){
        Agens tenylegesAgens = ezt;

        // Megnezzuk, hogy valami modsitja-e a kapott agenst
        for(var tm : aktivTulajdonsagModositok){
            if (ezt instanceof MedveVirus && tm instanceof MedveVirus) return;
            tenylegesAgens = tm.agensFogadasa(tenylegesAgens, tamado, this);
            if(tenylegesAgens == null)
                break;
        }

        if(tenylegesAgens != null)
            aktivTulajdonsagModositok.add(tenylegesAgens);
    }

    //TODO: Teszteljétek, nem 100% hogy jó...

    /**
     * azért felel, hogy a virológus elő tudjon állítani egy ágenst, a paraméterként kapott genetikai kód alapján.
     * @param ebbol
     */
    public void agensKeszitese(GenetikaiKod ebbol){
        var tempTarolo = new AnyagTarolo(420420);
        Set<AnyagTarolo> tarakOssz = new HashSet<>();
        tarakOssz.add(anyagok);
        // Osszes tarolo osszegyujtese
        for(var tm : aktivTulajdonsagModositok){
            tarakOssz = tm.anyagTarolasa(tarakOssz);
        }

        for (var tar: tarakOssz) {
            tempTarolo.Merge(tar);
        }

        var maradek = ebbol.agensLetrehozasa(this, tempTarolo);
        anyagTarolasa(maradek);
    }

    //endregion
    //region letapogatas
    // AXI

    /**
     * azért felel, hogy a virológus le tudja tapogatni a laborban lévő kódot,
     * és így megszerezze kódot a már megtanultak listájába
     */
    public void kodLetapogatasa(){
        // Adott mezorol genetikai kod
        GenetikaiKod adott = jelenlegiMezo.letapogatas();

        // ha kaptunk akkor belerakjuk
        if(adott != null){
            boolean volte = false;
            for(GenetikaiKod genKod : ismertKodok){
                if(genKod.getKodNeve().equals(adott.getKodNeve())){
                    volte = true;
                    break;
                }
            }
            if(!volte)
                ismertKodok.add(adott);
        }
    }
    //endregion
    //region mozgas

    /**
     * Kezdeményezi az átlépést egy másik területi elemre.
     * @param cel
     */
    public void mozgas(TeruletiElem cel){
        //a nem szomszedos elemekre nem lehet vandorolni
        if(!jelenlegiMezo.getSzomszedok().stream().anyMatch(p-> p == cel))
            return;

        TeruletiElem vegsoCel = cel;

        for(var tm : aktivTulajdonsagModositok){
            vegsoCel = tm.mozgasMezore(this, vegsoCel);
            if(vegsoCel == null)
                break;
        }

        if(vegsoCel != null){
            jelenlegiMezo.kilepes(this);

            // Atlepes utan gyorsan megjegyezzuk, hogy hol is jartunk
            elozoTerulete = jelenlegiMezo;

            jelenlegiMezo = vegsoCel.belepes(this);
            if(this.aktivTulajdonsagModositok.stream().anyMatch(p->p instanceof MedveVirus))
                vegsoCel.mindenkiFertozese(new MedveVirus());
        }
    }

    // AXI
    public void setStartTerulet(TeruletiElem start){
        jelenlegiMezo = start;
    }

    //endregion
    //region vedofelszereles

    /**
     * Megkísérli az adott védőfelszerelés felvételét az jelenlegiMezo-n.
     * @param felszereles
     */
    public void vedofelszerelesFelvetele(Vedofelszereles felszereles){

        jelenlegiMezo.vedofelszerelesFelvetele(felszereles, this);
    }

    // AXI
    // 4.4.11

    /**
     * Mepróbálja tárolni a paramétereként kapott felszerelést.
     * @param felszereles
     * @return
     */
    public Vedofelszereles vedofelszerelesTarolasa(Vedofelszereles felszereles){
        if(aktivVedofelszerelesek.contains(felszereles)){
            return felszereles;
        }
        else{
            if(aktivVedofelszerelesek.size() < 3){
                aktivVedofelszerelesek.add(felszereles);
                aktivTulajdonsagModositok.add(felszereles);
            }
            else {
                hordozottVedofelszerelesek.add(felszereles);
            }
        }
        return null;
    }
    //Hasznalt maszkot nem vesz fel ujra

    /**
     * Adott védőfelszerelés (felszereles) eldobását kezeli.
     * @param felszereles
     */
    public void vedofelszerelesEldobasa(Vedofelszereles felszereles) {
        if(aktivVedofelszerelesek.contains(felszereles))
            aktivVedofelszerelesek.remove(felszereles);
        if(aktivTulajdonsagModositok.contains(felszereles))
            aktivTulajdonsagModositok.remove(felszereles);
        if(hordozottVedofelszerelesek.contains(felszereles))
            hordozottVedofelszerelesek.remove(felszereles);
    }
    //endregion
    //region tick

    /**
     * Kezeli a virológus által elszenvedett felejtést
     * A virológus kör-végi tevékenységeiért felelős, értesíti a játékot
     * a virológus által éppen ismert genetikai kódok számáról,
     * mely alapján a játékot meg lehet nyerni, valamint kezeli a felejtést
     * (ami minden kör végén lehet hatásos).
     * */
    @Override
    public void onTickReceived(int currentRound) {
        //Felejtés kezelése
        for (var tm : aktivTulajdonsagModositok){
            this.ismertKodok = tm.emlekezetkieses(ismertKodok);
        }
        for(int i = 0; i < aktivTulajdonsagModositok.size(); i++){
            var tm_i = aktivTulajdonsagModositok.get(i);
            if(tm_i instanceof Agens && ((Agens)tm_i).getHatralevoHatas() == 0)
                aktivTulajdonsagModositok.remove(tm_i);
        }
        Jatek.getInstance().ennyitIsmerek(this, this.ismertKodok.size());
    }
    //endregion

    public void setNev(String nev){
        this.nev = nev;
    }

    public void felszerelesEltetele(Vedofelszereles vedofelszereles){
        if(!aktivVedofelszerelesek.contains(vedofelszereles))
            return;
        this.aktivVedofelszerelesek.remove(vedofelszereles);
        this.aktivTulajdonsagModositok.remove(vedofelszereles);

        this.hordozottVedofelszerelesek.add(vedofelszereles);
    }
    public void felszerelesAktivalasa(Vedofelszereles vedofelszereles){
        if(!this.hordozottVedofelszerelesek.contains(vedofelszereles))
            return;
        hordozottVedofelszerelesek.remove(vedofelszereles);
        this.aktivVedofelszerelesek.add(vedofelszereles);
        this.aktivTulajdonsagModositok.add(vedofelszereles);
    }


    //GG | Proposal
    public void baltaHasznalat(Virologus kedvezmenyezett){
        for(int i = 0; i < aktivTulajdonsagModositok.size(); i++){
            TulajdonsagModosito tm = aktivTulajdonsagModositok.get(i);
            tm.baltaHasznalat(this, kedvezmenyezett);
        }
    }

    //GG | Proposal
    public void gyilkossagElszenvedese(){
        jelenlegiMezo.kilepes(this);
        Jatek.getInstance().halalJelentes(this);
    }


}

package Modell.Palya;

import Logika.Virologus;
import Modell.Anyagok.Anyag;
import Modell.Anyagok.AnyagTarolo;

/**
 * Virológusok/Játékosok képesek rálépni és kilépni onnan. Itt vannak az anyagok elraktározva,
 * amiket a virológus képes felvenni, annak érdekében, hogy a genetikai kódok alapján ágenst
 * legyen képes készíteni.
 */
public class Raktar extends TeruletiElem{
    /**
     * Az anyagok tényleges tárolását/kiszolgálását megvalósító tároló.
     */
    private AnyagTarolo tarolo;

    public Raktar(){
        tarolo = new AnyagTarolo(1000);
    }

    @Override
    public void AnyagokElhelyezese(Anyag anyag) {
        this.tarolo.Tarolas(anyag);
    }


    // AXI

    /**
     * A virológus kísérletére amennyiben rendelkezésre áll,
     * az anyag paraméterben megfogalmazott igénynek megfelelően anyagot ad a virológusnak.
     * @param anyag
     * @param akiAkarja
     */
    @Override
    public void anyagokFelvetele(Anyag anyag, Virologus akiAkarja) {
        if (anyag == null) return;
        // Osszeszedjuk a kerest vagy az osszeset ha nincs annyi
        Anyag amijevan = tarolo.Hasznalat(anyag);

        // odaadjuk neki amije van aztan vissza jon a felesleg
        if(akiAkarja != null) {
            Anyag felesleg = akiAkarja.anyagTarolasa(amijevan.clone());

            if (felesleg == null) return;
            // feleseleget vissza rakjuk a tarolojaba
            tarolo.Tarolas(felesleg.clone());
        }
    }

    /**
     * parameterkent adott anyagot elhelyez az aktuális raktárban
     * @param anyag
     */
    public void anyagElhelyezese(Anyag anyag) {
        tarolo.Tarolas(anyag);
    }

    @Override
    public String toString(){
        return "Raktar\tSzomszedok: " + szomszedok.size() + " db.";
    }

    @Override
    public String getTipus() { return "Raktár"; }
}

package Modell;


import Logika.Virologus;
import Modell.Agensek.Agens;
import Modell.Anyagok.Anyag;
import Modell.Anyagok.AnyagTarolo;
import Modell.Genetika.GenetikaiKod;
import Modell.Palya.TeruletiElem;
import Modell.Vedofelszereles.Vedofelszereles;

import java.io.Serializable;
import java.util.Set;

/**
 * Tevekenysegkörök szerint képes módosítani a virológus tulajdonságait.
 * Azoknak az osztályoknak az ős (absztrakt) osztálya, amelyek valahogyan hatnak
 * a virológusra, így valamelyik tulajdonságához kötődő tevékenységét
 * felüldefiniálják egy új szabállyal.
 * */
public abstract class TulajdonsagModosito implements Serializable {
    private static final long serialVersionUID = 5680575037349123606L;

    /**
     * Metódus melyben a zsák (és bármely potenciális leendő hasonló TM) hozzájárulhat a virológus
     * tárolóképességéhez. Felüldefiniálatlanul érintetlenül visszaadja ‘tarolok’ paraméterét.
     * @param tarolok
     * @return
     */
    public Set<AnyagTarolo> anyagTarolasa(Set<AnyagTarolo> tarolok) {
        return tarolok;
    }

    /**
     *Kezeli a mozgást befolyásoló tulajdonságokat. Felüldefiniálatlanul nem befolyásolja (visszaadja) a jelenlegi úti célt.
     * @param virologus
     * @param jelenleg
     * @return
     */
    public TeruletiElem mozgasMezore(Virologus virologus, TeruletiElem jelenleg) {
        return jelenleg;
    }

    /**
     * Ha a módosító egy kesztyű, akkor a felkent ágenst visszadobja arra a virológusra, aki alkalmazta azt. Ha bénulás,
     * akkor lehetetlenné teszi a kenést, egyébként nem csinál semmit.
     * @param ezt
     * @param tole
     * @param ra
     * @return
     */
    public Virologus agensKenese(Agens ezt, Virologus tole, Virologus ra) {
        return ra;
    }

    /**
     * A különböző módosítókban eltérő módon befolyásolja, mi történik a virológussal, ha megpróbálnak rákenni egy ágenst.
     * Ahol nincs felüldefiniálva, nem csinál semmit.
     * @param agens
     * @param tole
     * @param viro
     * @return
     */
    public Agens agensFogadasa(Agens agens, Virologus tole, Virologus viro) {
        return agens;
    }

    /**
     * Kezeli, amikor a virológus megpróbál lopni egy másiktól.
     * @param kitol
     * @param ezt
     * @return
     */
    public Vedofelszereles lopas(Virologus kitol, Vedofelszereles ezt) {
        return null;
    }

    /**
     * Ha a módosító egy bénulás, akkor lehetővé teszi, hogy lopjanak az adott virológustól, alap esetben nem járul hozzá a lopási tevékenységhez.
     * @param ezt
     * @param altala
     * @param tole
     * @return
     */
    public Vedofelszereles lopasKezelese(Vedofelszereles ezt, Virologus altala, Virologus tole) {
        return ezt;
    }

    /**
     * Ha a módosító egy felejtés, akkor ettől a virológus elfelejti a tanult genetikai kódokat,
     * egyébként nem csinál semmit.
     * @param emlekek
     * @return
     */
    public Set<GenetikaiKod> emlekezetkieses(Set<GenetikaiKod> emlekek) {
        return emlekek;
    }

    public Anyag anyagFelvetel(Anyag a, Virologus v) {
        return a;
    }

    public void belepesUtan(Virologus v1) {}

    public void baltaHasznalat(Virologus altala, Virologus ra){

    }
}

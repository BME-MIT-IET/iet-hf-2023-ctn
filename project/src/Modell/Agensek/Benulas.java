package Modell.Agensek;

import Logika.Virologus;
import Modell.Anyagok.Anyag;
import Modell.Palya.TeruletiElem;
import Modell.Vedofelszereles.Vedofelszereles;

/**
 * A játékost (virológust) módosítja/hat rá oly módon, hogy az képtelen lesz mozogni, lopni,
 * illetve ágenstől védekezni meghatározott időre.
 */
public class Benulas extends Agens{
    public Benulas(){

    }

    public Benulas(int hatas){
        this.hatralevoHatas = hatas;
    }

    /**
     * megakadályozza a virológus mozgását.
     * @param virologus az adott virológus, akire vonatkozik
     * @param jelenleg amelyik mezőn áll éppen
     * @return
     */
    @Override
    public TeruletiElem mozgasMezore(Virologus virologus, TeruletiElem jelenleg) {
        if(hatralevoHatas > 0)
            return null;
        return super.mozgasMezore(virologus, jelenleg);
    }

    /**
     * megakadályozza, hogy bénult virológus kenjen ágenst.
     * @param ezt amelyik ágenst próbálja kenni
     * @param tole aki próbál kenni
     * @param ra akire próbálnak kenni
     * @return
     */
    @Override
    public Virologus agensKenese(Agens ezt, Virologus tole, Virologus ra) {
        if(hatralevoHatas > 0)
            return null;
        return super.agensKenese(ezt,tole,ra);
    }

    /**
     * Ellehetetleníti a virológus lopási kísérleteit.
     * @param kitol akitől lopni szeretnének
     * @param ezt amit lopni szeretnének
     * @return
     */
    @Override
    public Vedofelszereles lopas(Virologus kitol, Vedofelszereles ezt) {
        if(hatralevoHatas > 0)
            return null;
        return super.lopas(kitol, ezt);
    }

    /**
     * Lehetővé teszi, hogy lopjanak az adott virológustól.
     * @param ezt
     * @param altala
     * @return
     */
    @Override
    public Vedofelszereles lopasKezelese(Vedofelszereles ezt, Virologus altala, Virologus tole) {
        //ha nincs, nincs mit lopni
        final Vedofelszereles finalEzt = ezt;
        if(hatralevoHatas > 0 && (ezt == null || tole.getAktivVedofelszerelesek().stream().anyMatch(p->p.getClass().isInstance(finalEzt)))){
            if(ezt == null){
                var maradek = altala.anyagTarolasa(tole.getAnyagok());
                tole.anyagTarolasa(maradek);
                return null;
            }
            ezt = tole.getAktivVedofelszerelesek().stream().filter(p->p.getClass().isInstance(finalEzt)).findFirst().orElse(null);
            if(ezt == null)
                ezt = tole.getHordozottVedofelszerelesek().stream().filter(p->p.getClass().isInstance(finalEzt)).findFirst().orElse(null);
            if(ezt == null)
                return null;
            tole.vedofelszerelesEldobasa(ezt);
            altala.vedofelszerelesTarolasa(ezt);
            return null;
        }
        return super.lopasKezelese(ezt,altala, tole);
    }

    @Override
    public Anyag anyagFelvetel(Anyag a, Virologus v) {
        return null;
    }

    @Override
    //public String getNev() { return "Benulas\t" + super.getNev(); }
    public String getNev() { return "Bénulás"; }
}

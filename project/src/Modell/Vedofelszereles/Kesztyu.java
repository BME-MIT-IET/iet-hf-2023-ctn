package Modell.Vedofelszereles;

import Logika.Virologus;
import Modell.Agensek.Agens;

/**
 * A viselőjét ha megkennék egy ágenssel, akkor az a kenés visszafordítódik a kenőre.
 * Azaz a kensére úgy reagál, hogy aki kenne, az fogja saját magát megkenni.
 */
public class Kesztyu extends Vedofelszereles{
    /**
     *  megvalósítja egy adott ágens visszakenését.
     * @param agens milyen ágenst
     * @param tole kitől jön az ágens
     * @param ra akire megy
     * @return
     */

    int hasznalatok = 0;

    @Override
    public Agens agensFogadasa(Agens agens, Virologus tole, Virologus ra) {
        if(tole == ra)
            return null;
        ra.getFelhasznalhatoAgensek().add(agens);
        ra.agensVetese(agens, tole);
        hasznalatok++;
        //if (hasznalatok <= 3) ra.vedofelszerelesEldobasa(this);
        if (hasznalatok >= 3) ra.vedofelszerelesEldobasa(this);
        return null;
    }

    /**
     *  Már kenéskor jelzi a támadott félnek, hogy kesztyűvel rendelkezik,
     *  így rá visszakenni ágenst nem lehetséges.
     * @param ezt ezt akarja rákenni
     * @param tole tőle érkezik az ágens
     * @param ra rá kenik az ágenst
     * @return
     */
    @Override
    public Virologus agensKenese(Agens ezt, Virologus tole, Virologus ra) {
        return tole;
    }

    @Override
    public String getNeve(){
        return "Kesztyu (" + hasznalatok + " hasznalat)";
    }
}

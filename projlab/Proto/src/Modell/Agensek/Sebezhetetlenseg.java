package Modell.Agensek;

import Logika.Virologus;

/**
 * Allapotot jelöl. Ez idő alatt amíg ez a hatás hat a virológusra, addig képtelen ágenst fogadni
 * más virológusoktól kenés esetén
 */
public class Sebezhetetlenseg extends Agens{
    public Sebezhetetlenseg() {
    }

    public Sebezhetetlenseg(int hatas) {
        this.hatralevoHatas = hatas;
    }

    /**
     * Felüldefiniálja a tulajdonságmódosító metódusát,
     * és így tiltja meg attól, hogy mástól ágenst kaphasson.
     * @param agens ameylik ágenst küldik rá
     * @param tole akitől kapja
     * @return
     */
    @Override
    public Agens agensFogadasa(Agens agens, Virologus tole, Virologus viro) {
        if(hatralevoHatas > 0)
            return null;
        return super.agensFogadasa(agens, tole, tole);
    }

    @Override
    //public String getNev() {return "Sebezhetetlenseg\t" + super.getNev();};
    public String getNev() {return "Sebezhetetlenség";}
}

package Modell.Palya;

import Logika.Virologus;
import Modell.Agensek.MedveVirus;
import Modell.Genetika.GenetikaiKod;

/**
 * Virológusok/Játékosok képesek rálépni és kilépni onnan. Itt vannak elrejtve a genetikai
 * kódok, amiket a virológus képes felvenni, megtanulni annak érdekében, hogy közelebb jusson
 * a győzelemhez.
 */
public class Labor extends TeruletiElem{

    //TODO: INKONZISZTENCIA
    public boolean bFertozo;



    @Override
    public TeruletiElem belepes(Virologus belepo) {
        if (bFertozo)
            belepo.agensKapasa(null, new MedveVirus());
            super.belepes(belepo);
        return this;
    }

    /**
     * a laborban lévő genetikai kód
     */
    private GenetikaiKod kod;

    public Labor(){
        super();
    }

    /**
     * Letapogatja a helyet, és ha talál genetikai kódot, akkor
     * a virológus megkapja, megtanulja, amivel képes lesz nyerni vagy ágenseket készíteni.
     * @return
     */
    @Override
    public GenetikaiKod letapogatas() {
        //Lehet ide kellene vmi ronda copy-ctor
        return new GenetikaiKod(kod);
        //return kod;
    }
    // AXI
    public void setGenKod(GenetikaiKod gk){
        this.kod = gk;
    }

    @Override
    public String toString(){
        return "Labor\tSzomszedok: " + szomszedok.size() + " db.";
    }

    @Override
    public String getTipus() { return "Labor"; }
}

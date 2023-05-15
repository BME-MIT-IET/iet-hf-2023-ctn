package Modell.Palya;

import Logika.Virologus;
import Modell.Agensek.MedveVirus;

public class FertozoLabor extends Labor{

    @Override
    public TeruletiElem belepes(Virologus belepo) {
        belepo.agensKapasa(belepo, new MedveVirus());
        return super.belepes(belepo);
    }

    @Override
    public String toString(){
        return "FERTOZO_Labor\tSzomszedok: " + szomszedok.size() + " db.";
    }

    @Override
    public String getTipus() { return "Fertőző Labor"; }
}

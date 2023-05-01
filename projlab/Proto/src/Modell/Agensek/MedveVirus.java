package Modell.Agensek;

//proposal: korKezdete, event ami atvesz Viro-t, jatekra egy halal handler, es kesz
//akar ha vki halott, ez is kezelheti, ha nem akarjuk h a jatek kezelje
//elpusztitas maybe csak egy mindent elhasznalas (anyag)

import Logika.Virologus;
import Modell.Anyagok.Aminosav;
import Modell.Anyagok.Nukleotid;
import Modell.Palya.TeruletiElem;

public class MedveVirus extends Vitustanc {

    public MedveVirus(){
        hatralevoHatas = 1;
    }


    public void belepesUtan(TeruletiElem uj) {
        //Fertoz mindenkit, elhasznal mindent
        uj.anyagokFelvetele(new Nukleotid(420420), null);
        uj.anyagokFelvetele(new Aminosav(690690), null);
        uj.mindenkiFertozese(new MedveVirus());
    }

    @Override
    public TeruletiElem mozgasMezore(Virologus virologus, TeruletiElem jelenleg) {
        TeruletiElem ret = super.mozgasMezore(virologus, jelenleg);
        belepesUtan(ret);
        return ret;
    }

    @Override
    public void onTickReceived(int currentRound) {
        //nem oregszik!
    }

    @Override
    //public String getNev() {return "MedveVirus\tHatralevo hatas: vegtelen";};
    public String getNev() {return "MedveVírus";};
}

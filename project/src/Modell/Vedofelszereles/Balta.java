package Modell.Vedofelszereles;

import Logika.Virologus;

public class Balta extends Vedofelszereles {

    @Override
    public void baltaHasznalat(Virologus altala, Virologus ra) {
        ra.gyilkossagElszenvedese();

        //Szinkron sorrendi halo kesleltetese

        altala.vedofelszerelesEldobasa(this);
    }

    @Override
    public String getNeve(){
        return "Balta";
    }

    //gyilkossag handler?

}

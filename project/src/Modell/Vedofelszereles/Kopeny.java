package Modell.Vedofelszereles;

import Logika.Virologus;
import Modell.Agensek.Agens;

/**
 * 82.3%-os eséllyel megvédi egy-egy ágenstől a viselőjét,
 * amikor azzal meg akarják kenni.
 *
 * */
public class Kopeny extends Vedofelszereles{
    /**
     * ezeli a helyzetet, melyben a virológusra megpróbálnak ágenst kenni.
     * 82.3% eséllyel megsemmisíti az ágenst, ekkor null-al tér vissza,
     * egyéb esetben az ősosztályéval megegyező viselkedést tanúsít.
     * @param agens ezt az ágenst
     * @param tole tőle jön az ágens
     * @param viro neki
     * @return
     */
    @Override
    public Agens agensFogadasa(Agens agens, Virologus tole, Virologus viro) {
        //TODO: ide a 82.3% if
        if(Math.random() < 0.823)
            return null;
        return agens;
    }

    @Override
    public String getNeve(){
        return "Kopeny";
    }
}

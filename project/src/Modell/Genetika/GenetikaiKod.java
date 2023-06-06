package Modell.Genetika;

import Logika.Virologus;
import Modell.Agensek.Agens;
import Modell.Anyagok.Anyag;
import Modell.Anyagok.AnyagTarolo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

/**
 * A játék megnyeréséhez szükséges itemek. Laborból gyűjthetőek, ezekkel képes a
 * virológus/játékos ágenseket tanulni és készíteni.
 */
public class GenetikaiKod implements Serializable {

    /**
     * azoknak az anyagoknak a listája, amelyek szükségesek az adott
     * genetikai kódból készíthető ágens elkészítéséhez.
     */
    private Set<Anyag> anyagSzukseglet;
    /**
     * Egy ágens, melynek klónjait képes a virológus a kód alapján létrehozni.
     */
    private Agens agens;
    //copy-ctor XD
    public GenetikaiKod(GenetikaiKod masik){

        //this.anyagSzukseglet = new HashSet<Anyag>(masik.anyagSzukseglet);
        //this.agens = masik.agens.clone();

        this.anyagSzukseglet = masik.anyagSzukseglet;
        this.agens = masik.agens;
    }

    /**
     * Létrehozza a genetikai
     * kódot, az általa létrehozható ágens, és a létrehozáshoz szükséges anyag alapján.
     * @param anyagSzukseglet
     * @param ehhez
     */
    public GenetikaiKod(Set<Anyag> anyagSzukseglet, Agens ehhez) {
        this.anyagSzukseglet = anyagSzukseglet;
        this.agens = ehhez;
    }

    public Set<Anyag> getAnyagSzukseglet() { return anyagSzukseglet; }

    /**
     * egy a virológus által kapott anyagtár felhasználásával megkísérli a hozzá tartozó ágens
     * előállítását. Siker esetén visszaadja a megmaradt anyagokat a tárolóban (ha van ilyen),
     * és átadja az elkészült ágenst v-nek.
     * @param v
     * @param anyagTarolo
     * @return
     */
    public AnyagTarolo agensLetrehozasa(Virologus v, AnyagTarolo anyagTarolo) {
        ArrayList<Anyag> hasznalva = new ArrayList<>();
        boolean voltHiba = false;
        for(var it: anyagSzukseglet){
            var it_hasznalva = anyagTarolo.Hasznalat(it);
            if(it_hasznalva != null && it_hasznalva.getMennyiseg() == it.getMennyiseg())
                hasznalva.add(it_hasznalva);
            else{
                voltHiba = true;
                break;
            }
        }
        if(voltHiba)
            for (var it : hasznalva)
                anyagTarolo.Tarolas(it);
        else
            v.getFelhasznalhatoAgensek().add(agens);
        return anyagTarolo;
    }

    public String getKodNeve(){
        //return "GenetikaiKod - " + agens.getNev();
        return agens.getNev();
    }
}

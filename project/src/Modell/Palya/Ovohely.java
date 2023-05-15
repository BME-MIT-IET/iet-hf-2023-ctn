package Modell.Palya;

import Logika.Virologus;
import Modell.Vedofelszereles.Vedofelszereles;

import java.util.ArrayList;
import java.util.List;

/**
 * Virológusok/Játékosok képesek rálépni és kilépni onnan. Itt vannak elrejtve védőfelszerelések
 * amiket a virológus képes felvenni, annak érdekében, hogy védekezésben részesülhessen. (pl.:
 * lásd Kesztyu)
 */
public class Ovohely extends TeruletiElem{
    /**
     * az óvóhelyen lévő felszerelések listája
     */
    private ArrayList<Vedofelszereles> taroltFelszerelesek;

    public Ovohely(){
        taroltFelszerelesek = new ArrayList<Vedofelszereles>();
    }


    /**
     * Ha a paraméterként kapott (felvenni kívánt) felszerelés bennevan az óvóhely tárolt felszereléseiben,
     * akkor azt eltávolítja az óvóhelyről és hozzáadja a virológus készletéhez, és ha nem sikerül elraknia a virológusnak,
     * a kért védőfelszerelést, (vissza)adja az óvóhelyen lévő felszerelésekhez.
     * @param o
     * @param ki
     */
    @Override
    public void vedofelszerelesFelvetele(Vedofelszereles o, Virologus ki) {
        Vedofelszereles finalO = o;
        var res = taroltFelszerelesek.stream().filter(p->p.getClass().isInstance(finalO)).findFirst().orElse(null);
        if(res == null)
            return;
        o = res;
        if(taroltFelszerelesek.contains(o)){
            taroltFelszerelesek.remove(o);
            Vedofelszereles kert = o;
            kert = ki.vedofelszerelesTarolasa(kert);

            // Ha nem birta elrakni...
            if(kert != null){
                taroltFelszerelesek.add(kert);
            }
        }
    }

    // AXI
    // Inicitnel kell csak

    /**
     * A paraméteerként kapott védőfelszerelést elhelyezi az adott óvóhelyen, azaz hozzáadja a listához.
     * @param ezt
     */
    public void vedofelszerelesElhelyezese(Vedofelszereles ezt){
        taroltFelszerelesek.add(ezt);
    }

    @Override
    public String toString(){
        return "Ovohely\tSzomszedok: " + szomszedok.size() + " db.";
    }

    @Override
    public String getTipus() { return "Óvóhely"; }
}

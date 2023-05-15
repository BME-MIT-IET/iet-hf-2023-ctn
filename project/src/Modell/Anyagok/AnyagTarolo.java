package Modell.Anyagok;

import Modell.Agensek.Agens;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A Virológusoknak és a Raktárnak anyagok tárolásához és menedzseléséhez szükséges
 * osztály, nyilvántartja az adott entitás anyag mennyiségét.
 */
public class AnyagTarolo implements Serializable {
    /**
     * megadja azt a maximális mennyiséget, amennyi anyagot (bármely
     * típusból összesen) tud tárolni.
     */
    private int maxKapacitas;

    /**
     * Az anyagok tényleges tárolását megvalósító heterogén kollekció.
     */
    private ArrayList<Anyag> anyagok = new ArrayList<Anyag>();

    public AnyagTarolo() {
        this.maxKapacitas = 100;     // Ebben nem vagyok biztos h ennyinek kene lennie...
    }

    public AnyagTarolo(int maxKapacitas){
        this.maxKapacitas = maxKapacitas;
    }

    /**
     * Amikor anyagot akarunk használni, akkor a mit-be az
     * igényelt (adott) anyagot kell megadni és a visszatérési érték attól függően,
     * hogy van-e elég a tárolóban abból az anyagból fog visszatérni.
     * @param mit igényelt anyag mennyisége
     * @return a kért mennyiséggel tér vissza (ha van elég) vagy pedig az összes anyaggal (ha nincs elég).
     */
    public Anyag Hasznalat(Anyag mit) {
        int mennyiseg = mit.mennyiseg;
        var res = mit.clone();
        res.setMennyiseg(0);
        var hasznalando = anyagok.stream().filter(p->p.merge(res) != null).findFirst().orElse(null);

        if(hasznalando == null)
            return res;

        if(hasznalando.getMennyiseg() > mennyiseg)
        {
            hasznalando.mennyiseg -= mennyiseg;
            res.setMennyiseg(mennyiseg);
        }
        else{
            res.setMennyiseg(hasznalando.getMennyiseg());
            hasznalando.setMennyiseg(0);
        }
        return res;
    }

    /**
     * Az attribútumban megadott anyagot hozzáadja a tárolóhoz,
     * amennyiben nem képes annyit tárolni, a felesleget visszaadja.
     * @param mit
     * @return
     */
    public Anyag Tarolas(Anyag mit) {
        if(mit == null)
            return null;

        Anyag finalMit = mit;
        var egyesulve = anyagok.stream().filter(p->p.merge(finalMit) != null).findFirst().orElse(null);
        if(egyesulve == null){
            anyagok.add(mit);
            egyesulve = mit;
            mit = mit.clone();
        }

        int jelenlegTaroltMennyiseg = anyagok.stream().mapToInt(p->p.getMennyiseg()).sum();
        //ha túltöltöttük, kivesszük a többletet
        if(jelenlegTaroltMennyiseg > maxKapacitas){
            mit.setMennyiseg(-1*(jelenlegTaroltMennyiseg - maxKapacitas));
            egyesulve.merge(mit);
            mit.setMennyiseg(jelenlegTaroltMennyiseg - maxKapacitas);
            return mit.clone();
        }
        //nem maradt
        return null;
    }

    /**
     * Az adott tárolóba kísérli illeszteni a ‘vele’
     * paraméterként kapott tároló tartalmát, amennyiben megtelik, visszatér a beillesztés
     * sikertelen részét tartalmazó tárolóval.
     * @param vele
     * @return
     */
    public AnyagTarolo Merge(AnyagTarolo vele) {
        ArrayList<Anyag> maradek = new ArrayList<>();
        for (var anyag : vele.anyagok){
            var temp = this.Tarolas(anyag);
            if(temp != null)
                maradek.add(temp);
        }
        vele.anyagok = maradek;

        if(maradek.size() != 0)
            return vele;

        return null;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(
                "Max Kapacitás: " + maxKapacitas + "\n" +
                "Összegyűjtött anyagok:\n");
        anyagok.stream().forEach(p->sb.append("\t" + p.toString() + "\n"));

        return sb.toString();
    }
}

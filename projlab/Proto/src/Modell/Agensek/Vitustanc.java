package Modell.Agensek;

import Logika.Virologus;
import Modell.Palya.TeruletiElem;

import java.util.ArrayList;
import java.util.List;

//SZEKVENCIA SZERINT!
//LEJARAT CUSTOM!

/**
 *Nem engedi a játékost úgy lépni, ahogyan ő szeretne,
 * mindig véletlenszerűen lép ide-oda.
 * */
public class Vitustanc extends Agens {

    public Vitustanc() {
    }

    public Vitustanc(int hatas) {
        hatralevoHatas = hatas;
    }

    private <T> T getRandomItem(List<T> list){
        return list.get((int) (Math.random() * (list.size())));
    }

    /**
     * Mivel ezen módosító hatása alatt a virológus nem választhatja meg szabadon célját, a
     * jelenlegi célt figyelmen kívül hagyva a virológus aktuális pozíciójának tetszőleges
     * (random) szomszédjával tér vissza.
     * @param virologus az adott virológus,a ki mozogna
     * @param jelenleg az a mező, ahol éppen áll
     * @return
     */
    @Override
    public TeruletiElem mozgasMezore(Virologus virologus, TeruletiElem jelenleg) {
        if(hatralevoHatas > 0){
            List<TeruletiElem> szomszedokAsList= new ArrayList<>();
            szomszedokAsList.addAll(virologus.getJelenlegiMezo().getSzomszedok());
            return getRandomItem(szomszedokAsList);
        }
        //Nem aktív
        return super.mozgasMezore(virologus, jelenleg);
    }

    @Override
    //public String getNev() {return "Vitustanc\t" + super.getNev();};
    public String getNev() {return "Vitustánc";}
}

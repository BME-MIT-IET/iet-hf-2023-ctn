package Modell.Agensek;

import Modell.Genetika.GenetikaiKod;

import java.util.Set;

//SZEKVENCIA SZERINT!
/**
 *
 *
 *
 * */

/**
 * Az az ágens, ami miatt a virológus elfelejti a már megtanult genetikai kódokat.
 */
public class Felejtes extends Agens{

    public Felejtes() {
        hatralevoHatas = -1;
    }
    public Felejtes(int hatas) {
        hatralevoHatas = hatas;
    }

    /**
     * Megkapja a virológus éppen ismert genetikai kódjait,
     * majd visszaadja a felejtésen átesett listát (vagy null-t).
     * @param emlekek éppen ismert genetikai kódok.
     * @return
     */
    @Override
    public Set<GenetikaiKod> emlekezetkieses(Set<GenetikaiKod> emlekek) {
        if(hatralevoHatas >= 0){
            emlekek.clear();
            return emlekek;
        }
        return super.emlekezetkieses(emlekek);
    }
    @Override
    //public String getNev() {return "Felejtes\t" + super.getNev();};
    public String getNev() {return "Felejtés";};

}

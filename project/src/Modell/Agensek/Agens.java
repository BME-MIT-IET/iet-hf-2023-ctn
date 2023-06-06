package Modell.Agensek;

import Logika.Idozites.TickReceiver;
import Logika.Jatek;
import Modell.TulajdonsagModosito;


/**
 * Egy ágenst reprezentál, figyel arra, hogy az ágens hatása lejárt-e, tehát, hogy aktívan hat-e
 * a játékosra. Nyilvántartja, hogy a még fel nem használt ágenst használni lehet-e vagy
 * lejárt már a felhasználható ideje.
 */
public class Agens extends TulajdonsagModosito implements TickReceiver {

    public Agens(){
        Jatek.getInstance().getIdozito().addListener(this);
    }

    boolean bAktiv;
    int hatralevoHatas, hatralevoFelhasznalhatosag;


    /**
     * amikor történik egy tick, akkor mit tegyen
     * az ágens (hatóidő csökkentése/felhasználhatóság csökkentése).
     * @param currentRound
     */
    @Override
    public void onTickReceived(int currentRound) {
        if(hatralevoHatas > 0) hatralevoHatas--;
    }

    public int getHatralevoHatas() {
        return hatralevoHatas;
    }

    public void setHatralevoHatas(int hatra) { hatralevoHatas = hatra; }

    public String getNev() {return "Hatralevo hatas: " + hatralevoHatas;};
}

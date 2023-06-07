package Logika.Idozites;

import Logika.Jatek;
import Logika.Virologus;
import Modell.Agensek.Agens;
import Modell.TulajdonsagModosito;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Nyilvántartja a köröket, növeli azok számát, amikor kell, illetve minden körben szól a
 * feliratkozott listenerek-nek, hogy Tick történt.
 */
public class Timer implements Serializable {
    int currentRound = 0;

    ArrayList<TickReceiver> listeners = new ArrayList<>();

    /**
     * Hozzá adja a paraméterben kapott rec-et a listához,
     * akiket megszólít minden egyes Tick-nél.
     * @param rec
     */
    public void addListener(TickReceiver rec){
        this.listeners.add(rec);
    }

    int playerIndex;
    void requestNextRound(Virologus jatekos){
        nextRound();
    }

    /**
     * Növeli a körök számát
     */
    public void nextRound(){
        currentRound++;
        Collections.sort(listeners, (p1, p2)->{
            if(p1 instanceof Virologus && p2 instanceof Virologus)
                return 0;
            else if(p1 instanceof Virologus)
                    return 1;
            return -1;
        });
        //TODO: légyszi nézzétek meg, hogy ez a rész így oké lenne-e
        listeners.forEach(p->{
            if (p instanceof Virologus && p == Jatek.getInstance().getAktiv()) {
                for(TulajdonsagModosito a: ((Virologus) p).getAktivModositok()) {
                    if (a instanceof Agens) ((Agens) a).onTickReceived(currentRound);
                }
                p.onTickReceived(currentRound);
            }

            //p.onTickReceived(currentRound);
        });

    }
}


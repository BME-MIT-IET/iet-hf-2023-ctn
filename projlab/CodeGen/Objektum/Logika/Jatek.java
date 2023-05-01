package Logika;

import java.util.*;

/**
 * TODO: Kell még egy halom modell, gondolom MVC UI lesz, a csatlakozó, és discovery screen modellje is saját kell legyen
 * Talán mivel még a CLIre gyúrunk, ezeket hanyagolhatjuk, viszont a connectivity modulnak lehetnek még segédobjektumai.
 * Opcionális, tehát lehet elkerülhető a mostani dokumentáció.
 * A mezőkezelés talán most inkább a térkép mind objektum feladata lehetne, mert db szerű kell legyen a játékos-és map management
 * hogy a hálózati sync, és a borok egszerű query API-ja menjen.
 */
public class Jatek implements TickReceiver, TickReceiver {

    /**
     * Default constructor
     */
    public Jatek() {
    }

    /**
     * 
     */
    private Set<TeruletiElem> palya;

    /**
     * 
     */
    private Timer idozito;


    /**
     * 
     */
    private Jatek inst;


    /**
     * @return
     */
    public Set<Virologus> getJatekosok() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public static Jatek getInstance() {
        // TODO implement here
        return null;
    }

    /**
     * @param virologusok
     */
    public void init(Set<Virologus> virologusok) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Set<TeruletiElem> getPalya() {
        // TODO implement here
        return null;
    }

    /**
     * @param currentRound 
     * @return
     */
    public void onTickReceived(int currentRound) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Virologus kiANyertes() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void jatekVege() {
        // TODO implement here
    }

    /**
     * @param virologus 
     * @param ennyit
     */
    public void ennyitIsmerek(Virologus virologus, int ennyit) {
        // TODO implement here
    }

    /**
     * @param currentRound
     */
    void onTickReceived(int currentRound) {
        // TODO implement here
    }

}
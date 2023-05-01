package Logika.Idozites;

/**
 * Minden egyes tick esetén amit a Timer kivált, valami történik az implementált osztályban.
 */
public interface TickReceiver {
    /**
     * Ezt a függvényt definiálják felül annak megfelelően,
     * hogy adott Tick-esetén mit kell tennie az osztálynak,
     * ami implementálja ezt az interfészt.
     * @param currentRound
     */
    void onTickReceived(int currentRound);
}

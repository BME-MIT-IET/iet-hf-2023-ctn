package Logika;

import java.util.*;

/**
 * Kicsit sok a probalkozas alapu fuggveny, vegulis vakokrol beszelunk.
 * A lopas pl. ugy mukodik, hogy meghivod a masikon nullal, ad egy listat a lophatokrol,
 * meghivod a lista egy elemevel, returnoli az adott elemet ha sikerul ellopni, nullt ha nem, ureset ha nincs mit lopni.
 * Erre a mintara megy a tobbi is.
 * Ami boolean, sikeresseget jelez.
 */
public class Virologus implements TickReceiver, TickReceiver {

    /**
     * Default constructor
     */
    public Virologus() {
    }

    /**
     * 
     */
    public String nev;

    /**
     * 
     */
    private Set<TulajdonsagModosito> aktivTulajdonsagModositok;

    /**
     * 
     */
    private Vedofelszereles* hordozottVedofelszerelesek;

    /**
     * 
     */
    Set<Agens> aktivAgensek;

    /**
     * 
     */
    private Set<Agens> felhasznalhatoAgensek;














    /**
     * @return
     */
    public String getNev() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public Set<Vedofelszereles> getAktivVedofelszerelesek() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Set<Vedofelszereles> getHordozottVedofelszerelesek() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Set<Anyag> getAnyagok() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Set<Agens> getAktivAgensek() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Set<Agens> getFelhasznalhatoAgensek() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public TeruletiElem getJelenlegiMezo() {
        // TODO implement here
        return null;
    }

    /**
     * @param tole 
     * @param ezt
     */
    public void lopas(Virologus tole, Vedofelszereles ezt) {
        // TODO implement here
    }

    /**
     * @param altala 
     * @param ezt
     */
    public void lopasKezelese(Virologus altala, Vedofelszereles ezt) {
        // TODO implement here
    }

    /**
     * @param mit 
     * @return
     */
    public Anyag anyagFelhasznalasa(Anyag mit) {
        // TODO implement here
        return null;
    }

    /**
     * @param anyag 
     * @return
     */
    public Anyag anyagFelvetele(Anyag anyag) {
        // TODO implement here
        return null;
    }

    /**
     * @param mit 
     * @return
     */
    public Anyag anyagTarolasa(Anyag mit) {
        // TODO implement here
        return null;
    }

    /**
     * @param miket 
     * @return
     */
    public AnyagTarolo anyagTarolasa(AnyagTarolo miket) {
        // TODO implement here
        return null;
    }

    /**
     * @param ezt 
     * @param ra
     */
    public void agensVetese(Agens ezt, Virologus ra) {
        // TODO implement here
    }

    /**
     * @param kod
     */
    public void agensKeszitese(GenetikaiKod kod) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Set<GenetikaiKod> kodLetapogatasa() {
        // TODO implement here
        return null;
    }

    /**
     * @param cel 
     * @return
     */
    public TeruletiElem mozgas(TeruletiElem cel) {
        // TODO implement here
        return null;
    }

    /**
     * @param felszereles
     */
    public void vedofelszerelesEldobasa(Vedofelszereles felszereles) {
        // TODO implement here
    }

    /**
     * @param felszereles
     */
    public void vedofelszerelesFelvetele(Vedofelszereles felszereles) {
        // TODO implement here
    }

    /**
     * @param felszereles 
     * @return
     */
    public Vedofelszereles vedofelszerelesTarolasa(Vedofelszereles felszereles) {
        // TODO implement here
        return null;
    }

    /**
     * @param currentRound
     */
    public void onTickReceived(int currentRound) {
        // TODO implement here
    }

    /**
     * @param tamado 
     * @param agens
     */
    public void agensKapasa(Virologus tamado, Agens agens) {
        // TODO implement here
    }

    /**
     * @param currentRound
     */
    void onTickReceived(int currentRound) {
        // TODO implement here
    }

}